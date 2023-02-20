package com.nguoikeusu;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;

public class Thread1 implements Runnable{

    private String url;

    private static int id = 0;
    private int idx;
    private Integer lock = 1;

    public Thread1(String url) {
        synchronized (lock) {
            this.idx = ++id;
        }

        this.url = url;
    }

    @Override
    public void run() {
        System.out.println("s--------------" + idx + "--------------");
        Test.mp.put(idx, url);
        System.out.println(Test.mp.keySet());

    }

    private void print_https_cert(HttpsURLConnection con){

        if(con!=null){

            try {

                System.out.println("Response Code : " + con.getResponseCode());
                System.out.println("Cipher Suite : " + con.getCipherSuite());
                System.out.println("\n");

                Certificate[] certs = con.getServerCertificates();
                for(Certificate cert : certs){
                    System.out.println("Cert Type : " + cert.getType());
                    System.out.println("Cert Hash Code : " + cert.hashCode());
                    System.out.println("Cert Public Key Algorithm : "
                            + cert.getPublicKey().getAlgorithm());
                    System.out.println("Cert Public Key Format : "
                            + cert.getPublicKey().getFormat());
                    System.out.println("\n");
                }

            } catch (SSLPeerUnverifiedException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }

        }

    }

    private void print_content(HttpsURLConnection con){
        if(con!=null){

            try {

//                System.out.println("****** Content of the URL ********");
                BufferedReader br =
                        new BufferedReader(
                                new InputStreamReader(con.getInputStream()));

                String input;
                StringBuilder sb = new StringBuilder();

                while ((input = br.readLine()) != null){
//                    System.out.println(input);
                    sb.append(input);
                }
                br.close();

                Document doc = Jsoup.parse(sb.toString());
                System.out.println(doc.select("a").size() + " " + idx);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
