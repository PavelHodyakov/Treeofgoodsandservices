package com.example.pavel.treeofgoodsandservices;

import java.util.ArrayList;

public class Category {
    private String name;
    private String img;
    private ArrayList<Goods> subs;

    Category(String name, String img, ArrayList<Goods> subs) {
        this.name = name;
        this.img = img;
        this.subs = subs;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public ArrayList<Goods> getSubs() {
        return subs;
    }
}
