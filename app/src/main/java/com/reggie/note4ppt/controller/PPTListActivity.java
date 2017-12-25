package com.reggie.note4ppt.controller;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.Select;
import com.reggie.note4ppt.Model.OnRecyclerItemClickListener;
import com.reggie.note4ppt.Model.PPTListAdapter;
import com.reggie.note4ppt.R;
import com.reggie.note4ppt.db.Collection;
import com.reggie.note4ppt.db.PPT;
import com.reggie.note4ppt.db.PPT_Table;
import com.reggie.note4ppt.utils.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PPTListActivity extends Activity implements View.OnClickListener {

    public static final int EDITPPT = 1;

    private Context mContext;
    private TextView mTvBack;
    private TextView mTvSave;
    private SwipeRecyclerView mRvPptlist;
    private ImageView mAdd;
    private PPTListAdapter mPPTListAdapter;

    private List<PPT> pptList = new ArrayList<>();

    private Intent intentFromCollectionListActivity;

    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pptlist);
        initView();
    }

    private void initView() {
        mContext = this;
        intentFromCollectionListActivity = getIntent();

        mTvBack = (TextView) findViewById(R.id.tv_back);
        mTvBack.setOnClickListener(this);
        mTvSave = (TextView) findViewById(R.id.tv_save);
        mTvSave.setOnClickListener(this);
        mRvPptlist = (SwipeRecyclerView) findViewById(R.id.rv_pptlist);
        mAdd = (ImageView) findViewById(R.id.add);
        mAdd.setOnClickListener(this);


        mRvPptlist.setLayoutManager(new LinearLayoutManager(this));

        pptList = new ArrayList<>();

        long collection_id = intentFromCollectionListActivity.getLongExtra("collection_id", 0);
        List<PPT> temppptList = new Select().from(PPT.class).where(PPT_Table.collection_id.eq(collection_id)).queryList();
        pptList  = temppptList;

        mPPTListAdapter = new PPTListAdapter(pptList,mContext);

        mRvPptlist.setAdapter(mPPTListAdapter);
        mRvPptlist.addOnItemTouchListener(new OnRecyclerItemClickListener(mRvPptlist) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                //Toast.makeText(mContext,vh.getLayoutPosition() + "", Toast.LENGTH_SHORT).show();
                Intent intentToEdit = new Intent(mContext,SinglePPTActivity.class);
                intentToEdit.putExtra("position",vh.getAdapterPosition());
                intentToEdit.putExtra("openType",0);
                startActivityForResult(intentToEdit,EDITPPT);
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                //判断被拖拽的是否是前两个，如果不是则执行拖拽
                if (vh.getLayoutPosition() != 0 && vh.getLayoutPosition() != 1) {
                    mItemTouchHelper.startDrag(vh);
                    //获取系统震动服务
                    Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);//震动70毫秒
                    vib.vibrate(50);
                }
            }
        });

        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            /**
             * 是否处理滑动事件 以及拖拽和滑动的方向 如果是列表类型的RecyclerView的只存在UP和DOWN，如果是网格类RecyclerView则还应该多有LEFT和RIGHT
             * @param recyclerView
             * @param viewHolder
             * @return
             */
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                } else {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    final int swipeFlags = 0;
//                    final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //得到当拖拽的viewHolder的Position
                int fromPosition = viewHolder.getAdapterPosition();
                //拿到当前拖拽到的item的viewHolder
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(pptList, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(pptList, i, i - 1);
                    }
                }
                mPPTListAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
               // int position = viewHolder.getAdapterPosition();
               //mPPTListAdapter.notifyItemRemoved(position);
                //pptList.remove(position);
            }

            /**
             * 重写拖拽可用
             * @return
             */
            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }
            /**
             * 长按选中Item的时候开始调用
             *
             * @param viewHolder
             * @param actionState
             */
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            /**
             * 手指松开的时候还原
             * @param recyclerView
             * @param viewHolder
             */
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(0);
            }
        });

        mItemTouchHelper.attachToRecyclerView(mRvPptlist);

        mRvPptlist.setRightClickListener(new SwipeRecyclerView.OnRightClickListener(){

            @Override
            public void onRightClick(int position, String id) {
                pptList.remove(position);
//                myAdapter.notifyItemRemoved(position);
                mPPTListAdapter.notifyDataSetChanged();
                Toast.makeText(mContext, " position = " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_save:
                saveToDB();
                break;
            case R.id.add:
                PPT newppt = new PPT();
                newppt.words = "0";
                newppt.uri = "0";
                pptList.add(newppt);
                mPPTListAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void saveToDB(){
        PPT tempppt = new PPT();
        PPT ppt;
        long tempCollection_id = System.currentTimeMillis();
        for(int i = 0; i < pptList.size(); i++){
            ppt = pptList.get(i);
            tempppt.collection_id = tempCollection_id;
            tempppt.uri = ppt.uri;
            tempppt.words = ppt.words;
            tempppt.insert();
        }
        Collection tempCollection = new Collection();
        tempCollection.collection_id = tempCollection_id;
        tempCollection.user_id = 1;
        tempCollection.name = "nametest";
        tempCollection.address = "addresstest";
        tempCollection.time = System.currentTimeMillis();
        tempCollection.insert();
        Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
        finish();
        setResult(RESULT_OK);
    }

    /**
     * 编辑单个ppt完成之后的回调函数
     * 加入ppt的list中
     * 刷新列表
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EDITPPT)
            if(resultCode == SinglePPTActivity.EDIT_SUCCESS){
                int pos = data.getIntExtra("position",-1);
                String imageUri = data.getStringExtra("imageUri");
                String words = data.getStringExtra("words");
                pptList.get(pos).uri = imageUri;
                pptList.get(pos).words = words;
                mPPTListAdapter.notifyDataSetChanged();
            }
    }
}
