package com.effone.viewpageholder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.effone.viewpageholder.adapter.OrderItemDetailsAdapter;
import com.effone.viewpageholder.adapter.TaxDetailsAdapter;
import com.effone.viewpageholder.common.AppPrefernces;
import com.effone.viewpageholder.model.Order_Items;
import com.effone.viewpageholder.model.TaxItems;

import java.util.ArrayList;

public class View_And_Pay_BillActivity extends AppCompatActivity implements View.OnClickListener {
    private AppPrefernces mAppPrefernces;
    private String order_id;
    private ListView mLvItemQuantity,mLvTaxQuality;
    private TaxDetailsAdapter taxDetailsAdapter;
    private OrderItemDetailsAdapter orderItemDetails;
    private  TextView mTvSubmit,mEtPromocodeMsg;
    private RadioGroup mRadioGroup;
    ArrayList<TaxItems> taxItemses;
    private EditText mEtPromoCodeNumber;
    private Button mBtApply;
    private TextView mTvBillSummary,mTvRestAddress,mTvBillDate,mTvBillNo,mTvOrderId,mTvOrderTotal;
    ArrayList<Order_Items> order_itemses;
    private  TextView mTvRestName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__and__pay__bill);
        mAppPrefernces=new AppPrefernces(this);
        order_id=mAppPrefernces.getORDER_ID();
        decarlation();
        
    }

    private void decarlation() {
        mTvRestName=(TextView)findViewById(R.id.tv_address_rest_names);
        mTvRestAddress=(TextView)findViewById(R.id.tv_address_rest_address);
        mTvBillDate=(TextView)findViewById(R.id.tv_bill_date);
        mTvBillNo=(TextView)findViewById(R.id.tv_bill_no);
        mTvOrderId=(TextView)findViewById(R.id.tv_order_id);
        mTvOrderTotal=(TextView)findViewById(R.id.tv_total_price);
        mTvBillSummary=(TextView)findViewById(R.id.tv_bill_table);
        mTvBillSummary.setText(getString(R.string.bill_summary));
        mEtPromoCodeNumber=(EditText)findViewById(R.id.et_promo_code);
        mEtPromocodeMsg=(TextView)findViewById(R.id.tv_price_of_total_msg);
        mBtApply=(Button)findViewById(R.id.bt_apply);
        mBtApply.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
