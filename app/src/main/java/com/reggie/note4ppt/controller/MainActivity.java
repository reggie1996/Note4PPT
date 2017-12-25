package com.reggie.note4ppt.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.reggie.note4ppt.R;

public class MainActivity extends Activity implements View.OnClickListener {

    /**
     * 编辑保存到数据库
     */
    private Button mBtEdit;
    /**
     * 数据库数据
     */
    private Button mBtDb;

    /**
     * Collection列表
     */
    private Button mBtCollections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        mBtEdit = (Button) findViewById(R.id.bt_edit);
        mBtEdit.setOnClickListener(this);
        mBtDb = (Button) findViewById(R.id.bt_db);
        mBtDb.setOnClickListener(this);
        mBtCollections = (Button) findViewById(R.id.bt_collections);
        mBtCollections.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt_edit:
                Intent intent1 = new Intent(this, PPTListActivity.class);
                startActivity(intent1);
                break;
            case R.id.bt_db:
                Intent intent2 = new Intent(this, TestDB.class);
                startActivity(intent2);
                break;
            case R.id.bt_collections:
                Intent intent4 = new Intent(this, CollectionListActivity.class);
                startActivity(intent4);
                break;
        }
    }
}
