package Handler;

import java.io.*;
import java.net.*;
import java.sql.Connection;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.AuthToken;
import Model.Event;
import Result.EventResult;
import Service.EventService;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;

public class EventHandler implements HttpHandler {
    String eventID = null;

    /**
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String httpURI = exchange.getRequestURI().toString();
        String[] parts = httpURI.split("/");
        // Get request
        if (exchange.getRequestMethod().equalsIgnoreCase("get")) {

            // Get the HTTP request headers
            Headers reqHeaders = exchange.getRequestHeaders();
            // Check to see if an "Authorization" header is present
            if (reqHeaders.containsKey("Authorization")) {
                // Extract the auth token from the "Authorization" header
                String authToken = reqHeaders.getFirst("Authorization");
                boolean success = false;
                try {
                    //Opening the database
                    Database myDatabase = new Database();
                    myDatabase.openConnection();
                    Connection myConnection = myDatabase.getConnection();

                    //Make sure authtoken is valid
                    AuthTokenDao myAuthTokenDao = new AuthTokenDao(myConnection);
                    AuthToken myAuthtoken = myAuthTokenDao.find(authToken, "authtoken");
                    if (myAuthtoken != null) {
                        int numParts = parts.length;
                        if (numParts == 3) {
                            eventID = (parts[2]);
                        }
                        EventResult result = new EventResult();
                        try {
                            EventService myEventService = new EventService();
                            if (eventID == null) {
                                myEventService.EventServiceAll(myAuthtoken);
                                Event[] myEvents = myEventService.getListOfEventsFinal();
                            } else {
                                myEventService.EventServiceSingular(myAuthtoken.getUserName(), eventID);
                                Event myEvent = myEventService.getSingularEvent();
                            }
                            result = myEventService.getMyResult();

                            //Returning a success
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                            Gson gson = new Gson();
                            gson.toJson(result, resBody);
                            resBody.close();
                        }
                        catch (DataAccessException e) {
                            //Failed to access
                            result.setMessage("Failed because :" + e.toString());
                            result.setSuccess(false);
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                            Gson gson = new Gson();
                            gson.toJson(result, resBody);
                            resBody.close();
                        }
                        success = true;
                    }
                    myDatabase.closeConnection(false);

                } catch (DataAccessException e) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                    Gson gson = new Gson();
                    //gson.toJson(myEvents, resBody);
                    resBody.close();
                }
            }
        }
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