package com.oop.data;

import com.oop.data.DongLichSu;
import com.oop.util.CallAPI;
import com.oop.util.Config;
import com.oop.util.JsonHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThoiKy {

    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String BASE_URL = "https://nguoikesu.com";
    private Document doc;
    private int numOfThoiKy = 0;

    public static JSONObject nhanVatObject = new JSONObject();

    private final ExecutorService pool = Executors.newFixedThreadPool(8);

    public ThoiKy() {
        try {
            logger.info("Bat dau lay thong tin cac thoi ky lich su");
            doc = CallAPI.callAPI(BASE_URL);
            Elements cacThoiKyLichSu = doc.select(".mod-articlescategories li");
            numOfThoiKy = cacThoiKyLichSu.size();
            JSONObject obj = new JSONObject();
            obj.put("description", "Danh sách các thời kỳ lịch sử Việt Nam");
            JSONArray arr = new JSONArray();
            for(int i = 0; i < numOfThoiKy; i++) {
                Elements es = cacThoiKyLichSu.get(i).select("a");
                for (Element e : es) {
                    JSONObject o = new JSONObject();
                    o.put("name", e.text());
                    o.put("link", BASE_URL + e.attr("href"));
                    try {
                        Document thoiKyDoc = CallAPI.callAPI(BASE_URL + e.attr("href"));
                        Element thoiKyElement = thoiKyDoc.select(".category-desc.clearfix").get(0);
                        o.put("description", thoiKyElement.text());
                    } catch (Exception ex) {
                        logger.error("Loi khi lay thong tin description cua thoi ky " + e.text(), ex);
                    }
                    pool.submit(new DongLichSu(e.text(), BASE_URL + e.attr("href")));
                    arr.add(o);
                }
            }
            obj.put("list", arr);
            JsonHandler.writeJson(obj, Config.THOI_KY_FILENAME);
            pool.shutdown();
            try {
                if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                    pool.shutdownNow();
                }
                JsonHandler.writeJson(nhanVatObject, Config.NHAN_VAT_FILENAME);
            } catch (InterruptedException ex) {
                pool.shutdownNow();
                Thread.currentThread().interrupt();
            }

        } catch (Exception e) {
            logger.error("Loi khi lay thong tin cac thoi ky lich su", e);
        }
    }
}
