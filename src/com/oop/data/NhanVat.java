package com.oop.data;

import com.oop.util.CallAPI;
import com.oop.util.UrlDecode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.oop.data.ThoiKy.nhanVatObject;

public class NhanVat {
    private final String BASE_URL = "https://nguoikesu.com";
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private Document doc;
    private String name;

    private String thoiKyName;
    private String thoiKyCode;
    private String url;

    public NhanVat(String thoiKyName, String thoiKyCode, String url) {
        this.thoiKyName = thoiKyName;
        this.thoiKyCode = thoiKyCode;
        this.url = BASE_URL + url;
        // Co the kiem tra nam da ton tai hay chua truoc

        try {
            doc = CallAPI.callAPI(this.url);
            Element thongTinNhanVat = doc.select(".infobox").get(0);
            Elements trs = thongTinNhanVat.select("tr");
//            JSONObject object = JsonHandler.readJson(FILENAME);
            JSONArray array = nhanVatObject.containsKey("list") ? (JSONArray) nhanVatObject.get("list") : new JSONArray();
            JSONObject obj = new JSONObject();
            obj.put("name", doc.select(".page-header h2").text());
            obj.put("code", UrlDecode.getCodeFromUrl(url));
            obj.put("thoi-ky-name", thoiKyName);
            obj.put("thoi-ky-code", thoiKyCode);

            for (Element e : trs) {
                if (e.select("th") != null && e.select("td") != null) {
                    String key = "";
                    String value = "";
                    try {
                        key = e.select("th").get(0).text();
                       value = e.select("td").get(0).text();
                    } catch (Exception ex) {

                    }

                    if (key.length() > 0 && value.length() > 0) {
                        obj.put(key, value);
                    }

                }
            }
            array.add(obj);
            nhanVatObject.put("list", array);

//            JsonHandler.writeJson(object, FILENAME);
        } catch (Exception e) {
            logger.error("Loi khi lay thong tin nhan vat, thoi ky: " + thoiKyName, e);
        }
    }
}
