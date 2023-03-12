package Handler;

import java.io.*;
import java.net.*;
import java.sql.Connection;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.AuthToken;
import Model.Person;
import Result.PersonResult;
import Service.PersonService;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;

public class PersonHandler implements HttpHandler {
    String personID = null;
    PersonResult result = new PersonResult();
    /**
     *
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String httpURI = exchange.getRequestURI().toString();
        String[] parts = httpURI.split("/");

        try {
            // Get request
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                // Get the HTTP request headers
                Headers reqHeaders = exchange.getRequestHeaders();
                // Check to see if an "Authorization" header is present
                if (reqHeaders.containsKey("Authorization")) {

                    // Extract the auth token from the "Authorization" header
                    String authToken = reqHeaders.getFirst("Authorization");

                    try {
                        //Opening the database
                        Database myDatabase = new Database();
                        myDatabase.openConnection();
                        Connection myConnection = myDatabase.getConnection();

                        //Pull up the authToken
                        AuthTokenDao myAuthTokenDao = new AuthTokenDao(myConnection);
                        AuthToken myAuthtoken = myAuthTokenDao.find(authToken);
                        if (myAuthtoken != null) {
                            int numParts = parts.length;
                            if (numParts == 3) {
                                personID = (parts[2]);
                                handleWithID(exchange, myAuthtoken);
                            }
                            else if(personID == null){
                                handleWithoutID(exchange, myAuthtoken);
                            } else {result.setMessage("IDK, consult your local programmer");
                                result.setSuccess(false);
                                myDatabase.closeConnection(false);
                            }
                        } else {
                            result.setMessage("Not Authorized");
                            result.setSuccess(false);
                            myDatabase.closeConnection(false);
                        }
                    } catch (DataAccessException e) {
                        result.setMessage("Failed");
                        result.setSuccess(false);
                    }
                }
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

    private void handleWithID(HttpExchange exchange, AuthToken myAuthtoken) throws DataAccessException, IOException {
        PersonService myPersonService = new PersonService(myAuthtoken, personID);
        PersonResult result = myPersonService.getResult();
        result.setSingularPerson(myPersonService.getSingularPerson());

        sendResponse(exchange, result);
    }

    private static void handleWithoutID(HttpExchange exchange, AuthToken myAuthtoken) throws DataAccessException, IOException {
        PersonService myPersonService = new PersonService(myAuthtoken);
        PersonResult result = myPersonService.getResult();
        result.setPersonList(myPersonService.getListOfPeopleFinal());

        sendResponse(exchange, result);
    }

    private static void sendResponse(HttpExchange exchange, PersonResult result) throws IOException {
        //Sending response
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
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