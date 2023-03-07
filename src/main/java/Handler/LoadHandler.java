package Handler;

import java.io.*;
import java.lang.reflect.Type;
import java.net.*;

import Result.LoadResult;
import Service.ClearService;
import Service.LoadService;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import Request.LoadRequest;

public class LoadHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {
            //Iys a post request
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                // Extract the JSON string from the HTTP request body

                // Get the request body input stream
                InputStream reqBody = exchange.getRequestBody();

                // Read JSON string from the input stream
                String reqData = readString(reqBody);

                // Display/log the request JSON data
                System.out.println(reqData);

                Gson gson = new Gson();
                //Clearing the database first, I think it uses the service
                ClearService myClear = new ClearService();
                //Create the request from Json
                LoadRequest request = (LoadRequest) gson.fromJson(reqData, LoadRequest.class);
                LoadService service = new LoadService(request);
                LoadResult result = service.getMyResult();

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream resBody = exchange.getResponseBody();
                gson.toJson((Object) result, (Type) resBody); //FIXME? TF?
                resBody.close();
						/*
						LoginRequest request = (LoginRequest)gson.fromJson(reqData, LoginRequest.class);

						LoginService service = new LoginService();
						LoginResult result = service.login(request);

						exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
						OutputStream resBody = exchange.getResponseBody();
						gson.toJson(result, resBody);
						resBody.close();
						*/
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