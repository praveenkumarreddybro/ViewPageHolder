package com.effone.viewpageholder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.effone.viewpageholder.MenuActivity;
import com.effone.viewpageholder.R;
import com.effone.viewpageholder.model.Content;
import com.effone.viewpageholder.model.Items;

import java.util.HashMap;

/**
 * Created by sumanth.peddinti on 5/22/2017.
 */

public class MenuCatagAdapter extends BaseAdapter{
    private  Context context;
    private HashMap<String,HashMap<String,Items[]>> list;
    private LayoutInflater inflater;

    public MenuCatagAdapter(Context context, int simple, HashMap<String, HashMap<String, Items[]>> list) {
        this.context=context;
        this.list=list;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final FilterViewHolders holder;
        if(convertView==null) {
            vi = inflater.inflate(R.layout.simple, null);
            holder = new FilterViewHolders();
            holder.mTvItemName=(TextView)vi.findViewById(R.id.textView);
            vi.setTag( holder );
        }else {
            holder = (FilterViewHolders) vi.getTag();
        }
            holder.mTvItemName.setText( (String)(list.keySet().toArray())[ position ]);

        return vi;
    }

    public static  class FilterViewHolders {
    public TextView mTvItemName;
    }
}
