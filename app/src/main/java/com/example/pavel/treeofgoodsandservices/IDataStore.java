package com.example.pavel.treeofgoodsandservices;

import java.util.ArrayList;

public interface IDataStore {

    void addData(DBHelper dbHelper, ArrayList<Category> categories);

    void removeData(DBHelper dbHelper);

    boolean checkData(DBHelper dbHelper);

    ExpandedListComponents getData(DBHelper dbHelper);

}

