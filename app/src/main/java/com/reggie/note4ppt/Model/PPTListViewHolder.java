package com.reggie.note4ppt.Model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.reggie.note4ppt.R;

/**
 * Created by 24073 on 2017/12/18.
 */

public class PPTListViewHolder extends RecyclerView.ViewHolder {

    public ImageView iv_uri;
    public TextView tv_words;
    public LinearLayout ll_item, ll_hidden;

    public PPTListViewHolder(View itemView) {
        super(itemView);
        iv_uri = (ImageView) itemView.findViewById(R.id.iv_uri);
        tv_words = (TextView) itemView.findViewById(R.id.tv_words);

        ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
        ll_hidden = (LinearLayout) itemView.findViewById(R.id.ll_hidden);
    }

}
