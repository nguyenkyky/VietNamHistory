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

public class DanhNhan {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String BASE_URL = "https://thuvienlichsu.com";
    private final int numOfPage = 23;
    private final ExecutorService pool = Executors.newFixedThreadPool(8);

    private JSONArray danhNhanArray = new JSONArray();

    public DanhNhan() {
        try {
            logger.info("Bat dau lay thong tin cac danh nhan lich su");
            for(int i = 1; i <= numOfPage; i++) {
                pool.submit(new DanhNhanData(BASE_URL + "/nhan-vat?page=" + i));
            }

            pool.shutdown();
            try {
                if (!pool.awaitTermination(600, TimeUnit.SECONDS)) {
                    pool.shutdownNow();
                }
                JSONObject diaDanhObject = new JSONObject();
                diaDanhObject.put("list", danhNhanArray);
                JsonHandler.writeJson(diaDanhObject, Config.DANH_NHAN_FILENAME);
            } catch (InterruptedException ex) {
                pool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        } catch (Exception ex) {
            logger.error("Co loi xay ra khi lay thong tin dia danh lich su", ex);
        }
    }

    private class DanhNhanData implements Runnable {

        private String url;

        public DanhNhanData(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            Document doc = CallAPI.callAPI(url);
            Elements elements = doc.select(".row.g-0");
            for (Element e : elements) {
                String danhNhanUrl = BASE_URL + e.select(".nut_nhan").attr("href");
                String danhNhanName = e.select(".card-title.header-edge").text();
                logger.info("Truy cap duong link " + danhNhanUrl);
                try {
                    JSONObject keep = new JSONObject();
                    Document diaDanhDetail = CallAPI.callAPI(danhNhanUrl);
                    Element el = diaDanhDetail.select(".divide-tag").get(1);
                    String danhNhanDes = el.select(".card-body").text();
                    keep.put("name", danhNhanName);
                    keep.put("description", danhNhanDes);
                    danhNhanArray.add(keep);
                } catch (Exception ex) {
                    logger.error("Loi khi truy cap duong link " + danhNhanUrl, ex);
                }
            }
        }
    }
}
