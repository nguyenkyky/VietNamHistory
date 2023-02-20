package com.nguoikeusu;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    public static Map<Integer, String> mp = new HashMap<>();
    public static void main(String[] args) {

        JSONObject object = new JSONObject();
        JSONArray arr = new JSONArray();
        object.put("list", arr);

        try1(object);
        try2(object);
        System.out.println(object.get("list"));
    }

    private static void try1(JSONObject object) {
        JSONArray arr = (JSONArray) object.get("list");
        arr.add(6);
        arr.add(8);
        arr.add(10);
        object.put("list", arr);
    }

    private static void try2(JSONObject object) {
        JSONArray arr = (JSONArray) object.get("list");
        arr.add(3);
        arr.add(4);
        arr.add(5);
        object.put("list", arr);
    }
}
