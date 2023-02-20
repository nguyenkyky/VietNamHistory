package com.oop.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CallAPI {

    private static final Logger logger = LogManager.getLogger(CallAPI.class.getName());

    private static HostnameVerifier allHostsValid = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    public static Document callAPI(String url) {
        try {
            URL u = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) u.openConnection();
            conn.setHostnameVerifier(allHostsValid);
            Document doc = getResponse(conn, url);
            conn.disconnect();
            return doc;

        } catch (Exception e) {
            logger.error("Loi khi goi duong link " + url, e);
            return new Document(url);
        }
    }

//    public static Document callAPI(String url) {
//        try {
//            HttpRequest getRequest = HttpRequest.newBuilder()
//                    .uri(new URI(url))
//                    .GET()
//                    .build();
//            HttpClient httpClient = HttpClient.newHttpClient();
//            HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
//            return Jsoup.parse(getRequest.toString());
//        } catch (Exception ex) {
//            logger.error("Loi khi goi duong link " + url, ex);
//        }
//        return null;
//    }

    private static Document getResponse(HttpsURLConnection conn, String url) {
        if(conn != null) {
            try {
                BufferedReader br =
                        new BufferedReader(
                                new InputStreamReader(conn.getInputStream()));

                String input;
                StringBuilder sb = new StringBuilder();

                while ((input = br.readLine()) != null){
                    sb.append(input);
                }
                br.close();

                return Jsoup.parse(sb.toString());
            } catch (Exception e) {
                logger.error("Loi khi doc response tu link " + url, e);
                return new Document(url);
            }
        }
        return new Document(url);
    }
}
