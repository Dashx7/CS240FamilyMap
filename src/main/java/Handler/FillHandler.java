package Handler;

import java.io.*;
import java.net.*;

import DataAccess.DataAccessException;
import Result.FillResults;
import Service.FillService;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;


public class FillHandler extends Handler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            // post request
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                // Get the request body input stream
                InputStream reqBody = exchange.getRequestBody();

                // Read JSON string from the input stream
                String reqData = readString(reqBody);

                // Display/log the request JSON data
                System.out.println(reqData);

                String httpURI = exchange.getRequestURI().toString();
                String[] parts = httpURI.split("/");
                int numParts = parts.length;
                int numGenerations = 4;
                if (numParts == 4) { //Making sure that it's the right /fill/username/generation request
                    numGenerations = Integer.parseInt((parts[3]));
                } else if (numParts != 3) {
                    //We have a problem
                    throw new IOException("Whack URl");
                }
                String username = (parts[2]);
                FillResults result = new FillResults();

                FillService service = new FillService(username, numGenerations);
                result = service.getMyResults();
                if (result.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                Gson gson = new Gson();
                gson.toJson(result, resBody);
                resBody.close();

            }
        } catch (IOException e) {
            // Some kind of internal error has occurred inside the server (not the
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            // We are not sending a response body, so close the response body
            exchange.getResponseBody().close();
            // Display/log the stack trace
            e.printStackTrace();
        }
    }
}