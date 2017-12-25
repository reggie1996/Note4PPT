package com.reggie.note4ppt.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by 24073 on 2017/12/7.
 */

@Table(database = PPTDatabase.class)
public class PPT extends BaseModel {

    @PrimaryKey(autoincrement = true)
    public int id;

    @Column
    public long ppt_id;

    @Column
    public long collection_id;

    @Column
    public String uri;

    @Column
    public String words;

    @Override
    public String toString() {
        return "PPT{" +
                "id=" + id +
                ", ppt_id=" + ppt_id +
                ", collection_id=" + collection_id +
                ", uri='" + uri + '\'' +
                ", words='" + words + '\'' +
                '}';
    }
}
