package com.oop.data;

import com.oop.util.CallAPI;
import com.oop.util.UrlDecode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DongLichSu implements Runnable{

    public static final String BASE_URL = "https://nguoikesu.com";

    private String name;
    private String url;

    private String code;

    private final Logger logger = LogManager.getLogger(this.getClass().getName());

    public DongLichSu(String name, String url) {
        this.name = name;
        this.url = url;
        this.code = UrlDecode.getCodeFromUrl(url);
    }

    private void get1() {
        try {
            Document doc = CallAPI.callAPI(url);
//            if(doc == null) {
//                logger.warn("Get nothing from " + url);
//                return;
//            }
            Elements elements = doc.select("a.btn");
            for (Element e : elements) {
                String nextUrl = BASE_URL + e.attr("href");
                try {
                    Document nextDoc = CallAPI.callAPI(nextUrl);
                    Elements nextElements = nextDoc.select("h3 a");
                    for (Element ne : nextElements) {
                        String nhanVatUrl = ne.attr("href");
                        new NhanVat(name, code, nhanVatUrl);
                    }
                } catch (Exception exception) {
                    logger.error("Loi khi ket noi toi link " + url, exception);
                }
            }
        } catch (Exception e) {
            logger.error("Loi khi ket noi toi link " + url, e);
        }
    }

    private void get2() {
        try {
            Document doc = CallAPI.callAPI(url);
            Elements navs = doc.select(".pagenav");
            int navSize = 1;
            if(navs != null) {
                navSize = navs.size();
            }

            process2(url);

            if(navSize > 1) {
                for(int i = 1; i < navSize; i++) {
                    process2(url + "?start=" + (i * 5));
                }
            }
        } catch (Exception e) {
            logger.error("Loi khi ket noi toi link " + url, e);
        }
    }

    private void process2(String url) {
        Document doc = CallAPI.callAPI(url);
        Elements readmores = doc.select(".page-header a");
        for(Element e : readmores) {
            String nextUrl = BASE_URL + e.attr("href");
            Document nextDoc = CallAPI.callAPI(nextUrl);
            String nhanVatUrl = null;
            try {
                nhanVatUrl = nextDoc.select(".readmore a").get(0).attr("href");
                new NhanVat(name, code, nhanVatUrl);
            } catch (Exception ex) {

            }
        }
    }

    @Override
    public void run() {
        switch (this.code) {
            case "hong-bang-va-van-lang":
            case "au-lac-va-nam-viet":
                get1();
                break;
            case "nha-trieu":
            case "nha-tran":
            case "nha-tien-ly":
            case "nha-hau-ly":
            case "ho-khuc":
            case "nha-ngo":
            case "nha-dinh":
            case "nha-tien-le":
            case "nha-ly":
            case "nha-ho":
            case "nha-hau-tran":
            case "nha-hau-le":
            case "nha-le-trung-hung":
            case "nha-mac":
            case "chua-trinh":
            case "chua-nguyen":
            case "nha-nguyen":
                get2();
                break;
        }
    }
}
