package com.example.shruti.todolist;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shruti.todolist.db.TaskContract;
import com.example.shruti.todolist.db.TaskDbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG= "MainActivity";
    private TaskDbHelper mHelper;
    private ListView mTaskListView;
    private ArrayAdapter<String> mAdapter;
    Button buttonAlarm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAlarm= (Button)findViewById(R.id.button3);
        buttonAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentAlarm= new Intent(MainActivity.this, AlarmActivity.class);
                startActivity(IntentAlarm);
            }
        });

        mHelper= new TaskDbHelper(this);
        mTaskListView= (ListView) findViewById(R.id.list_todo);
        SQLiteDatabase db= mHelper.getReadableDatabase();
        Cursor cursor= db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()){
            int idx= cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            Log.d(TAG,"Task: "+ cursor.getString(idx));
        }
        cursor.close();
        db.close();
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        mHelper= new TaskDbHelper(this);
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case R.id.action_add_task:
                //Log.d(TAG, "Add new task");
                final EditText taskEditText= new EditText(this);
                AlertDialog dialog= new AlertDialog.Builder(this)
                        .setTitle("Add new task")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task= String.valueOf(taskEditText.getText());
                                SQLiteDatabase db= mHelper.getWritableDatabase();
                                ContentValues values= new ContentValues();
                                values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
                                db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                                //Log.d(TAG,"Task to add:" + task);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI(){
        ArrayList<String> tasklist= new ArrayList<>();
        SQLiteDatabase db= mHelper.getReadableDatabase();
        Cursor cursor= db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()){
            int idx= cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            tasklist.add(cursor.getString(idx));
        }

        if (mAdapter == null){
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.item_todo1,
                    R.id.task_title,
                    tasklist);
            mTaskListView.setAdapter(mAdapter);
        }else {
            mAdapter.clear();
            mAdapter.addAll(tasklist);
            mAdapter.notifyDataSetChanged();
        }
        cursor.close();
        db.close();
    }

    public void deleteTask(View view){
        View parent= (View)view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        String task= String.valueOf(taskTextView.getText());
        SQLiteDatabase db= mHelper.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE,
                TaskContract.TaskEntry.COL_TASK_TITLE + " = ? ",
                new String[]{task});
        db.close();
        updateUI();
    }

    public void activityRing(View v){
        Uri number= Uri.parse("tel: ");
        Intent IntentRing= new Intent(Intent.ACTION_DIAL, number);
        startActivity(IntentRing);
    }

}
