package com.effone.viewpageholder;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.effone.viewpageholder.adapter.HISMenuPageAdapter;
import com.effone.viewpageholder.common.OnDataChangeListener;
import com.effone.viewpageholder.common.OnHandClickInterface;
import com.effone.viewpageholder.common.UpdateableInterface;
import com.effone.viewpageholder.database.SqlOperation;
import com.effone.viewpageholder.model.CartItems;
import com.effone.viewpageholder.model.Items;
import com.effone.viewpageholder.model.Menu;
import com.effone.viewpageholder.model.Sample;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static com.effone.viewpageholder.common.URL.menu_url;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,UpdateableInterface,OnDataChangeListener,OnHandClickInterface {

    private String mJson;
    private Gson mGson;
    private RequestQueue mQueue;
    private Sample sample;
    private  ViewPager mVpMainMenu;

    private TextView mTvConfirm,mTvSummary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVpMainMenu=(ViewPager)findViewById(R.id.vp_main_menu);
        mVpMainMenu.setCurrentItem(1,true);
        mTvConfirm=(TextView)findViewById(R.id.tv_confirm);
        mTvSummary=(TextView)findViewById(R.id.tv_summary_details);
        mTvConfirm.setOnClickListener(this);
        mGson = new Gson();
        mQueue = Volley.newRequestQueue(this);
        showOrderItems();
        StringRequest stringRequest = new StringRequest(menu_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mJson = response;
                        sample = mGson.fromJson(mJson, Sample.class);

                        HashMap<String ,Items[]> pagerItem=new LinkedHashMap<>();
                        for (int i = 0; i <sample.getMenu().getCategories().length ; i++) {

                            pagerItem.put(sample.getMenu().getCategories()[i].getName(),sample.getMenu().getCategories()[i].getItems());
                        }
                        HISMenuPageAdapter menuPageAdapter= new HISMenuPageAdapter(getSupportFragmentManager(),pagerItem);
                        mVpMainMenu.setAdapter(menuPageAdapter);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
        mQueue.add(stringRequest);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_confirm:
                Intent intent=new Intent(this,PlaceOrderActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
   SqlOperation sqlOperation;
    ArrayList<CartItems>  cartItemses;
    private void showOrderItems() {
        sqlOperation=new SqlOperation(this);
        sqlOperation.open();
        cartItemses = sqlOperation.getCartItems(1);
        int totalCount=0;
        int totalPrice=0;
        sqlOperation.close();
        for (int i = 0; i <cartItemses.size(); i++) {
            totalCount +=  cartItemses.get(i).getItemQuantity();
            totalPrice += cartItemses.get(i).getItemPrice()*cartItemses.get(i).getItemQuantity();

        }
        mTvSummary.setText(totalCount+" Items in Cart \n "+ totalPrice+" Plus charges");
    }

    @Override
    public void update() {
        showOrderItems();
    }

    @Override
    protected void onStart() {
        super.onStart();
        showOrderItems();
    }

    @Override
    public void onDataChanged(int size) {
        showOrderItems();
    }

    @Override
    public void getFragmentPosition(int postion) {
        mVpMainMenu.setCurrentItem(mVpMainMenu.getCurrentItem()+1, true);

    }
}
