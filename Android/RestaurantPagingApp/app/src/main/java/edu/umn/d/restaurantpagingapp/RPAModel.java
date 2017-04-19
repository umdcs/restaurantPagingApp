package edu.umn.d.restaurantpagingapp;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.service.restrictions.RestrictionsReceiver;
import android.util.Log;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jeff Smith on 3/15/2017.
 */

public class RPAModel implements ModelViewPresenterComponents.Model {

    /**
     * Here we will instantiate any of the classes that are used by the model
     * for calculations or otherwise.
     */
    public RPAModel(ModelViewPresenterComponents.RPAPresenterContract presenter){
        mPresenter = presenter;
    }

    /**
     * Gets one reservation from the waiting list
     * @param index index of the element in the list
     * @return  The element in the waiting list at index.
     */
    public Reservation getReservation(int index){return (Reservation)waitingReservations.get(index);}

    /**
     * Get one reservation from the seated list
     * @param index index of the element in the list
     * @return  The element in the seated list at index.
     */
    public Reservation getSeatedReservation(int index){return (Reservation)seatedReservations.get(index);}

    /**
     * Get a list of reservations from the waiting list
     * @return  The list of reservations
     */
    public List getReservations(){
        return waitingReservations;
    }

    /**
     * Get a list of reservation from the seated list
     * @return  The list of reservations
     */
    public List getSeatedReservations() {return seatedReservations;}


    /**
     * Create a reservation and add it to the waiting list.
     * @param name  The name of the reservation
     * @param partySize The size of the party
     * @param phoneNumber   The phone number of the customer
     * @param time  The time when the reservation was created
     * @return  A reference to the reservation that was created.
     */
    public Reservation createReservation(String name, int partySize, String phoneNumber, String time, boolean[] specialRequests, String otherRequest){
        
        Reservation res = new Reservation(name,partySize,phoneNumber,time,specialRequests,otherRequest);
        waitingReservations.add(res);
        restPOST(res);


        return res;

    }

    /**
     * Edit a reservation at a certain index in the waiting list.
     * @param index Index of the reservation
     * @param name  New name for the reservation
     * @param partySize New party size for the reservation
     * @param phoneNumber   New phone number for the reservation
     */
    public void editReservation(int index, String name, int partySize, String phoneNumber,boolean[] options, String otherRequests){
        Reservation res = (Reservation)waitingReservations.remove(index);
        restDELETE(res);
        res.setName(name);
        res.setPartySize(partySize);
        res.setPhoneNumber(phoneNumber);
        res.setOptions(options);
        res.setOtherRequest(otherRequests);
        waitingReservations.add(index, res);
        restPOST(res);
    }

    /**
     * Edit a reservation at a certain index in the waiting list.
     * @param index Index of the reservation
     * @param name  New name for the reservation
     * @param partySize New party size for the reservation
     * @param phoneNumber   New phone number for the reservation
     */
    public void editSeatedReservation(int index, String name, int partySize, String phoneNumber,boolean[] options, String otherRequests){
        Reservation res = (Reservation)seatedReservations.remove(index);
        restDELETE(res);
        res.setName(name);
        res.setPartySize(partySize);
        res.setPhoneNumber(phoneNumber);
        res.setOptions(options);
        res.setOtherRequest(otherRequests);
        seatedReservations.add(index, res);
        restPOST(res);
    }

    /**
     * Delete a reservation in the waiting list at a specific index
     * @param index Index of the element
     */
    public Reservation deleteReservation(int index){
        Reservation res = (Reservation)waitingReservations.remove(index);
        restDELETE(res);
        return res;
    }
    /**
     * Delete a reservation in the seated list at a specific index
     * @param index Index of the element
     */
    public Reservation deleteSeatedReservation(int index){
        Reservation res = (Reservation)seatedReservations.remove(index);
        restDELETE(res);
        return res;
    }

    /**
     * Move a reservation from the waiting list to the seated list
     * @param position  The position of the reservation to be moved
     */
    public void moveToSeated(int position){
        Reservation res = (Reservation)waitingReservations.remove(position);
        restDELETE(res);
        res.toSeated();
        seatedReservations.add(res);
        restPOST(res);

    }

    /**
     * Move a reservation from the seated list to the waiting list
     * @param position  The position of the reservation to be moved
     */
    public void moveToMaster(int position){
        Reservation res = (Reservation)seatedReservations.remove(position);
        restDELETE(res);
        res.toMaster();
        waitingReservations.add(res);
        restPOST(res);

    }

    public void refresh(){
        restGET();
    }

    private List waitingReservations = new ArrayList();
    private List seatedReservations = new ArrayList();
    private ModelViewPresenterComponents.RPAPresenterContract mPresenter;
    private String serverAddress = "http://ukko.d.umn.edu:4532";




    public void restGET(){
        Log.d("Server address","");
        new HTTPAsyncTask().execute(serverAddress+"/getData", "GET");
    }

