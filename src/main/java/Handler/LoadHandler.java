package Handler;

import java.io.*;
import java.net.*;

import Result.LoadResult;
import Service.ClearService;
import Service.LoadService;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import Request.LoadRequest;

public class LoadHandler extends Handler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException{

        try {
            //It's a post request
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {

                // Get the request body input stream
                InputStream reqBody = exchange.getRequestBody();

                // Read JSON string from the input stream
                String reqData = readString(reqBody);

                // Display/log the request JSON data
                System.out.println(reqData);

                Gson gson = new Gson();
                //Clearing the database first
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
}