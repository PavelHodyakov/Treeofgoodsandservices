package com.example.pavel.treeofgoodsandservices;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataStore implements IDataStore {
    /**
     * Метод добавления данных в БД
      * @param dbHelper
     * @param categories - данные для сохранения
     */
   public void addData(DBHelper dbHelper, ArrayList<Category> categories){
       SQLiteDatabase db = dbHelper.getWritableDatabase();
       for(int i=0;i<categories.size();i++){

           Category category = categories.get(i);
           long idImg;
           long verification = imageVerification(dbHelper,category.getImg());
           if(verification==-1) {
               ContentValues image = new ContentValues();
               image.put("url", category.getImg());
               idImg = db.insert("images", null, image);
           }else{
               idImg=verification;
           }

           ContentValues categ = new ContentValues();
           categ.put("name",category.getName());
           categ.put("id_img",idImg);
           long idCategory = db.insert("categories", null,categ);

           for(int j=0;j<category.getSubs().size();j++){
               ContentValues goods = new ContentValues();
               goods.put("id",category.getSubs().get(j).getId());
               goods.put("name",category.getSubs().get(j).getName());
               goods.put("id_category",idCategory);
               db.insert("goods", null, goods);
           }
       }
   }

    /**
     * Метод очистки данных из таблиц
     * @param dbHelper
     */
   public void removeData(DBHelper dbHelper){
       SQLiteDatabase db = dbHelper.getWritableDatabase();
       db.delete("goods",null,null);
       db.delete("categories", null, null);
       db.delete("images", null,null);
       db.close();
   }

    /**
     * Метод дл проверки изображения на сущестование
     * @param dbHelper
     * @param image - url изображения
     * @return
     */
   private long imageVerification(DBHelper dbHelper, String image){

       SQLiteDatabase db = dbHelper.getReadableDatabase();
       Cursor cursor = db.query("images", new String[] {"id"}, "url = ?",
               new String[] {image}, null, null, null, null);
       if(cursor.moveToFirst()){
            int idIndex = cursor.getColumnIndex("id");
            long id = cursor.getLong(idIndex);
            cursor.close();
            return id;
       }
       else{
           cursor.close();
           return -1;
       }
   }

    /**
     * Метод проверки наличия данных
     * @param dbHelper
     * @return
     */
   public boolean checkData(DBHelper dbHelper){
       SQLiteDatabase db = dbHelper.getReadableDatabase();
       Cursor cursor = db.query("categories", new String[] {"id"}, null,
               null, null, null, null, null);
       if(cursor.moveToFirst()){
           cursor.close();
           return true;
       }
       else{
           cursor.close();
           return false;
       }
   }

    /**
     * Метод для извлечения данных
     * @param dbHelper
     * @return - Объект ExpandedListComponents
     */
   public ExpandedListComponents getData(DBHelper dbHelper){
       // коллекция для групп
       ArrayList<Map<String, String>> groupData = new ArrayList<>();
       // коллекция для элементов одной группы
       ArrayList<Map<String, String>> childDataItem;
       // общая коллекция для коллекций элементов
       ArrayList<ArrayList<Map<String, String>>> childData = new ArrayList<>();
       // в итоге получится childData = ArrayList<childDataItem>
       // список атрибутов группы или элемента
       Map<String, String> m;

       SQLiteDatabase db = dbHelper.getReadableDatabase();
       Cursor cursor = db.query("categories", new String[] {"id","name"}, null,
               null, null, null, null, null);

       while(cursor.moveToNext()){
           long id = cursor.getLong(cursor.getColumnIndex("id"));
           m = new HashMap<>();
           m.put("categoryName", cursor.getString(cursor.getColumnIndex("name")));
           groupData.add(m);
           Cursor goods = db.query("goods", new String[] {"name"}, "id_category = ?",
                   new String[]{String.valueOf(id)}, null, null, null, null);

           childDataItem = new ArrayList<Map<String, String>>();
           while(goods.moveToNext()){
               //здесь ошибка
               m = new HashMap<>();
               m.put("goodsName", goods.getString(goods.getColumnIndex("name")));
               Log.e(" Goods:", goods.getString(goods.getColumnIndex("name")));
               childDataItem.add(m);
           }
           childData.add(childDataItem);
           goods.close();
       }
       cursor.close();

       // список атрибутов групп для чтения
       String groupFrom[] = new String[] {"categoryName"};
       // список ID view-элементов, в которые будет помещены атрибуты групп
       int groupTo[] = new int[] {android.R.id.text1};
       // список атрибутов элементов для чтения
       String childFrom[] = new String[] {"goodsName"};
       // список ID view-элементов, в которые будет помещены атрибуты элементов
       int childTo[] = new int[] {android.R.id.text1};

       return new ExpandedListComponents(groupFrom,groupTo,childFrom,
               childTo,groupData,childData);
   }



}
