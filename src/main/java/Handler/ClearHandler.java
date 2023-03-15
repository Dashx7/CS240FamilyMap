package Handler;

import java.io.*;
import java.net.*;
import Service.ClearService;
import Result.ClearResult;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;


public class ClearHandler extends Handler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            // Determine the HTTP request type (GET, POST, etc.).
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) { //Yep
                // Get the request body input stream
                InputStream reqBody = exchange.getRequestBody();
                // Read JSON string from the input stream
                String reqData = readString(reqBody);

                // Display/log the request JSON data
                System.out.println(reqData);

                //Actually Clearing things
                ClearService service = new ClearService();
                //Grabs the result of clearing it
                ClearResult result = service.getMyResults();
                if(result.isSuccess()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else{
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                Writer resBody  = new OutputStreamWriter(exchange.getResponseBody());
                Gson gson = new Gson();
                gson.toJson(result, resBody); //Writes it to the resBody
                resBody.close();
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
        }
    }
}