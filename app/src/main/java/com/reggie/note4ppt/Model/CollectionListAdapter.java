package com.reggie.note4ppt.Model;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.reggie.note4ppt.R;
import com.reggie.note4ppt.db.Collection;
import com.reggie.note4ppt.db.PPT;
import com.reggie.note4ppt.db.PPT_Table;

import java.util.List;

/**
 * Created by 24073 on 2017/12/10.
 */

public class CollectionListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Collection> collections;

    public CollectionListAdapter(Context mContext, List<Collection> collections) {
        this.mContext = mContext;
        this.collections = collections;
    }

    @Override
    public int getCount() {
        return collections.size();
    }

    @Override
    public Object getItem(int i) {
        return collections.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view == null)
            view = LayoutInflater.from(mContext).inflate(R.layout.collection_list_item, null);

        Collection collection = collections.get(i);
        PPT theFirstPPT = new Select().from(PPT.class).where(PPT_Table.collection_id.eq(collection.collection_id)).querySingle();
        Uri imageUri = Uri.parse(theFirstPPT.uri);
        ImageView theFirstPPTofCollection = (ImageView) view.findViewById(R.id.iv_thefirstppt);
        Glide.with(mContext).load(imageUri).into(theFirstPPTofCollection);
        TextView collection_name = (TextView) view.findViewById(R.id.collection_name);
        collection_name.setText(collection.name + "");
        TextView collection_address = (TextView) view.findViewById(R.id.collection_address);
        collection_address.setText(collection.address);
        TextView collection_time = (TextView) view.findViewById(R.id.collection_time);
        collection_time.setText(collection.time + "");
                /*
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext , GetDataFromDBActivity.class);
                intent.putExtra("collection_id",collections.get(i).collection_id);
                mContext.startActivity(intent);
            }
        });
        */
        return view;
    }
}
