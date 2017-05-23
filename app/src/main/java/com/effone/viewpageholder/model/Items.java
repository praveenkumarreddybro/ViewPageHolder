package com.effone.viewpageholder.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sumanth.peddinti on 5/10/2017.
 */

public class Items implements Serializable
{
    private ArrayList<Content> content;

    private String name;

    public ArrayList<Content> getContent ()
    {
        return content;
    }

    public void setContent (ArrayList<Content> content)
    {
        this.content = content;
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

