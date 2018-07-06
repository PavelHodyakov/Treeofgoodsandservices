package com.example.pavel.treeofgoodsandservices;

public class Goods {
    String name;
    int id;

    Goods(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
