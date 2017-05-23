package com.effone.viewpageholder.model;

/**
 * Created by sumanth.peddinti on 5/10/2017.
 */


public class Menu
{

    private Menu_type[] menu_type;

    private String restaurant_id;

    private String location_id;

    public Menu_type[] getMenu_type ()
    {
        return menu_type;
    }

    public void setMenu_type (Menu_type[] menu_type)
    {
        this.menu_type = menu_type;
    }

    public String getRestaurant_id ()
    {
        return restaurant_id;
    }

    public void setRestaurant_id (String restaurant_id)
    {
        this.restaurant_id = restaurant_id;
    }

    public String getLocation_id ()
    {
        return location_id;
    }

    public void setLocation_id (String location_id)
    {
        this.location_id = location_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [menu_type = "+menu_type+", restaurant_id = "+restaurant_id+", location_id = "+location_id+"]";
    }
}