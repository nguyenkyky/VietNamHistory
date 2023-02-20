package com.oop.service.impl;

import com.oop.model.Description;
import com.oop.model.ThoiKyModel;
import com.oop.repository.ThoiKyRepository;
import com.oop.repository.impl.ThoiKyRepositoryImpl;
import com.oop.service.ThoiKyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThoiKyServiceImpl implements ThoiKyService {
    private static ThoiKyServiceImpl instance = null;
    private static final Logger logger = LogManager.getLogger(ThoiKyServiceImpl.class.getName());
    private ThoiKyRepository thoiKyRepository = ThoiKyRepositoryImpl.getInstance();

    public static ThoiKyServiceImpl getInstance() {
        if (instance == null) {
            instance = new ThoiKyServiceImpl();
        }
        return instance;
    }

    @Override
    public List<ThoiKyModel> getAllThoiKy() {
        return thoiKyRepository.getAllThoiKy();
    }

    @Override
    public Map<String, String> getThoiKyByName(String name) {
        Description des = thoiKyRepository.getThoiKyByName(name);
        if (des == null) {
            return new HashMap<>();
        }
        return des.getMapDescription();
    }
}
