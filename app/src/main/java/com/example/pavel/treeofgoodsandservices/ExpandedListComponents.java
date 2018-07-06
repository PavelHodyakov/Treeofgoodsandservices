package com.example.pavel.treeofgoodsandservices;

import java.util.ArrayList;
import java.util.Map;

public class ExpandedListComponents {

    private String groupFrom[];
    private int groupTo[];
    private String childFrom[];
    private int childTo[];
    private ArrayList<Map<String, String>> groupData;
    private ArrayList<ArrayList<Map<String, String>>> childData;

    ExpandedListComponents(String[] groupFrom, int[] groupTo, String[] childFrom,
                           int[] childTo, ArrayList<Map<String, String>> groupData,
                           ArrayList<ArrayList<Map<String, String>>> childData) {
        this.groupFrom = groupFrom;
        this.groupTo = groupTo;
        this.childFrom = childFrom;
        this.childTo = childTo;
        this.groupData = groupData;
        this.childData = childData;
    }

    public String[] getGroupFrom() {
        return groupFrom;
    }

    public int[] getGroupTo() {
        return groupTo;
    }

    public String[] getChildFrom() {
        return childFrom;
    }

    public int[] getChildTo() {
        return childTo;
    }

    public ArrayList<Map<String, String>> getGroupData() {
        return groupData;
    }

    public ArrayList<ArrayList<Map<String, String>>> getChildData() {
        return childData;
    }
}
