package com.oop.repository.impl;

import com.oop.model.Description;
import com.oop.model.ThoiKyModel;
import com.oop.repository.Repository;
import com.oop.repository.ThoiKyRepository;
import com.oop.util.Config;
import com.oop.util.JsonHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ThoiKyRepositoryImpl implements ThoiKyRepository, Repository {

    private static ThoiKyRepositoryImpl instance;
    private List<ThoiKyModel> models = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(ThoiKyRepositoryImpl.class.getName());

    private ThoiKyRepositoryImpl() {

    }

    public static ThoiKyRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new ThoiKyRepositoryImpl();
        }

        return instance;
    }

    @Override
    public void loadData() {
        logger.info("Bat dau load data cac thoi ky");
        JSONObject object = JsonHandler.readJson(Config.THOI_KY_FILENAME);
        JSONArray arr = (JSONArray) object.get("list");
        for (int i = 0; i < arr.size(); i++) {
            JSONObject ob = (JSONObject) arr.get(i);
            try {
                models.add(new ThoiKyModel((String) ob.get("name"), (String) ob.get("link"), (String) ob.get("description")));
            } catch (Exception ex) {
                logger.error("Loi khi load data cac thoi ky", ex);
            }
        }
    }

    @Override
    public List<ThoiKyModel> getAllThoiKy() {
        return models;
    }

    @Override
    public Description getThoiKyByName(String name) {
        for (ThoiKyModel thoiKyModel : models) {
            if (name.equalsIgnoreCase(thoiKyModel.getName())) {
                return thoiKyModel;
            }
        }
        return null;
    }
}
