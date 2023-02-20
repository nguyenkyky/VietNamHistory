package com.oop.model;

import com.oop.util.HienThi;

import java.util.HashMap;
import java.util.Map;

public class ThoiKyModel extends Model implements Description, CustomUrl{
    private String link;
    private String description;
    public ThoiKyModel(String name, String link, String description) {
        super(name);
        this.link = link;
        this.description = description;
    }

    @Override
    public Map<String, String> getMapDescription() {
        Map<String, String> res = new HashMap<>();
        res.put("Tên", name);
        res.put("Chi tiết", description);
        res.put("Link tham khảo", link);
        return res;
    }

    @Override
    public String getUrl() {
        return HienThi.getThoiKyUrl() + "/" + name;
    }
}
