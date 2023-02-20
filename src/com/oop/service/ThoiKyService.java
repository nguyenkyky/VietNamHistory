package com.oop.service;

import com.oop.model.ThoiKyModel;

import java.util.List;
import java.util.Map;

public interface ThoiKyService {
    List<ThoiKyModel> getAllThoiKy();
    Map<String, String> getThoiKyByName(String name);
}
