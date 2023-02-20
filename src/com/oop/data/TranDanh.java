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

public class TranDanh {

    private final String BASE_URL = "https://vi.wikipedia.org";
    private final String ALL_URL = "https://vi.wikipedia.org/wiki/Danh_sách_trận_đánh_trong_lịch_sử_Việt_Nam";
    public static JSONObject tranDanhObject = new JSONObject();
    private final ExecutorService pool = Executors.newFixedThreadPool(8);

    public TranDanh() {
        Document doc = CallAPI.callAPI(ALL_URL);
        Elements elements = doc.select(".mw-parser-output h2");
        for (Element e : elements) {
            String thoiKyName = e.select(".mw-headline").text();
            Elements listTranDanh = e.nextElementSibling().select("li a");
            for (Element ee : listTranDanh) {
                String tranDanhUrl = ee.attr("href");
                    pool.submit(new TranDanhData(thoiKyName, BASE_URL + tranDanhUrl));
            }
        }

        pool.shutdown();
        try {
            if (!pool.awaitTermination(300, TimeUnit.SECONDS)) {
                pool.shutdownNow();
            }
            JsonHandler.writeJson(tranDanhObject, Config.TRAN_DANH_FILENAME);
        } catch (InterruptedException ex) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private class TranDanhData implements Runnable{
        private String thoiKyName;
        private String url;
        private final Logger logger = LogManager.getLogger(this.getClass().getName());
        public TranDanhData(String thoiKyName, String url) {
            this.thoiKyName = thoiKyName;
            this.url = url;
        }
        @Override
        public void run() {
            Document doc = CallAPI.callAPI(url);
            JSONArray arr = tranDanhObject.containsKey("list") ? (JSONArray) tranDanhObject.get("list") : new JSONArray();
            JSONObject obj = new JSONObject();
//        try {
//            String khaiQuat = doc.select(".mw-parser-output p").get(0).text();
//            obj.put("khai-quat", khaiQuat);
//        } catch (Exception e) {
//            logger.error("Duong link " + url + " co the khong phai 1 tran danh", e);
//        }

            try {
                Element tranDanhDoc = doc.select(".infobox").get(0);
                String name = doc.select(".mw-page-title-main").text();
                obj.put("name", name);
                Element info = tranDanhDoc.select("table").get(0);
                for (Element e : info.select("tr")) {
                    String key = e.select("th").text();
                    String value = e.select("td").text();
                    if (key.equals("Thời gian Địa điểm Kết quả") || key.equals("Thời gian Địa điểm Kết quả Thay đổi lãnh thổ")) {
                        continue;
                    }
                    if (key.length() > 0 && value.length() > 0) {
                        obj.put(key, value);
                    }
                }

                for (Element e : doc.select("tr")) {
                    if (e.text().equals("Tham chiến") || e.text().equals("Chỉ huy và lãnh đạo") || e.text().equals("Lực lượng") || e.text().equals("Thương vong và tổn thất")) {
                        Element ne = e.nextElementSibling();
                        JSONObject o = new JSONObject();
                        JSONArray a = new JSONArray();
                        JSONArray b = new JSONArray();
                        try {
                            Elements es = ne.select("td").get(0).select("a");
                            for (Element ee : es) {
                                if(ee.text() != "" && ee.text().charAt(0) != '[')
                                    a.add(ee.text());
                            }
                        } catch (Exception ex) {
                            logger.error("Loi khi doc thong tin" + e.text() + ", url: " + url);
                        }

                        try {
                            Elements es = ne.select("td").get(1).select("a");
                            for (Element ee : es) {
                                if(ee.text() != "" && ee.text().charAt(0) != '[')
                                    b.add(ee.text());
                            }
                        } catch (Exception ex) {
                            logger.error("Loi khi doc thong tin" + e.text() + ", url: " + url);
                        }
                        o.put("a", a);
                        o.put("b", b);
                        obj.put(e.text(), o);
                    }
                }
            } catch (Exception e) {
                logger.error("Loi khi doc thong tin cua tran danh, url: " + url, e);
            }

            if (obj.keySet().size() > 0) {
                arr.add(obj);
                tranDanhObject.put("list", arr);
            }
        }
    }
}
