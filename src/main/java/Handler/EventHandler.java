package Handler;

import java.io.*;
import java.net.*;
import java.sql.Connection;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.AuthToken;
import Model.Event;
import Service.EventService;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;

public class EventHandler implements HttpHandler {
    String eventID = null;
    /**
     *
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        String httpURI = exchange.getRequestURI().toString();
        String[] parts = httpURI.split("/");
        try {
            // Get request
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {

                // Get the HTTP request headers
                Headers reqHeaders = exchange.getRequestHeaders();
                // Check to see if an "Authorization" header is present
                if (reqHeaders.containsKey("Authorization")) {

                    // Extract the auth token from the "Authorization" header
                    String authToken = reqHeaders.getFirst("Authorization");
                    success = handleEvent(exchange, success, parts, authToken);
                }
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }

        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

    private boolean handleEvent(HttpExchange exchange, boolean success, String[] parts, String authToken) throws IOException {
        try {
            //Opening the database
            Database myDatabase = new Database();
            myDatabase.openConnection();
            Connection myConnection = myDatabase.getConnection();
            AuthTokenDao myAuthTokenDao = new AuthTokenDao(myConnection);
            AuthToken myAuthtoken = myAuthTokenDao.find(authToken, "authtoken");
            if (myAuthtoken != null) {
                int numParts = parts.length;
                if (numParts == 3) {
                    eventID = (parts[2]);
                }
                if(eventID == null){
                    handleForAllEvents(exchange, myAuthtoken);
                }
                else{
                    handleForSingularEvent(exchange, myAuthtoken, eventID);
                }
                success = true;
                myDatabase.closeConnection(false);
            } else {
                myDatabase.closeConnection(false);
            }
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        return success;
    }

    private void handleForAllEvents(HttpExchange exchange, AuthToken myAuthtoken) throws DataAccessException, IOException {
        EventService myEventService = new EventService();
        myEventService.EventServiceAll(myAuthtoken);
        Event[] myEvents = myEventService.getListOfEventsFinal();
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
        Gson gson = new Gson();
        gson.toJson(myEvents, resBody);
        resBody.close();
    }

    private static void handleForSingularEvent(HttpExchange exchange, AuthToken myAuthtoken, String eventID) throws DataAccessException, IOException {
        EventService myEventService = new EventService();
        myEventService.EventServiceSingular(myAuthtoken,eventID);
        Event myEvent = myEventService.getSingularEvent();
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
        Gson gson = new Gson();
        gson.toJson(myEvent, resBody);
        resBody.close();
    }

    /*
        The readString method shows how to read a String from an InputStream.
    */
    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}