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
    EventService myEventService;
    EventResult result;

    /**
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        myEventService = new EventService();
        result = new EventResult();
        String httpURI = exchange.getRequestURI().toString();
        String[] parts = httpURI.split("/");
        // Get request
        if (exchange.getRequestMethod().equalsIgnoreCase("get")) {

            // Get the HTTP request headers
            Headers reqHeaders = exchange.getRequestHeaders();
            // Check to see if an "Authorization" header is present
            if (reqHeaders.containsKey("Authorization")) {
                try {
                    // Extract the auth token from the "Authorization" header
                    String authToken = reqHeaders.getFirst("Authorization");
                    if (authToken.compareTo("") == 0) {
                        throw new DataAccessException("No authtoken found");
                    }

                    //Opening the database
                    Database myDatabase = new Database();
                    myDatabase.openConnection();
                    Connection myConnection = myDatabase.getConnection();

                    //Make sure authtoken is valid
                    AuthTokenDao myAuthTokenDao = new AuthTokenDao(myConnection);
                    AuthToken myAuthtoken = myAuthTokenDao.find(authToken, "authtoken");
                    myDatabase.closeConnection(false);
                    if (myAuthtoken != null) {
                        int numParts = parts.length;
                        if (numParts == 3) {
                            eventID = (parts[2]);
                            if (eventID == null) {
                                throw new DataAccessException("No eventID after URL");
                            } else {
                                myEventService.EventServiceSingular(myAuthtoken.getUserName(), eventID);
                            }
                        } else if (eventID == null) {
                            myEventService.EventServiceAll(myAuthtoken);
                        }
                        result = myEventService.getMyResult();

                        //Returning a success
                        if (result.isSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }
                        Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                        Gson gson = new Gson();
                        gson.toJson(result, resBody);
                        resBody.close();
                    } else {
                        throw new DataAccessException("Error: Did not have a valid Authtoken");
                    }

                } catch (DataAccessException e) {
                    result.setMessage("Error: " + e.toString() + ", " + e.returnMessage());
                    result.setSuccess(false);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                    Gson gson = new Gson();
                    gson.toJson(result, resBody);
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