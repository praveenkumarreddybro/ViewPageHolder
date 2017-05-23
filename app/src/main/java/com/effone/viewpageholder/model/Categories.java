package com.effone.viewpageholder.model;

import java.util.ArrayList;

/**
 * Created by sumanth.peddinti on 5/10/2017.
 */


public class Categories
{
    private ArrayList<Items> Items;

    private String name;

    public ArrayList<Items> getItems ()
    {
        return Items;
    }

    public void setItems ( ArrayList<Items> Items)
    {
        this.Items = Items;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }


}


