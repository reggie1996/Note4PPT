package com.reggie.note4ppt.controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.Select;
import com.reggie.note4ppt.R;
import com.reggie.note4ppt.db.Collection;
import com.reggie.note4ppt.db.PPT;
import com.reggie.note4ppt.db.User;

import java.util.List;

public class TestDB extends Activity implements View.OnClickListener {
    int i = 0;

    /**
     * TextView
     */
    private TextView mTextView;
    /**
     * Button
     */
    private Button mButton3;
    /**
     * adduser
     */
    private Button mButtonAdduser;
    /**
     * show
     */
    private Button mShow;
    /**
     * deluser
     */
    private Button mDeluser;
    /**
     * delclt
     */
    private Button mDelclt;
    /**
     * addclt
     */
    private Button mAddclt;
    /**
     * delppt
     */
    private Button mDelppt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_db);
        initView();

    }

    private void initView() {
        mTextView = (TextView) findViewById(R.id.textView);
        mButton3 = (Button) findViewById(R.id.addclt);
        mButton3.setOnClickListener(this);
        mButtonAdduser = (Button) findViewById(R.id.button_adduser);
        mButtonAdduser.setOnClickListener(this);
        mShow = (Button) findViewById(R.id.show);
        mShow.setOnClickListener(this);
        mDeluser = (Button) findViewById(R.id.deluser);
        mDeluser.setOnClickListener(this);
        mDelclt = (Button) findViewById(R.id.delclt);
        mDelclt.setOnClickListener(this);
        mAddclt = (Button) findViewById(R.id.addclt);
        mAddclt.setOnClickListener(this);
        mDelppt = (Button) findViewById(R.id.delppt);
        mDelppt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.addclt:
                addclt();
                break;
            case R.id.button_adduser:
                adduser();
                break;
            case R.id.show:
                show();
                break;
            case R.id.deluser:
                deluser();
                break;
            case R.id.delclt:
                delclt();
                break;
            case R.id.delppt:
                delppt();
                break;
        }
    }

    private void delppt() {
        PPT ppt = new Select().from(PPT.class).querySingle();
        if (ppt != null)
            ppt.delete();
        show();
    }

    private void delclt() {
        Collection collection = new Select().from(Collection.class).querySingle();
        if (collection != null)
            collection.delete();
        show();
    }

    private void deluser() {
        User user = new Select().from(User.class).querySingle();
        if (user != null)
            user.delete();
        show();
    }

    private void show() {

        //打印三张表中的东西
        List<User> users = new Select().from(User.class).queryList();
        String s = "";
        for(int i = 0; i<users.size(); i++){
            s += users.get(i);
            s += "\n";
        }
        s += "\n";
        s += "\n";
        List<Collection> collections = new Select().from(Collection.class).queryList();
        for(int i = 0; i<collections.size(); i++){
            s += collections.get(i);
            s += "\n";
        }
        s += "\n";
        s += "\n";
        List<PPT> ppts = new Select().from(PPT.class).queryList();
        for (int i = 0; i < ppts.size(); i++) {
            s += ppts.get(i);
            s += "\n";
        }

        mTextView.setText(s);

    }

    private void adduser() {
        User user = new User();
        user.name = "user";
        user.insert();
        show();
    }


    private void addclt() {
        Collection collection = new Collection();
        collection.name = "collection";
        collection.collection_id = i;
        i = i + 2;
        collection.user_id = 1;
        collection.insert();
        show();
    }
}
