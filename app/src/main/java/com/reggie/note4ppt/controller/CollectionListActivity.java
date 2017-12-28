package com.reggie.note4ppt.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.raizlabs.android.dbflow.sql.language.Select;
import com.reggie.note4ppt.Model.TimeLineAdapter;
import com.reggie.note4ppt.R;
import com.reggie.note4ppt.db.Collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 各个会议以时间轴的形式呈现
 */
public class CollectionListActivity extends Activity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private TimeLineAdapter mTimeLineAdapter;
    private List<Collection> collectionList = new ArrayList<>();
    private FloatingActionButton mFabAddCollection;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_list);
        initView();
    }

    private void initView() {
        mContext = this;
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_mine);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);

        collectionList = new Select().from(Collection.class).queryList();
        Collections.reverse(collectionList);//元素倒置

        mTimeLineAdapter = new TimeLineAdapter(collectionList);
        mTimeLineAdapter.setOnItemClickListener(new TimeLineAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mContext , DisplayPPTListActivity.class);
                intent.putExtra("collection_id",collectionList.get(position).collection_id);
                mContext.startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mTimeLineAdapter);

        mFabAddCollection = (FloatingActionButton) findViewById(R.id.fab_add_collection);
        mFabAddCollection.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.fab_add_collection:
                Intent intent = new Intent(this,PPTListActivity.class);
                intent.putExtra("openType",1);
                startActivityForResult(intent,1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initView();
    }
}
