package edu.umn.d.restaurantpagingapp;

/**
 * Created by Jeff Smith on 4/5/2017.
 */

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * HTTPAsyncTask for managing HTTP messages in a separate thread
 *
 * This class uses the AsyncTask class and extends it to open up
 * a HTTP connection to a HTTP Server. The class overrides the doInBackground
 * method to create the HTTP connection and make the Request while also
 * managing the response.  Upon completion of doInBackground, the task
 * calls the onPostExecute function.
 */
class HTTPAsyncTask extends AsyncTask<String, Integer, String> {

    @Override
    protected String doInBackground(String... params) {

            /* The String... params argument represents an arbitrary sized
             * array.  In this case, we might have 2 or 3 parameters depending
             * on if the request type is a GET or POST
             *
             * params[0] contains the HOST and port number and route of the URI
             */
        Log.d("Debug:", "Attempting to connect to: " + params[0]);

            /* Java class to create a network connection to a HTTP
             * Server. InputStreams are used to process the incoming
             * network data.
             */
        HttpURLConnection serverConnection = null;
        InputStream is = null;

        try {
                /* The Java URL class is used to hold the URI */
            URL url = new URL(params[0]);

                /* We can open a connection to this URL now */
            serverConnection = (HttpURLConnection) url.openConnection();

                /* The second parameter, params[1] contains the TYPE of the HTTP
                 * request. It can be GET, POST, PUT or DELETE.
                 */
            serverConnection.setRequestMethod(params[1]);

                /* If the TYPE is POST, PUT or DELETE then we need to take
                 * the third parameter params[2] which contains the JSON data
                 * we need to place in the body of the HTTP message, and write
                 * that JSON data as a string to the network connection to the
                 * HTTP server.
                 */
            if (params[1].equals("POST") ||
                    params[1].equals("PUT") ||
                    params[1].equals("DELETE")) {
                Log.d("DEBUG POST/PUT/DELETE:", "In post: params[0]=" + params[0] + ", params[1]=" + params[1] + ", params[2]=" + params[2]);

                    /* Various server parameters need to set on HTTP connections that indicate the type
                     * of data that will be sent. In our case, we are sending JSON as output so need to
                     * set the Content-Type header attribute.
                     */
                serverConnection.setDoOutput(true);
                serverConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

                    /* Since params[2] contains the JSON String to send, we must also calculate the
                     * byte length of this data and set the Content-Length header attribute as well.
                     */
                serverConnection.setRequestProperty("Content-Length", "" +
                        Integer.toString(params[2].toString().getBytes().length));

                    /* Finally, the JSON data can be written out to the server by using
                     * a DataOutputStream that is created with the server's output stream.
                     */
                DataOutputStream out = new DataOutputStream(serverConnection.getOutputStream());
                    /* Write the json string data to the network */
                out.writeBytes(params[2].toString());

                    /* flush and close the output stream buffer */
                out.flush();
                out.close();
            }

                /* ************************
                 * HTTP RESPONSE Section
                 * All requests are followed by a response with HTTP
                 *
                 * Get the response code and determine what to do.
                 */
            int responseCode = serverConnection.getResponseCode();

            Log.d("Debug: ", "HTTP Response Code : " + responseCode);

                /* Get the input stream (what's coming from our server to the Android client)
                 * process the JSON data that's contained with it.
                 */
            is = serverConnection.getInputStream();

                /* This implementation is built so that ALL Responses send back a JSON data, as either
                 * the data you want from a GET Request or as confirmation of receiving the data
                 * on a POST, PUT, or DELETE Request.
                 */
            StringBuilder sb = new StringBuilder();
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

                /* At this point, the StringBuilder sb contains all the data that was in the
                 * body of the Response. Since we expect JSON to be in this, the string hopefully
                 * contains valid JSON data.  We need to return this string out of this
                 * function and the onPostExecute function will process it.
                 */
            return sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            serverConnection.disconnect();
        }

            /* If you receive this statement, you know there is an error */
        return "Should not get to this if the data has been sent/received correctly!";
    }

    /**
     *
     * @param result the result from the query
     */
    protected void onPostExecute(String result) {

            /* Take the result (a String) returned from the doInBackground function and
             * convert it to a JSONObject to test that it was a valid JSON data format.
             *
             * Then, simply set the text in the view to be the JSON Object as a
             * simple means to show the output.
             */

        try {
            JSONObject jsonData = new JSONObject( result );
            Log.d("onPostExecute JSON:", jsonData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
