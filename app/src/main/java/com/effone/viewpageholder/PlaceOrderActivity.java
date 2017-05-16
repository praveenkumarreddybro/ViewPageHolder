package com.effone.viewpageholder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.effone.viewpageholder.adapter.MenuItemSummeryListAdapter;
import com.effone.viewpageholder.common.AppPrefernces;
import com.effone.viewpageholder.common.OnDataChangeListener;
import com.effone.viewpageholder.database.SqlOperation;
import com.effone.viewpageholder.model.CartItems;
import com.effone.viewpageholder.model.OrderToServer;
import com.effone.viewpageholder.model.OrderingMenu;
import com.effone.viewpageholder.model.TaxItems;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.effone.viewpageholder.common.URL.place_order_url;
import static com.effone.viewpageholder.database.DBConstant.ser;
import static com.effone.viewpageholder.database.DBConstant.serviceTax;
import static com.effone.viewpageholder.database.DBConstant.vatTax;

public class PlaceOrderActivity extends AppCompatActivity implements View.OnClickListener, OnDataChangeListener {
    private TextView mTvItemPrice, mTvItemCount, mTvChargers, mTvEstimatedTotal;
    private TextView mTvPlaceOrder;
    private EditText mEtTableNo;
    private ListView mLvItemSummary;
    private AppPrefernces appPrefernces;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        appPrefernces=new AppPrefernces(this);
        decalartion();
    }

    private void decalartion() {
        mTvItemCount = (TextView) findViewById(R.id.tv_items);
        mTvItemPrice = (TextView) findViewById(R.id.tv_items_price);
        mTvChargers = (TextView) findViewById(R.id.tv_charger_price);
        mTvEstimatedTotal = (TextView) findViewById(R.id.tv_estimated_price);
        mTvPlaceOrder = (TextView) findViewById(R.id.tv_place_order);
        mEtTableNo = (EditText) findViewById(R.id.et_table_no);
        mLvItemSummary = (ListView) findViewById(R.id.item_summery_list);
        mTvPlaceOrder.setOnClickListener(this);
        setValuesInto();
    }

    private void setValuesInto() {
        showOrderItems();
    }

    SqlOperation sqlOperation;
    ArrayList<CartItems> cartItemses;
    float totalCount = 0;
    int totalPrice = 0;
    private void showOrderItems() {

        sqlOperation = new SqlOperation(this);
        sqlOperation.open();
        cartItemses = sqlOperation.getCartItems(1);


        sqlOperation.close();
        for (int i = 0; i < cartItemses.size(); i++) {
            totalCount += cartItemses.get(i).getItemQuantity();
            totalPrice += cartItemses.get(i).getItemPrice() * cartItemses.get(i).getItemQuantity();

        }
        mTvItemPrice.setText("" + totalPrice);
        mTvItemCount.setText("Items (" + Math.round(totalCount) + ")");
        mTvChargers.setText("" + taxAmountCalculation());
        double sum = totalPrice + taxAmountCalculation();
        mTvEstimatedTotal.setText("" + sum);
        getTaxDetails(totalPrice);
        getOrderItemsList();

    }

    private void getOrderItemsList() {
        MenuItemSummeryListAdapter menuItemSummeryListAdapter = new MenuItemSummeryListAdapter(this, android.R.layout.simple_list_item_1, cartItemses, taxItemses);
        mLvItemSummary.setAdapter(menuItemSummeryListAdapter);

    }

    private float taxAmountCalculation() {
        return (float) (serviceTax + vatTax + ser);
    }


    ArrayList<TaxItems> taxItemses;

    private void getTaxDetails(float totalByItems) {

        taxItemses = new ArrayList<TaxItems>();
        TaxItems res1 = new TaxItems("Total before Tax", totalByItems);
        TaxItems res2 = new TaxItems("Service Charges", serviceTax);
        TaxItems res3 = new TaxItems("Service Tax", vatTax);
        TaxItems res4 = new TaxItems("VAT Tax", ser);
        taxItemses.add(res1);
        taxItemses.add(res2);
        taxItemses.add(res3);
        taxItemses.add(res4);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tv_place_order) {
            String mTableName = mEtTableNo.getText().toString().trim();
            if (mTableName.length() > 2) {
                sqlOperation = new SqlOperation(this);
                sqlOperation.open();
                sqlOperation.setFlagaUpdate();
                sqlOperation.close();
                ArrayList<OrderToServer> orderToServers= new ArrayList<>();
                OrderToServer orderToServer= new OrderToServer();
                orderToServer.setLocationId(22);
                orderToServer.setRestaurant_id(555);
                if(appPrefernces.getORDER_ID() != null)
                orderToServer.setOrder_id(appPrefernces.getORDER_ID());
                else
                    orderToServer.setOrder_id("");
                orderToServer.setTable_no(Integer.parseInt(mTableName));
                orderToServer.setTotal_price(totalPrice);
                ArrayList<OrderingMenu> orderingMenus=new ArrayList<>();
                for (CartItems cartItems:cartItemses) {
                    orderingMenus.add(new OrderingMenu(cartItems.getItemMenuCatId(),cartItems.ItemQuantity));
                }
                orderToServer.setItems(orderingMenus);
                orderToServers.add(orderToServer);
                Gson gson= new Gson();
                String json=gson.toJson(orderToServers);
                pushDataToServer(json);

            } else {
                Toast.makeText(this, "Please enter the Table no", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void pushDataToServer(final String mTableName) {
        StringRequest req = new StringRequest(Request.Method.POST, place_order_url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String value = response;
                        if (!value.equals("")) {
                            Toast.makeText(PlaceOrderActivity.this," "+response,Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(PlaceOrderActivity.this,ConfirmationActivity.class);
                            intent.putExtra("Order_id",response);
                            startActivity(intent);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PlaceOrderActivity.this," "+error,Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return mTableName.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");

                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(req);
    }

    @Override
    public void onDataChanged(int size) {
        setValuesInto();
    }
}
