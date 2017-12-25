package com.reggie.note4ppt.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * Created by 24073 on 2017/12/6.
 */

@Table(database = PPTDatabase.class)
public class Collection extends BaseModel{
    @PrimaryKey
    public long collection_id;

    @Column
    public long user_id;

    @Column
    public String name;

    @Column
    public long time;

    @Column
    public String address;

    /**
     *
     * @param _user_id 作者的id
     * @return 返回该作者的所有Collection
     */
    public static List<Collection> getAllCollectionsMadeByUser(long _user_id){
        List<Collection> collections = new Select().from(Collection.class).where(Collection_Table.user_id.eq(_user_id)).queryList();
        return collections;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "collection_id=" + collection_id +
                ", user_id=" + user_id +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", address='" + address + '\'' +
                '}';
    }
}
