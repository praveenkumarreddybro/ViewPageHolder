package com.effone.viewpageholder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.effone.viewpageholder.adapter.MenuItemSummeryListAdapter;
import com.effone.viewpageholder.common.OnDataChangeListener;
import com.effone.viewpageholder.database.SqlOperation;
import com.effone.viewpageholder.model.CartItems;
import com.effone.viewpageholder.model.OrderToServer;
import com.effone.viewpageholder.model.OrderingMenu;
import com.effone.viewpageholder.model.TaxItems;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.effone.viewpageholder.database.DBConstant.ser;
import static com.effone.viewpageholder.database.DBConstant.serviceTax;
import static com.effone.viewpageholder.database.DBConstant.vatTax;

public class PlaceOrderActivity extends AppCompatActivity implements View.OnClickListener, OnDataChangeListener {
    private TextView mTvItemPrice, mTvItemCount, mTvChargers, mTvEstimatedTotal;
    private TextView mTvPlaceOrder;
    private EditText mEtTableNo;
    private ListView mLvItemSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
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

    private void showOrderItems() {

        sqlOperation = new SqlOperation(this);
        sqlOperation.open();
        cartItemses = sqlOperation.getCartItems(1);

        float totalCount = 0;
        int totalPrice = 0;
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
                OrderToServer orderToServer= new OrderToServer();
                orderToServer.setLocationId("LOC000001");
                orderToServer.setRestaurant_id("SYD00045");
                orderToServer.setOrder_id("SY56002019914");
                orderToServer.setTable_no(6);
                ArrayList<OrderingMenu> orderingMenus=new ArrayList<>();
                for (CartItems cartItems:cartItemses) {
                    orderingMenus.add(new OrderingMenu(cartItems.getItemMenuCatId(),cartItems.ItemQuantity));
                }
                orderToServer.setItems(orderingMenus);
                Gson gson= new Gson();
                String json=gson.toJson(orderToServer);
                pushDataToServer(orderToServer);

            } else {
                Toast.makeText(this, "Please enter the Table no", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void pushDataToServer(OrderToServer mTableName) {


    }

    @Override
    public void onDataChanged(int size) {
        setValuesInto();
    }
}
