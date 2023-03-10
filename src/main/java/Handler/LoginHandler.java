package Handler;

import java.io.*;
import java.net.*;

import DataAccess.DataAccessException;
import Model.AuthToken;
import Request.LoginRequest;
import Model.User;
import Service.LoginService;
import Result.LoginResult;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;


public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try {
            // This HTTPS request is a post
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {

                // Get the HTTP request headers
                Headers reqHeaders = exchange.getRequestHeaders();

                // Extract the JSON string from the HTTP request body

                // Get the request body input stream
                InputStream reqBody = exchange.getRequestBody();

                // Read JSON string from the input stream
                String reqData = readString(reqBody);

                // Display/log the request JSON data
                System.out.println(reqData);

                // TODO: Login
                Gson gson = new Gson();
                LoginRequest request = (LoginRequest) gson.fromJson(reqData, LoginRequest.class);
                LoginService myLoginService = new LoginService(request);
                LoginResult myResult = myLoginService.getMyResult();
                AuthToken myAuthToken = myLoginService.getUserToken();
                //Reader resBody  = new InputStreamReader(exchange.getRequestBody());

                // Start sending the HTTP response to the client, starting with
                // the status code and any defined headers.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                Writer resBody  = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(myResult, resBody); //Writes it to the resBody
                resBody.close();

                // We are not sending a response body, so close the response body
                // output stream, indicating that the response is complete.
                exchange.getResponseBody().close();

                success = true;
            }

            if (!success) {
                // The HTTP request was invalid somehow, so we return a "bad request"
                // status code to the client.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

                // We are not sending a response body, so close the response body
                // output stream, indicating that the response is complete.
                exchange.getResponseBody().close();
            }
        } catch (IOException e) {
            // Some kind of internal error has occurred inside the server (not the
            // client's fault), so we return an "internal server error" status code
            // to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

            // We are not sending a response body, so close the response body
            // output stream, indicating that the response is complete.
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        } catch (DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            // Display/log the stack trace
            e.printStackTrace();
            throw new RuntimeException(e);
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