    public void restPOST(Reservation res){
        JSONObject jsonParam = null;
        try {
            //Create JSONObject here
            jsonParam = new JSONObject();
            jsonParam.put("name", res.getName() );
            jsonParam.put("size", res.getPartySize());
            jsonParam.put("phoneNumber", res.getPhoneNumber());
            jsonParam.put("time", res.getTime());
            JSONArray options = new JSONArray();
            options.put(res.highChairRequested());
            options.put(res.boothRequested());
            options.put(res.wheelChairRequested());
            options.put(res.willSplitRequested());
            options.put(res.otherRequested());
            jsonParam.put("options", options);
            jsonParam.put("otherRequests",res.getOtherRequest());
            jsonParam.put("isSeated", res.isSeated());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("DEBUG:", jsonParam.toString());
        new HTTPAsyncTask().execute(serverAddress + "/postReservation", "POST", jsonParam.toString());
    }

    public void restPUT() {

        JSONObject jsonParam = null;
        try {
            //Create JSONObject here
            jsonParam = new JSONObject();
            jsonParam.put("name", "Melissa");
            jsonParam.put("Party size", "5");
            jsonParam.put("Phone number", "7632583591");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("DEBUG [PUT]:", jsonParam.toString());
        new HTTPAsyncTask().execute(serverAddress + "/putReservation", "PUT", jsonParam.toString());
    }

    public void restDELETE(Reservation res) {

        JSONObject jsonParam = null;
        try {
            //Create JSONObject here
            jsonParam = new JSONObject();
            jsonParam.put("name", res.getName() );
            jsonParam.put("size", res.getPartySize());
            jsonParam.put("phoneNumber", res.getPhoneNumber());
            jsonParam.put("time", res.getTime());
            JSONArray options = new JSONArray();
            options.put(res.highChairRequested());
            options.put(res.boothRequested());
            options.put(res.wheelChairRequested());
            options.put(res.willSplitRequested());
            options.put(res.otherRequested());
            jsonParam.put("options", options);
            jsonParam.put("otherRequests",res.getOtherRequest());
            jsonParam.put("isSeated", res.isSeated());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("DEBUG:", jsonParam.toString());
        new HTTPAsyncTask().execute(serverAddress+ "/deleteData", "DELETE", jsonParam.toString());
    }
    /**
     * HTTPAsyncTask for managing HTTP messages in a separate thread
     *
     * This class uses the AsyncTask class and extends it to open up
     * a HTTP connection to a HTTP Server. The class overrides the doInBackground
     * method to create the HTTP connection and make the Request while also
     * managing the response.  Upon completion of doInBackground, the task
     * calls the onPostExecute function.
     */
    @SuppressWarnings("JavaDoc")
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
                /**/
                // Make sure there is something in there
                List newWaitingList = new ArrayList();
                List newSeatedList = new ArrayList();

                // create the waiting list from the data on the server
                JSONArray waitingJson = (JSONArray)jsonData.get("waitList");
                for(int i = 0; i<waitingJson.length();i++){
                    JSONObject reservationObject = waitingJson.getJSONObject(i);
                    String name = reservationObject.getString("name");
                    int size = reservationObject.getInt("size");
                    String phoneNumber = reservationObject.getString("phoneNumber");
                    String time = reservationObject.getString("time");
                    JSONArray jsonOptions = reservationObject.getJSONArray("options");
                    boolean[] options = new boolean[5];
                    for(int j=0;j<5;j++){
                        Log.d("option",String.valueOf(jsonOptions.getBoolean(j)));
                        options[j] = jsonOptions.getBoolean(j);
                    }
                    String otherRequests = reservationObject.getString("otherRequests");



                    Reservation reservation = new Reservation(name,size,phoneNumber,time,options,otherRequests);
                    newWaitingList.add(reservation);
                }

                // create the seated list reservations out of the info from server
                JSONArray seatedJson = (JSONArray)jsonData.get("seatedList");
                for(int i = 0; i<seatedJson.length();i++){
                    JSONObject reservationObject = seatedJson.getJSONObject(i);
                    String name = reservationObject.getString("name");
                    int size = reservationObject.getInt("size");
                    String phoneNumber = reservationObject.getString("phoneNumber");
                    String time = reservationObject.getString("time");

                    JSONArray jsonOptions = reservationObject.getJSONArray("options");
                    boolean[] options = new boolean[5];
                    for(int j=0;j<5;j++){
                        options[j] = jsonOptions.getBoolean(j);
                    }

                    String otherRequests = reservationObject.getString("otherRequests");


                    Reservation reservation = new Reservation(name,size,phoneNumber,time,options,otherRequests);
                    reservation.toSeated();
                    newSeatedList.add(reservation);
                }

                waitingReservations = newWaitingList;
                seatedReservations = newSeatedList;

                mPresenter.notifyModelUpdated();

                Log.d("waitingList",String.valueOf(waitingReservations));

                Log.d("onPostExecute JSON:", jsonData.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
