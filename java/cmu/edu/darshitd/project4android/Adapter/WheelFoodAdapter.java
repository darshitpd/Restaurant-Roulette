package cmu.edu.darshitd.project4android.Adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import cmu.edu.darshitd.project4android.Data.MenuItemData;
import cmu.edu.darshitd.project4android.R;
import github.hellocsl.cursorwheel.CursorWheelLayout;

/**
 * Date modified: 8th April 2019
 * Code used from https://github.com/BCsl/CursorWheelLayout and https://www.youtube.com/watch?v=AIb0MHZhuS0
 */
public class WheelFoodAdapter extends CursorWheelLayout.CycleWheelAdapter {

    private Context mContext;
    private List<MenuItemData> menuItems;
    private LayoutInflater inflater;
    private int gravity;

    //Constuctor
    public WheelFoodAdapter(Context mContext, List<MenuItemData> menuItems){
        this.mContext = mContext;
        this.menuItems = menuItems;
        gravity = Gravity.CENTER;
        inflater = LayoutInflater.from(mContext);
    }

    //Constructor
    public WheelFoodAdapter(Context mContext, List<MenuItemData> menuItems, int gravity){
        this.mContext = mContext;
        this.menuItems = menuItems;
        this.gravity = gravity;
        inflater = LayoutInflater.from(mContext);
    }
    //Return size of menuItems
    @Override
    public int getCount() {
        return menuItems.size();
    }

    //Setting values in the view
    @Override
    public View getView(View parent, int position) {
        MenuItemData itemData = getItem(position);
        View root = inflater.inflate(R.layout.wheel_text_layout, null, false);
        TextView textView = (TextView)root.findViewById(R.id.wheel_menu_item_tv);
        textView.setVisibility(View.VISIBLE);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setText(itemData.mTitle);
        if (textView.getLayoutParams() instanceof FrameLayout.LayoutParams)
            ((FrameLayout.LayoutParams)textView.getLayoutParams()).gravity = gravity;
        return root;
    }

    //Getting selected item from the wheel layout
    @Override
    public MenuItemData getItem(int position) {
        return menuItems.get(position);
    }
}
