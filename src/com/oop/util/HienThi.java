package com.oop.util;

public class HienThi {
    public static final String DANH_SACH_THOI_KY = "Danh sách thời kỳ lịch sử";
    public static final String DANH_SACH_TINH_NANG = "Cùng tìm hiểu lịch sử Việt Nam nào!!!";
    public static final String DANH_SACH_NHAN_VAT = "Danh sách nhân vật lịch sử";
    public static final String HOME_URL = "/Trang chủ";
    public static final String THOI_KY_URL = "/Thời kỳ";
    public static final String NHAN_VAT_URL = "/Nhân vật";
    public static String getThoiKyUrl() {
        return HOME_URL + THOI_KY_URL;
    }
    public static String getNhanVatUrl() {
        return HOME_URL + NHAN_VAT_URL;
    }

    public static String getDataFromUrl(String url) {
        String[] arr = url.split("/");
        if (arr.length > 0) {
            return arr[arr.length - 1];
        }
        return null;
    }
}
