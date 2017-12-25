package com.reggie.note4ppt.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.raizlabs.android.dbflow.sql.language.Select;
import com.reggie.note4ppt.Model.CardPagerAdapter;
import com.reggie.note4ppt.R;
import com.reggie.note4ppt.db.PPT;
import com.reggie.note4ppt.db.PPT_Table;
import com.reggie.note4ppt.utils.ShadowTransformer;

import java.util.ArrayList;
import java.util.List;

public class DisplayPPTListActivity extends Activity {

    private Context mContext;
    private List<PPT> pptList = new ArrayList<>();
    private Intent intentFromCollectionListActivity;

    private ViewPager mViewPager;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_pptlist);
        initView();


    }

    private void initView() {
        mContext = this;
        intentFromCollectionListActivity = getIntent();
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mCardAdapter = new CardPagerAdapter(mContext);

        long collection_id = intentFromCollectionListActivity.getLongExtra("collection_id", 0);
        pptList = new Select().from(PPT.class).where(PPT_Table.collection_id.eq(collection_id)).queryList();
        for(PPT ppt : pptList){
            mCardAdapter.addCardItem(ppt);
        }

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);

    }
}
