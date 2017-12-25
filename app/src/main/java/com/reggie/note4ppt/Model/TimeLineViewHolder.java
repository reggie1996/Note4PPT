package com.reggie.note4ppt.Model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;
import com.reggie.note4ppt.R;

/**
 * Created by 24073 on 2017/12/12.
 */

public class TimeLineViewHolder extends RecyclerView.ViewHolder {
    public TimelineView mTimelineView;

    public ImageView theFirstPPT;
    public TextView collection_name;
    public TextView collection_address;
    public TextView collection_time;
    public TextView time_node;

    public TimeLineViewHolder(View itemView,int viewType) {
        super(itemView);
        mTimelineView = (TimelineView) itemView.findViewById(R.id.time_marker);
        theFirstPPT = (ImageView) itemView.findViewById(R.id.iv_thefirstppt);
        collection_name = (TextView) itemView.findViewById(R.id.collection_name);
        collection_address = (TextView) itemView.findViewById(R.id.collection_address);
        collection_time = (TextView) itemView.findViewById(R.id.collection_time);
        time_node = (TextView) itemView.findViewById(R.id.tv_time_node);
        mTimelineView.initLine(viewType);
    }

}
