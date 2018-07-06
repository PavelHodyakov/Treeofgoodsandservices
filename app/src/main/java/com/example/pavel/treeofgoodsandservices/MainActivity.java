package com.example.pavel.treeofgoodsandservices;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper dbHelper = new DBHelper(getApplicationContext(),"goods and services",
                null, 1);

        DataStore dataStore = new DataStore();
        if(!dataStore.checkData(dbHelper)){
            MyTask mt = new MyTask(this);
            mt.execute();
        } else {
            ExpandedListComponents expandedListComponents = dataStore.getData(dbHelper);
            dbHelper.close();
            SimpleExpandableListAdapter adapter; adapter = new SimpleExpandableListAdapter(
                    this,
                    expandedListComponents.getGroupData(),
                    android.R.layout.simple_expandable_list_item_1,
                    expandedListComponents.getGroupFrom(),
                    expandedListComponents.getGroupTo(),
                    expandedListComponents.getChildData(),
                    android.R.layout.simple_list_item_1,
                    expandedListComponents.getChildFrom(),
                    expandedListComponents.getChildTo());
            ExpandableListView elvMain = findViewById(R.id.expandable_list);
            elvMain.setAdapter(adapter);
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.layout.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_cart) {
            update();
        }
        return super.onOptionsItemSelected(item);
    }

    public void update(){
        Log.d("Message","Update");
        DBHelper dbHelper = new DBHelper(getApplicationContext(),"goods and services",
                null, 1);
        DataStore dataStore = new DataStore();
        dataStore.removeData(dbHelper);
        MyTask mt = new MyTask(this);
        mt.execute();
    }

}
