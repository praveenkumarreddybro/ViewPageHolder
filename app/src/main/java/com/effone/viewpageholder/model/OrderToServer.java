package com.effone.viewpageholder.model;

import java.util.ArrayList;

/**
 * Created by sumanth.peddinti on 5/12/2017.
 */

public class OrderToServer {
    private String locationId;
    private String restaurant_id;
    private int table_no;
    private String order_id;
    private ArrayList<OrderingMenu> Items;



    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public int getTable_no() {
        return table_no;
    }

    public void setTable_no(int table_no) {
        this.table_no = table_no;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public ArrayList<OrderingMenu> getItems() {
        return Items;
    }

    public void setItems(ArrayList<OrderingMenu> items) {
        Items = items;
    }
}
