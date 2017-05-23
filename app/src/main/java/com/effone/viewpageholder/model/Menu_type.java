package com.effone.viewpageholder.model;

/**
 * Created by sumanth.peddinti on 5/22/2017.
 */

public class Menu_type
{
    private Categories[] categories;

    private String menu_cata_id;

    private String menu_cata_type;

    public Categories[] getCategories ()
    {
        return categories;
    }

    public void setCategories (Categories[] categories)
    {
        this.categories = categories;
    }

    public String getMenu_cata_id ()
    {
        return menu_cata_id;
    }

    public void setMenu_cata_id (String menu_cata_id)
    {
        this.menu_cata_id = menu_cata_id;
    }

    public String getMenu_cata_type ()
    {
        return menu_cata_type;
    }

    public void setMenu_cata_type (String menu_cata_type)
    {
        this.menu_cata_type = menu_cata_type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [categories = "+categories+", menu_cata_id = "+menu_cata_id+", menu_cata_type = "+menu_cata_type+"]";
    }
}

