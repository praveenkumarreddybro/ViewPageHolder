package com.effone.viewpageholder.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.effone.viewpageholder.MainActivity;
import com.effone.viewpageholder.R;
import com.effone.viewpageholder.common.OnDataChangeListener;
import com.effone.viewpageholder.database.SqlOperation;
import com.effone.viewpageholder.model.CartItems;
import com.effone.viewpageholder.model.TaxItems;

import java.util.ArrayList;
import java.util.List;

import static com.effone.viewpageholder.database.DBConstant.ser;
import static com.effone.viewpageholder.database.DBConstant.serviceTax;
import static com.effone.viewpageholder.database.DBConstant.vatTax;

/**
 * Created by sarith.vasu on 17-04-2017.
 */

public class MenuItemSummeryListAdapter extends ArrayAdapter<CartItems> {
    private ArrayList<CartItems> orderedItemSummaries;
    private LayoutInflater inflater;
    private  Context mContext;
    private SqlOperation sqliteoperation;
    private  ArrayList<TaxItems> taxItemses;
    private OnDataChangeListener mOnDataChangeListener;
    public MenuItemSummeryListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<CartItems> orderedItemSummaries, ArrayList<TaxItems> taxItemses) {
        super(context, resource,  orderedItemSummaries);
        this.mContext=context;
        this.orderedItemSummaries =(ArrayList<CartItems>) orderedItemSummaries;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.taxItemses=taxItemses;
        this.mOnDataChangeListener= (OnDataChangeListener) context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vi = convertView;
        final MenuItemSummeryListAdapter.FilterViewHolder holder;

        if(convertView==null){
            vi = inflater.inflate(R.layout.summery_list_item, null);
            holder = new MenuItemSummeryListAdapter.FilterViewHolder();
            holder.tv_summery_item_name=(TextView)vi.findViewById(R.id.tv_summery_item_name);
            holder.tv_item_total_price = (TextView) vi.findViewById(R.id.tv_item_total_price);
            holder.tv_service_charges_value=(TextView)vi.findViewById(R.id.tv_service_charges_value);
            holder.tv_vat_value = (TextView) vi.findViewById(R.id.tv_vat_value);
            holder.tv_service_tax_value=(TextView)vi.findViewById(R.id.tv_service_tax_value);
            holder.tv_total = (TextView) vi.findViewById(R.id.tv_total);
            holder.tv_minus=(TextView) vi.findViewById(R.id.tv_minus);
            holder.tv_add=(TextView) vi.findViewById(R.id.tv_plus);
            holder.tv_quantity=(TextView) vi.findViewById(R.id.tv_qutity);
            holder.tv_close=(TextView)vi.findViewById(R.id.tv_close);
            vi.setTag( holder );
        }
        else
            holder = (MenuItemSummeryListAdapter.FilterViewHolder) vi.getTag();

        if (orderedItemSummaries.size() <= 0) {
            holder.tv_summery_item_name.setText("No Data");

        } else {
            /***** Get each Model object from Arraylist ********/
            final CartItems value = (CartItems) orderedItemSummaries.get(position);
//            final TaxItems taxItems=(TaxItems) taxItemses.get(position);
            /************  Set Model values in Holder elements ***********/
            holder.tv_summery_item_name.setText(value.getItemName()+" ( "+value.getItemQuantity()+" ) ");
            holder.tv_item_total_price.setText("$ "+value.getItemPrice());
            holder.tv_service_charges_value.setText("$ "+serviceTax);
            holder.tv_vat_value.setText("$ "+vatTax);
            holder.tv_service_tax_value.setText("$ "+ser);
            holder.tv_total.setText("$ "+totalAmmount(value.getItemQuantity()*value.getItemPrice(),serviceTax,vatTax,ser));
            holder.tv_quantity.setText(""+value.getItemQuantity());

        final int[] qty = new int[1];
        if(! holder.tv_quantity.getText().toString().equals(""))
            qty[0] =Integer.parseInt(holder.tv_quantity.getText().toString());
        holder.tv_close.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                sqliteoperation = new SqlOperation(mContext);
                sqliteoperation.open();
                if(sqliteoperation.getItemCountInCart()!=1) {
                    //we are deleteing the item from the cart based on the item_id and ItemName
                    sqliteoperation.cartItemDelete(value.getItemMenuCatId(), value.getItemName());
                }else{
                    sqliteoperation.cartItemDelete(value.getItemMenuCatId(), value.getItemName());
                    Intent intent=new Intent(mContext, MainActivity.class);
                    intent .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);
                }
                sqliteoperation.close();
                mOnDataChangeListener.onDataChanged(1);

            }
            });
        holder.tv_add.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if(qty[0] <99) {
                    qty[0]++;
                    holder.tv_quantity.setText("" + qty[0]);
                }

                sqliteoperation = new SqlOperation(mContext);
                sqliteoperation.open();
                sqliteoperation.AddOrSubstractProduct(value.getItemCatagerie(),value.getItemSubCat(),
                        value.getItemMenuCatId(),value.getItemName()
                        ,value.getItemIngred(),Float.parseFloat(String.valueOf(value.getItemPrice())),qty[0],1,1);

                sqliteoperation.close();
                mOnDataChangeListener.onDataChanged(1);
                // tvQuatity.setText( sqliteoperation.getCount(groupPosition, childPosition, details[0], Float.parseFloat(details[1]), 1));
            }
        });


        holder.tv_minus.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if(qty[0] >0) {
                    qty[0]--;
                    holder.tv_quantity.setText("" + qty[0]);
                }
          //      Toast.makeText(context, "click remove", Toast.LENGTH_LONG).show();
                sqliteoperation = new SqlOperation(mContext);
                sqliteoperation.open();
                sqliteoperation.AddOrSubstractProduct(value.getItemCatagerie(),value.getItemSubCat(),
                        value.getItemMenuCatId(),value.getItemName()
                        ,value.getItemIngred(),Float.parseFloat(String.valueOf(value.getItemPrice())),qty[0],1, 2);
                sqliteoperation.close();
                mOnDataChangeListener.onDataChanged(1);
                // tvQuatity.setText( sqliteoperation.getCount(groupPosition, childPosition, details[0], Float.parseFloat(details[1]), 2));

            }
        });
        }
        return vi;

    }

    private double totalAmmount(double price, double serviceCharges, double vat, double serviceTax) {
        double sum=price+serviceCharges+vat+serviceTax;
        return sum;
    }

    public static  class FilterViewHolder {
        TextView tv_summery_item_name;
        TextView tv_item_total_price;
        TextView tv_service_charges_value;
        TextView tv_vat_value;
        TextView tv_service_tax_value;
        TextView tv_total;
        TextView tv_minus;
        TextView tv_add;
        TextView tv_quantity;
        TextView tv_close;
    }
}

