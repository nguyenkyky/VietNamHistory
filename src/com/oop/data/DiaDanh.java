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

public class DiaDanh {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String BASE_URL = "https://thuvienlichsu.com";
    private final int numOfPage = 10;
    private final ExecutorService pool = Executors.newFixedThreadPool(8);

    private JSONArray diaDanhArray = new JSONArray();

    public DiaDanh() {
        try {
            logger.info("Bat dau lay thong tin cac dia danh lich su");
            for(int i = 1; i <= numOfPage; i++) {
                pool.submit(new DiaDanhData(BASE_URL + "/dia-diem?page=" + i));
            }

            pool.shutdown();
            try {
                if (!pool.awaitTermination(300, TimeUnit.SECONDS)) {
                    pool.shutdownNow();
                }
                JSONObject diaDanhObject = new JSONObject();
                diaDanhObject.put("list", diaDanhArray);
                JsonHandler.writeJson(diaDanhObject, Config.DIA_DANH_FILENAME);
            } catch (InterruptedException ex) {
                pool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        } catch (Exception ex) {
            logger.error("Co loi xay ra khi lay thong tin dia danh lich su", ex);
        }
    }

    private class DiaDanhData implements Runnable {

        private String url;

        public DiaDanhData(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            Document doc = CallAPI.callAPI(url);
            Elements elements = doc.select(".row.g-0");
            for (Element e : elements) {
                String diaDanhUrl = BASE_URL + e.select(".nut_nhan").attr("href");
                String diaDanhName = e.select(".card-title.header-edge").text();
                try {
                    JSONObject keep = new JSONObject();
                    Document diaDanhDetail = CallAPI.callAPI(diaDanhUrl);
                    Element el = diaDanhDetail.select(".divide-tag").get(1);
                    String diaDanhDes = el.select(".card-body").text();
                    keep.put("name", diaDanhName);
                    keep.put("description", diaDanhDes);
                    diaDanhArray.add(keep);
                } catch (Exception ex) {
                    logger.error("Loi khi truy cap duong link " + diaDanhUrl, ex);
                }
            }
        }
    }
}
