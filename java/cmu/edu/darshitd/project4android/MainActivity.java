package cmu.edu.darshitd.project4android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cmu.edu.darshitd.project4android.Adapter.WheelFoodAdapter;
import cmu.edu.darshitd.project4android.Data.MenuItemData;
import github.hellocsl.cursorwheel.CursorWheelLayout;

/**
 * @author Darshit Doshi
 * Last modified April 07, 2019
 * This is the MainActivity class. It handles all the requests from the user and sets the SingleResaurant class when the response is ready
 */
public class MainActivity extends AppCompatActivity implements CursorWheelLayout.OnMenuSelectedListener {

    CursorWheelLayout wheel_text;
    List<MenuItemData> foodItems;
    EditText locationET;
    final MainActivity ma = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize the roulette view
        initViews();
        //Load the data
        loadData();

        wheel_text.setOnMenuSelectedListener(this);
    }

    //Start new intent of singlerestaurant class when it gets a response
    public void responseReady(String response){
        Intent intent = new Intent(MainActivity.this, SingleRestaurant.class);
        intent.putExtra("response", response);
        startActivity(intent);
    }

    //Load data in the roulette layout
    private void loadData() {
        foodItems = new ArrayList<>();
        foodItems.add(new MenuItemData(""));
        foodItems.add(new MenuItemData("Pizza"));
        foodItems.add(new MenuItemData("Chinese"));
        foodItems.add(new MenuItemData("Mexican"));
        foodItems.add(new MenuItemData("Burgers"));
        foodItems.add(new MenuItemData("Thai"));
        foodItems.add(new MenuItemData("Sandwich"));
        foodItems.add(new MenuItemData("Indian"));
        foodItems.add(new MenuItemData("Sushi Bars"));
        foodItems.add(new MenuItemData("Steak"));
        foodItems.add(new MenuItemData("Italian"));
        foodItems.add(new MenuItemData("Korean"));
        foodItems.add(new MenuItemData("Seafood"));
        foodItems.add(new MenuItemData("Japanese"));
        foodItems.add(new MenuItemData("American"));
        WheelFoodAdapter adapter = new WheelFoodAdapter(getBaseContext(), foodItems);
        wheel_text.setAdapter(adapter);
    }

    //Initialize the roullete view
    private void initViews() {
        wheel_text = (CursorWheelLayout)findViewById(R.id.wheel_text);
    }

    /**
     * Handle when a value is selected in the view. If it's not null, it finds for restaurants details
     * with selected cuisine and location. If location is null, it takes a default as pittsburgh.
     *
     */
    @Override
    public void onItemSelected(CursorWheelLayout parent, View view, int pos){

        GetRestuarants gr = new GetRestuarants();
        String location = "pittsburgh";
        if(!foodItems.get(pos).mTitle.equals("")){

            String cuisine =foodItems.get(pos).mTitle.toString();
            locationET = (EditText)findViewById(R.id.locationET);
            if(!locationET.getText().toString().matches("")) {
                Toast.makeText(getBaseContext(), "location", Toast.LENGTH_SHORT).show();
                location = locationET.getText().toString();
            }
            gr.search(cuisine,location, ma);
            Toast.makeText(getBaseContext(), "Searching for restaurants with "+foodItems.get(pos).mTitle+" cuisine in "+ location ,Toast.LENGTH_SHORT).show();

        }

    }

}
