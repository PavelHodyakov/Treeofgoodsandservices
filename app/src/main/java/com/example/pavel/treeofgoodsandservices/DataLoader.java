package com.example.pavel.treeofgoodsandservices;

import android.os.Build;
import android.support.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DataLoader implements IDataLoader {
    /**
     * Метод загрузки данных по url
     * @param uk - user key
     * @param pk - project key
     * @return - загруженные данные или false в случае ошибки загрузки
     */
    @Override
    public String loader(String uk, String pk) {
        String line = "";
        try {
            URL url = new URL(" http://jsonstub.com/test_api");
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();

            httpCon.setRequestMethod("GET");
            httpCon.setRequestProperty("Content-Type","application/json");
            httpCon.setRequestProperty("JsonStub-User-Key", uk);
            httpCon.setRequestProperty("JsonStub-Project-Key", pk);
            httpCon.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
            line = rd.readLine();
            rd.close();
            httpCon.disconnect();
        } catch (Exception e) {
            return "false";
        }
        return line;
    }

    /**
     * Метод для извлечения данных из json
     * @param json - загруженные данные
     * @return - список из объектов, которые содержит json
     * @throws JSONException
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ArrayList<Category> parceJson(String json) throws JSONException {
        ArrayList<Category> categoryArrayList = new ArrayList<>();
        JSONArray data = new JSONArray(json);
        for(int i=0;i<data.length();i++){
            JSONObject category = (JSONObject) data.get(i);
            String title = category.getString("title");
            String img = category.getString("img");
            JSONArray arrayGoods = category.getJSONArray("subs");
            ArrayList<Goods> goodsArrayList = new ArrayList<>();
            for(int j=0;j<arrayGoods.length();j++){

                JSONObject goods = (JSONObject) arrayGoods.get(j);
                String titleGoods = goods.getString("title");
                int idGoods = goods.getInt("id");
                goodsArrayList.add(new Goods(titleGoods,idGoods));
            }
            categoryArrayList.add(new Category(title,img,goodsArrayList));
        }
        return categoryArrayList;
    }
}
