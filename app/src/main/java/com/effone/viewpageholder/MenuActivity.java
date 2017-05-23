package com.effone.viewpageholder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.effone.viewpageholder.adapter.MenuCatagAdapter;
import com.effone.viewpageholder.model.Items;
import com.effone.viewpageholder.model.Sample;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MenuActivity extends AppCompatActivity {
    private ListView list_view;
    private Gson mGson;
    private String urls = "http://192.168.2.44/android_web_api/include/sample.json";
    private RequestQueue mQueue;
    private String mJson;
    public Sample sample;
     HashMap<String, HashMap<String ,ArrayList<Items> > > list;
    public     HashMap<String,ArrayList<Items> >pagerItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        list_view = (ListView) findViewById(R.id.list_view);
        mGson = new Gson();
        mQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(urls, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mJson = response;
                sample = mGson.fromJson(mJson, Sample.class);

                HashMap<String ,ArrayList<Items> > pagerItem=new LinkedHashMap<>();
                list= new HashMap<>();
                for (int i = 0; i <sample.getMenu().getMenu_type().length ; i++) {
                    ArrayList<Items> itemses=new ArrayList<>();
                    for (int j = 0; j < sample.getMenu().getMenu_type()[i].getCategories().length; j++) {
                        itemses=sample.getMenu().getMenu_type()[i].getCategories()[j].getItems();
                        pagerItem.put(sample.getMenu().getMenu_type()[i].getCategories()[j].getName(),itemses);

                    }
                    list.put(sample.getMenu().getMenu_type()[i].getMenu_cata_type(),pagerItem);

                }
                MenuCatagAdapter menuCatagier=new MenuCatagAdapter(MenuActivity.this,R.layout.simple,list);
                list_view.setAdapter(menuCatagier);
                list_view.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View v, int position,
                                            long arg3)
                    {
                        pagerItems  =list.get(sample.getMenu().getMenu_type()[position].getMenu_cata_type());
                        // assuming string and if you want to get the value on click of list item
                        // do what you intend to do on click of listview row
                        Intent intent=new Intent(MenuActivity.this,MainActivity.class);
                        intent.putExtra("map",pagerItems);
                        startActivity(intent);

                    }
                });
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MenuActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        mQueue.add(stringRequest);


    }
}
