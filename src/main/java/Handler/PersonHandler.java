package Handler;

import java.io.*;
import java.net.*;
import java.sql.Connection;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.AuthToken;
import Result.PersonResult;
import Service.PersonService;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;

public class PersonHandler implements HttpHandler {
    String personID;
    PersonResult result;
    Database myDatabase;

    /**
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //Refresh result and personID
        result = new PersonResult();
        personID = null;
        String httpURI = exchange.getRequestURI().toString();
        String[] parts = httpURI.split("/");

        try {
            // Get request
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {

                // Get the HTTP request headers
                Headers reqHeaders = exchange.getRequestHeaders();
                // Check to see if an "Authorization" header is present
                if (reqHeaders.containsKey("Authorization")) {
                    try {
                        //Opening the database
                        myDatabase = new Database();
                        myDatabase.openConnection();

                        // Extract the auth token from the "Authorization" header
                        String authToken = reqHeaders.getFirst("Authorization");
                        if (authToken.compareTo("") == 0) {
                            throw new DataAccessException("No authtoken found");
                        }
                        Connection myConnection = myDatabase.getConnection();

                        //Pull up the authToken
                        AuthTokenDao myAuthTokenDao = new AuthTokenDao(myConnection);
                        AuthToken myAuthtoken = myAuthTokenDao.find(authToken);
                        if (myAuthtoken != null) {
                            int numParts = parts.length;
                            if (numParts == 3) {
                                personID = (parts[2]);
                                handleWithID(exchange, myAuthtoken);
                            } else if (personID == null) {
                                handleWithoutID(exchange, myAuthtoken);
                            } else {
                                throw new DataAccessException("IDK, consult your local programmer");
                            }
                        } else {
                            throw new DataAccessException("Not Authorized");
                        }
                    } catch (DataAccessException e) {
                        result.setMessage("Error: Failed because" + e + ", " + e.returnMessage());
                        result.setSuccess(false);
                        myDatabase.closeConnection(false);

                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                        Gson gson = new Gson();
                        String test = gson.toJson(result);
                        gson.toJson(result, resBody);
                        resBody.close();
                    }
                }
            }
        } catch (IOException e) {
            result.setMessage("Failed because" + e);
            result.setSuccess(false);
            myDatabase.closeConnection(false);

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
            Gson gson = new Gson();
            gson.toJson(result, resBody);
            resBody.close();
        }
    }

    private void handleWithID(HttpExchange exchange, AuthToken myAuthtoken) throws DataAccessException, IOException {
        PersonService myPersonService = new PersonService(myAuthtoken, personID);
        PersonResult result = myPersonService.getResult();

        if(result.isSuccess()){
            //result.setSingularPerson(myPersonService.getSingularPerson());
            sendSuccessResponse(exchange, result);
        }
        else {
            sendFailureResponse(exchange,result);
        }
    }

    private static void handleWithoutID(HttpExchange exchange, AuthToken myAuthtoken) throws DataAccessException, IOException {
        PersonService myPersonService = new PersonService(myAuthtoken);
        PersonResult result = myPersonService.getResult();

        if(result.isSuccess()){
            //result.setData(myPersonService.getListOfPeopleFinal());
            sendSuccessResponse(exchange, result);
        }
        else {
            sendFailureResponse(exchange,result);
        }
    }

    private static void sendSuccessResponse(HttpExchange exchange, PersonResult result) throws IOException {
        //Sending response
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
        Gson gson = new Gson();
        gson.toJson(result, resBody);
        resBody.close();
    }
    private static void sendFailureResponse(HttpExchange exchange, PersonResult result) throws IOException {
        //Sending response bad
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
        Gson gson = new Gson();
        gson.toJson(result, resBody);
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