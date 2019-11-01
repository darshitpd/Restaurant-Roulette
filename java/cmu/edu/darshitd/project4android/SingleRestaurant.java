package cmu.edu.darshitd.project4android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cmu.edu.darshitd.project4android.Data.Restaurant;


/**
 * @author Darshit Doshi Last modified April 07, 2019 This class is used to
 * display information of the restaurant in the respective xml and handle right swipes.
 */
public class SingleRestaurant extends AppCompatActivity implements GestureDetector.OnGestureListener {

    TextView singleRestaurantName;
    ImageView singleRestaurantImage;
    TextView singleRestaurantAddress;
    TextView singleRestaurantPhone;
    Restaurant res;

    Map<Integer, Restaurant> restaurants;

    public static final int SWIPE_THRESHOLD = 100;
    public static final int SWIPE_VELOCITY_THRESHOLD = 100;

    private GestureDetector gestureDetector;

    /**
     * Set values fetched from the web service
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_restaurant);

        gestureDetector = new GestureDetector(this);

        restaurants = new HashMap<>();
        String response = getIntent().getStringExtra("response");
        JSONArray jsonArray=null;
        Restaurant r = null;

        try {
            JSONObject jsnobj = new JSONObject(response);
            jsonArray = jsnobj.getJSONArray("restaurants");
            int len = jsonArray.length();
            for (int i = 0; i < len; i++) {
                JSONObject explrObject = jsonArray.getJSONObject(i);
                String name = explrObject.getString("name");
                String image_url = explrObject.getString("image_url");
                String res_url = explrObject.getString("res_url");
                String address = explrObject.getString("address");
                String phone = explrObject.getString("phone");
                r = new Restaurant(name, image_url, res_url, address, phone);
                restaurants.put(i, r);

            }

        } catch (JSONException ex) {
        }

        if(restaurants.size() > 1) {
            Random rand = new Random();
            int pos = rand.nextInt(restaurants.size());

            res = restaurants.get(pos);

            singleRestaurantName = (TextView) findViewById(R.id.singleRestaurantName);
            singleRestaurantName.setText(res.name);

            singleRestaurantAddress = (TextView) findViewById(R.id.singleRestaurantAddress);
            singleRestaurantAddress.setText(res.address);

            singleRestaurantPhone = (TextView) findViewById(R.id.singleRestaurantPhone);
            singleRestaurantPhone.setText(res.phone);

            singleRestaurantImage = (ImageView) findViewById(R.id.singleRestaurantImage);
            Glide.with(SingleRestaurant.this).load(res.image_url).override(1000, 600).centerCrop().into(singleRestaurantImage);
        }
        else{
            singleRestaurantName = (TextView) findViewById(R.id.singleRestaurantName);
            singleRestaurantName.setText("Sorry, no restaurants found in this area!");
            TextView winner = (TextView)findViewById(R.id.winner);
            TextView subheader = (TextView)findViewById(R.id.subheader);
            winner.setVisibility(View.INVISIBLE);
            subheader.setVisibility(View.INVISIBLE);
            singleRestaurantAddress = (TextView) findViewById(R.id.singleRestaurantAddress);
            singleRestaurantAddress.setVisibility(View.INVISIBLE);

            singleRestaurantPhone = (TextView) findViewById(R.id.singleRestaurantPhone);
            singleRestaurantPhone.setVisibility(View.INVISIBLE);

            singleRestaurantImage = (ImageView) findViewById(R.id.singleRestaurantImage);
            singleRestaurantImage.setVisibility(View.INVISIBLE);

            ImageView locationImage = findViewById(R.id.locationImage);
            ImageView phoneImage = findViewById(R.id.phoneImage);
            Button moreButton = findViewById(R.id.moreButton);

            locationImage.setVisibility(View.INVISIBLE);
            phoneImage.setVisibility(View.INVISIBLE);
            moreButton.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /**
     * Code taken from stackOverflow https://stackoverflow.com/questions/4139288/android-how-to-handle-right-to-left-swipe-gestures
     * @param downEvent
     * @param moveEvent
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float velocityX, float velocityY) {
        boolean result = false;
        float diffY = moveEvent.getY() - downEvent.getY();
        float diffX = moveEvent.getX() - downEvent.getX();
        // which was greater?  movement across Y or X?
        if (Math.abs(diffX) > Math.abs(diffY)) {
            // right or left swipe
            if (Math.abs(diffX)> SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffX > 0) {
                    onSwipeRight();
                }
                result = true;
            }
        }
        return result;
    }

    /**
     * Show next random restaurant if user swipes right
     */
    private void onSwipeRight() {

        if(restaurants.size() > 1) {
            Random rand = new Random();
            int pos = rand.nextInt(restaurants.size());

            res = restaurants.get(pos);

            singleRestaurantName.setText(res.name);
            singleRestaurantAddress.setText(res.address);

            singleRestaurantPhone.setText(res.phone);
            Glide.with(SingleRestaurant.this).load(res.image_url).override(1000, 600).centerCrop().into(singleRestaurantImage);
        }
    }

    /**
     * Handle the onClick button and open the yelp url
     * @param v
     */
    public void moreYelpOnClick(View v){
        String url = res.res_url;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    /**
     * Handle the onClick button and make a new intent of main activity
     * @param v
     */
    public void tryAgainOnClick(View v){
        Intent i = new Intent(SingleRestaurant.this, MainActivity.class);
        startActivity(i);
    }

}
