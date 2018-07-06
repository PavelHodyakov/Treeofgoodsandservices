package com.example.pavel.treeofgoodsandservices;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import org.json.JSONException;

class MyTask extends AsyncTask<Void, Void, String> {

    private MainActivity activity;

    MyTask(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(Void... params) {
        DataLoader dataLoader = new DataLoader();
        String json = dataLoader.loader("cbd2482f-5e46-4483-b9f9-ad667183a86b",
                "334e7727-f014-493e-87c2-3db789aa802a");
        if(json.equals("false")){
            return "false";
        }

        DBHelper dbH = new DBHelper(activity.getApplicationContext(),"goods and services",
                null, 1);
        DataStore dataStore = new DataStore();
        try {
            dataStore.addData(dbH,dataLoader.parceJson(json));
            dbH.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "true";
    }

    @Override
    protected void onPostExecute(String params) {
        super.onPostExecute(params);
        if(params.equals("false")){
            Toast toast = Toast.makeText(activity.getApplicationContext(),
                    "Не удалось загрузить данные", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            DataStore dataStore = new DataStore();
            DBHelper dbH = new DBHelper(activity.getApplicationContext(), "goods and services",
                    null, 1);
            ExpandedListComponents expandedListComponents = dataStore.getData(dbH);
            dbH.close();
            SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                    activity.getApplicationContext(),
                    expandedListComponents.getGroupData(),
                    android.R.layout.simple_expandable_list_item_1,
                    expandedListComponents.getGroupFrom(),
                    expandedListComponents.getGroupTo(),
                    expandedListComponents.getChildData(),
                    android.R.layout.simple_list_item_1,
                    expandedListComponents.getChildFrom(),
                    expandedListComponents.getChildTo());
            ExpandableListView elvMain = activity.findViewById(R.id.expandable_list);
            elvMain.setAdapter(adapter);
        }
    }
}