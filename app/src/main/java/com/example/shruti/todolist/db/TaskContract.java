package com.example.shruti.todolist.db;

import android.provider.BaseColumns;

/**
 * Created by Shruti on 26-04-2017.
 */

public class TaskContract {
    public static final String DB_NAME= "com.example.shruti.todolist.db";
    public static final int DB_VERSION= 1;

    public class TaskEntry implements BaseColumns{
        public static final String TABLE= "tasks";

        public static final String COL_TASK_TITLE= "title";
    }
}
