package com.reggie.note4ppt.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * 用DBFlow生成的用户表,属性有id和用户的name
 * Created by 24073 on 2017/12/6.
 */


@Table(database = PPTDatabase.class)
public class User extends BaseModel{

    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    public String name;


    /**
     * @return 所有用户的List
     */
    public static List<User> getAllUsers(){
        List<User> users = new Select().from(User.class).queryList();
        return users;
    }

    /**
     * @param id 用户的id
     * @return 该id对应的用户对象
     */
    public static User getOneUser(long id){
        User user = new Select().from(User.class).where(User_Table.id.eq(id)).querySingle();
        return user;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
