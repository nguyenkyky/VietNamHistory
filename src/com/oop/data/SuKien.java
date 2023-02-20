package com.oop.data;

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

public class SuKien {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String BASE_URL = "https://thuvienlichsu.com";
    private final int numOfPage = 19;
    private final ExecutorService pool = Executors.newFixedThreadPool(8);
    private JSONArray suKienArray = new JSONArray();

    public SuKien() {
        try {
            logger.info("Bat dau lay thong tin su kien lich su");
            for(int i = 1; i <= numOfPage; i++) {
                pool.submit(new SuKienData(BASE_URL + "/su-kien?page=" + i));
            }
            try {
                if (!pool.awaitTermination(600, TimeUnit.SECONDS)) {
                    pool.shutdownNow();
                }
                JSONObject diaDanhObject = new JSONObject();
                diaDanhObject.put("list", suKienArray);
                JsonHandler.writeJson(diaDanhObject, Config.SU_KIEN_FILENAME);
            } catch (InterruptedException ex) {
                pool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        } catch (Exception ex) {
            logger.error("Loi xay ra khi lay thong tin su kien lich su");
        }

    }

    private class SuKienData implements Runnable {

        private String url;
        private final String DIA_DIEM_LIEN_QUAN = "Địa điểm liên quan";
        private final String NHAN_VAT_LIEN_QUAN = "Nhân vật liên quan";

        public SuKienData(String url) {
            this.url = url;
        }

        private String normalizeString(String s) {
            if (s.contains("(")) {
                int i = s.indexOf('(');
                return s.substring(0, i);
            }
            return s;
        }

        @Override
        public void run() {
            Document doc = CallAPI.callAPI(url);
            Elements suKiens = doc.select(".row.g-0");
            for (Element e : suKiens) {
                String suKienName = e.select(".card-title.header-edge").text();
                String suKienUrl = BASE_URL + e.select(".nut_nhan").attr("href");
                try {
                    logger.info("Truy cap duong link " + suKienUrl);
                    JSONObject keep = new JSONObject();
                    Document suKienDetail = CallAPI.callAPI(suKienUrl);
                    String suKienDes = suKienDetail.select(".divide-tag").get(1).select(".card-body").text();
                    keep.put("name", suKienName);
                    keep.put("description", suKienDes);
                    Elements els = suKienDetail.select(".divide-tag");
                    for (Element el : els) {
                        String title = el.select(".header-edge").text();
                        switch (title) {
                            case DIA_DIEM_LIEN_QUAN:
                            case NHAN_VAT_LIEN_QUAN:
                            {
                                    JSONArray arr = new JSONArray();
                                    for (Element detail : el.select(".row.g-0")) {
                                        arr.add(normalizeString(detail.select(".card-title").text()));
                                    }
                                    keep.put(title, arr);
                                    break;
                            }
                        }
                    }
                    suKienArray.add(keep);

                } catch (Exception ex) {
                    logger.error("Loi khi truy cap duong link " + suKienUrl, ex);
                }
            }
        }
    }

}
