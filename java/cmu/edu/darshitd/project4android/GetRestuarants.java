package cmu.edu.darshitd.project4android;

import android.os.AsyncTask;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import cmu.edu.darshitd.project4android.Data.Restaurant;


/*
 * This class provides capabilities to search for random restaurants from Web service given a cuisine and location(Default pittsburgh).
 * The method "search" is the entry to the class.
 * Network operations cannot be done from the UI thread, therefore this class makes use of an AsyncTask inner class that will do the network
 * operations in a separate worker thread.  However, any UI updates should be done in the UI thread so avoid any synchronization problems.
 * onPostExecution runs in the UI thread, and it calls the responseReady method to do the update.
 *
 */

public class GetRestuarants {

    MainActivity ma = null;

    /*
     * search is the public GetRestaurants method.  Its arguments are the cuisine,location and the MainActivity object that called it.  This provides a callback
     * path such that the responseReady method in that object is called when the data is available from the yelp api.
     */
    public void search(String cuisine, String location, MainActivity ma) {
        this.ma = ma;

        new AsynkYelpSearch().execute(cuisine, location);

    }

    /*
     * AsyncTask provides a simple way to use a thread separate from the UI thread in which to do network operations.
     * doInBackground is run in the helper thread.
     * onPostExecute is run in the UI thread, allowing for safe UI updates.
     */
    private class AsynkYelpSearch extends AsyncTask<String, String, String> {

        protected void onPostExecute(String response) {
            ma.responseReady(response);
        }

        @Override
        protected String doInBackground(String... strings) {
            String cuisine = strings[0];
            String location = strings[1];
            return getDetails(cuisine, location);
        }


        /*
         * Search Web service for the cuisine and location arguments, and return a json response that can be put in an Views
         */
        private String getDetails(String cuisine, String location) {
            String response = "";
            Restaurant r;
            JSONArray jsonArray = null;
            Map<Integer, Restaurant> restaurants = new HashMap<>();
            StringBuilder sb = new StringBuilder();

            //Url of web service app
            String urlString = "https://infinite-garden-32122.herokuapp.com/Task2Servlet/" + cuisine + "," + location;

            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String str;

                // Read each line of "in" until done, adding each to "response"
                while ((str = in.readLine()) != null) {
                    // str is one line of text readLine() strips newline characters
                    response += str;
                }

                in.close();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }
    }
}
