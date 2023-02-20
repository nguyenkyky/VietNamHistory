package com.oop.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class JsonHandler {
    public static final Logger logger = LogManager.getLogger(JsonHandler.class.getName());
    public static void writeJson(JSONObject obj, String filename) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            logger.info("Ghi file " + filename);
            writer.write(obj.toJSONString());
            logger.info("Ghi file " + filename + " thanh cong");
        } catch (Exception e) {
            logger.error("Loi khi ghi json file " + filename , e);
        }
    }

    public static JSONObject readJson(String filename) {
        JSONObject res = new JSONObject();
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder sb = new StringBuilder();
            String t = reader.readLine();
            while (t != null) {
                sb.append(t);
                t = reader.readLine();
            }
            JSONParser parser = new JSONParser();
            res = (JSONObject) parser.parse(sb.toString());
        } catch (Exception e) {
            logger.error("Loi khi doc json file", e);
        }
        return res;
    }
}
