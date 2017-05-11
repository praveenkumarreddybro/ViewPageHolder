package com.effone.viewpageholder.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.effone.viewpageholder.R;
import com.effone.viewpageholder.adapter.MenuListAdpater;
import com.effone.viewpageholder.common.OnDataChangeListener;
import com.effone.viewpageholder.common.OnHandClickInterface;
import com.effone.viewpageholder.common.UpdateableInterface;
import com.effone.viewpageholder.database.SqlOperation;
import com.effone.viewpageholder.model.CartItems;
import com.effone.viewpageholder.model.Content;
import com.effone.viewpageholder.model.Items;

import java.util.ArrayList;
import java.util.HashMap;


public class MenuViewFragment extends Fragment implements View.OnClickListener {
    ExpandableListView expListView;
    private Items[] mHisMenuItems;
    private  int position;
    private ViewPager mViewPager;

    private Context c = getActivity();


    private RequestQueue mQueue;
    private MenuListAdpater expListAdapter;

    private TextView mTvConfirm, mTvSumaryDetails;

    private TextView mTvHeading;
    private ImageView mImgLeftHand,mImgRightHand;
    private String heading;
    private String itemCountOfCart;
    private SqlOperation sqlOperation;

    public MenuViewFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.content_menu, container, false);
        mTvHeading = (TextView) root.findViewById(R.id.tv_menu_heading);
        mTvHeading.setText(heading);
        mImgLeftHand=(ImageView)root.findViewById(R.id.img_left_hand);
        mImgLeftHand.setOnClickListener(this);
        mImgRightHand=(ImageView)root.findViewById(R.id.img_right_hand);
        mImgRightHand.setOnClickListener(this);
        showingHands(position);
        mQueue = Volley.newRequestQueue(getActivity());
        sqlOperation=new SqlOperation(getActivity());
        expListView = (ExpandableListView) root.findViewById(R.id.elv_menu);
        String[] itemsname = new String[mHisMenuItems.length];
        HashMap<String, Content[]> mHashMap = new HashMap<>();
        for (int i = 0; i < mHisMenuItems.length; i++) {
            itemsname[i] = mHisMenuItems[i].getName();
            mHashMap.put(mHisMenuItems[i].getName(), mHisMenuItems[i].getContent());
        }
        MenuListAdpater adapter = new MenuListAdpater(getActivity(), android.R.layout.simple_list_item_1, heading, itemsname, mHashMap);
        expListView.setAdapter(adapter);




        return root;
    }

    private void showingHands(int position) {
    /*    switch (position){
            case 0:

                break;
            case 2:
                break;
            default:
                break;*/
        /*}*/
    if(position == 0){
        mImgLeftHand.setVisibility(View.INVISIBLE);
    } if(position == 2) {
        mImgRightHand.setVisibility(View.INVISIBLE);
    }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onClick(View view) {
     //   OnHandClickInterface onHandClickInterface =(OnHandClickInterface) this;

        if(view.getId() == R.id.img_left_hand){
//
            mViewPager.setCurrentItem(--position);
        }else {
            mViewPager.setCurrentItem(++position);
        }

    }

    public void setHeading(String heading, int position) {
        this.heading = heading;
        this.position=position;
    }
    public void setParentView(ViewPager pager){
        mViewPager=pager;
    }

    public void setValues(Items[] hisMenuItems) {
        mHisMenuItems = hisMenuItems;
    }




}
