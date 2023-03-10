package Handler;

import java.io.*;
import java.net.*;
import Service.ClearService;
import Result.ClearResult;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;


public class ClearHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try {
            // Determine the HTTP request type (GET, POST, etc.).
            if (exchange.getRequestMethod().toLowerCase().equals("post")) { //Yep


                // Get the HTTP request headers
                Headers reqHeaders = exchange.getRequestHeaders();
                // Extract the JSON string from the HTTP request body

                // Get the request body input stream
                InputStream reqBody = exchange.getRequestBody();

                // Read JSON string from the input stream
                String reqData = readString(reqBody);

                // Display/log the request JSON data
                System.out.println(reqData);


                //LoginRequest request = (LoginRequest) gson.fromJson(reqData, LoginRequest.class);

                //Actually Clearing things
                ClearService service = new ClearService();
                //Grabs the result of clearing it
                ClearResult result = service.getMyResults();

                if(result.isSuccess()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    Writer resBody  = new OutputStreamWriter(exchange.getResponseBody());
                    Gson gson = new Gson();
                    gson.toJson(result, resBody); //Writes it to the resBody
                    resBody.close();
                    success = true;
                }

            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
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
        }
    }

    /*
        The readString method shows how to read a String from an InputStream.
    */
    private String readString(InputStream is) throws IOException {
        //TODO build a string handler
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