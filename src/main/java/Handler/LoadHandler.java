package Handler;

import java.io.*;
import java.lang.reflect.Type;
import java.net.*;

import DataAccess.DataAccessException;
import Result.LoadResult;
import Service.ClearService;
import Service.LoadService;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import Request.LoadRequest;

public class LoadHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException{
        boolean success = false;

        try {
            //It's a post request
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
                if (result.isSuccess()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else{
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                Writer resBody  = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(result, resBody); //Writes it to the resBody
                resBody.close();
            }


        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
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