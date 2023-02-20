package com.oop.repository.impl;

import com.oop.model.SuKienModel;
import com.oop.repository.Repository;
import com.oop.repository.SuKienRepository;
import com.oop.util.Config;
import com.oop.util.JsonHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SuKienRepositoryImpl implements SuKienRepository, Repository {
    private static SuKienRepositoryImpl instance;
    private static final Logger logger = LogManager.getLogger(SuKienRepositoryImpl.class.getName());
    private List<SuKienModel> models = new ArrayList<>();

    public static SuKienRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new SuKienRepositoryImpl();
        }

        return instance;
    }
    @Override
    public void loadData() {
        logger.info("Bat dau load data su kien");
        JSONObject object = JsonHandler.readJson(Config.SU_KIEN_FILENAME);
        JSONArray array = (JSONArray) object.get("list");
        for (int i = 0; i < array.size(); i++) {
            try {
                JSONObject ob = (JSONObject) array.get(i);
                String name = (String) ob.get("name");
                String description = (String) ob.get("description");
                SuKienModel suKienModel = new SuKienModel(name);
                suKienModel.setDescription(description);
                if (ob.get("Nhân vật liên quan") != null) {
                    suKienModel.setNhanVatLienQuan((JSONArray) ob.get("Nhân vật liên quan"));
                }
                if (ob.get("Địa điểm liên quan") != null) {
                    suKienModel.setDiaDanhLienQuan((JSONArray) ob.get("Địa điểm liên quan"));
                }
                models.add(suKienModel);
            } catch (Exception ex) {
                logger.error("Loi khi load data su kien", ex);
            }
        }
    }
}
