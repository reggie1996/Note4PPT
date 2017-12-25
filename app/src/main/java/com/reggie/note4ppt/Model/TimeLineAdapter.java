package com.reggie.note4ppt.Model;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.vipulasri.timelineview.TimelineView;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.reggie.note4ppt.R;
import com.reggie.note4ppt.db.Collection;
import com.reggie.note4ppt.db.PPT;
import com.reggie.note4ppt.db.PPT_Table;
import com.reggie.note4ppt.utils.TimeMillisToDateUtils;
import com.reggie.note4ppt.utils.VectorDrawableUtils;

import java.util.List;

/**
 * Created by 24073 on 2017/12/12.
 */

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineViewHolder> implements View.OnClickListener{

    private List<Collection> collectionList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private OnItemClickListener mOnItemClickListener = null;

    public TimeLineAdapter(List<Collection> collectionList) {
        this.collectionList = collectionList;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        View view;
        view = mLayoutInflater.inflate(R.layout.collection_list_item,parent,false);
        view.setOnClickListener(this);
        return new TimeLineViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {
        Collection collection = collectionList.get(position);
        holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_active, R.color.colorTimeLine));
        holder.collection_name.setText(collection.name);
        holder.collection_address.setText(collection.address);
        String time =TimeMillisToDateUtils.TimeToDate(collection.time);
        holder.collection_time.setText(time);
        PPT theFirstPPT = new Select().from(PPT.class).where(PPT_Table.collection_id.eq(collection.collection_id)).querySingle();
        Uri imageUri = Uri.parse(theFirstPPT.uri);
        Glide.with(mContext).load(imageUri).into(holder.theFirstPPT);

        String time2 = TimeMillisToDateUtils.TimeToDate2(collection.time);
        if (position == 0){
            holder.time_node.setVisibility(View.VISIBLE);
            holder.time_node.setText(time2);
        }else {
            Collection preCollection = collectionList.get(position-1);
            String preTime = TimeMillisToDateUtils.TimeToDate2(preCollection.time);
            if(!preTime.equals(time2)){
                holder.time_node.setVisibility(View.VISIBLE);
                holder.time_node.setText(time2);
            }
        }

        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return (collectionList != null ? collectionList.size() : 0);
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }

    //define interface
    public interface OnItemClickListener{
        void onItemClick(View view , int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }
}
