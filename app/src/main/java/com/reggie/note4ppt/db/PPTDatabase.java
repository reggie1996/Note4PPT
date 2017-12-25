package com.reggie.note4ppt.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by 24073 on 2017/12/6.
 */
@Database(name = PPTDatabase.NAME,version = PPTDatabase.VERSION)
public class PPTDatabase {
    public static final String NAME = "PPTs";
    public static final int VERSION = 1;
}
