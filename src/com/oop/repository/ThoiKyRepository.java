package com.oop.repository;

import com.oop.model.Description;
import com.oop.model.ThoiKyModel;

import java.util.List;

public interface ThoiKyRepository {
    List<ThoiKyModel> getAllThoiKy();
    Description getThoiKyByName(String name);
}
