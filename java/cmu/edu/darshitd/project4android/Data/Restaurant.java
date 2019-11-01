package cmu.edu.darshitd.project4android.Data;


/**
 * @author Darshit Doshi Last modified April 07, 2019 This class is used to create an class of
 * restaurant and use it's instances
 */
public class Restaurant {
    public String name, image_url, res_url, address, phone;
    //Constructor
    public Restaurant(String name, String image_url, String res_url, String address, String phone){
        this.name = name;
        this.image_url = image_url;
        this.res_url = res_url;
        this.address = address;
        this.phone = phone;
    }
}
