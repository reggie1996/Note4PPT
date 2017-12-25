package com.reggie.note4ppt.Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.reggie.note4ppt.R;
import com.reggie.note4ppt.db.PPT;

import java.util.List;

/**
 * Created by 24073 on 2017/12/18.
 */

public class PPTListAdapter extends RecyclerView.Adapter<PPTListViewHolder>{

    private List<PPT> pptList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;


    public PPTListAdapter(List<PPT> PPTList,Context context) {
        this.mContext = context;
        this.pptList = PPTList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public PPTListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PPTListViewHolder(mLayoutInflater.inflate(R.layout.ppt_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(PPTListViewHolder holder, final int position) {

        PPT ppt = pptList.get(position);
        Glide.with(mContext).load(ppt.uri).placeholder(R.mipmap.aaa).into(holder.iv_uri);
        holder.tv_words.setText(ppt.words);
    }

    @Override
    public int getItemCount() {
        return (pptList != null ? pptList.size() : 0);
    }


}
