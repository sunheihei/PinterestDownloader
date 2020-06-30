package com.sunexample.fbdownloader;

import android.app.DownloadManager;
import android.net.Uri;

import java.util.concurrent.ExecutionException;


public class FacViDownloader {
    private String facebookVideoLink;

    public FacViDownloader(String facebookVideoLink) {
        this.facebookVideoLink = facebookVideoLink;
    }

    public String getUrl() {
        String link = "";
        if (facebookVideoLink.contains("facebook")) {
            facebookVideoLink = facebookVideoLink.replace("www", "mbasic");
            StringBuilder builder = null;
            try {
                builder = (StringBuilder) new Xconnect(facebookVideoLink).execute().get();
                link = getFaceBookLink(builder.toString());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return link;
    }

    public DownloadManager.Request getRequest() {
        if (facebookVideoLink.contains("facebook")) {
            facebookVideoLink = facebookVideoLink.replace("www", "mbasic");
            try {
                StringBuilder builder = (StringBuilder) new Xconnect(facebookVideoLink).execute().get();
                String link = getFaceBookLink(builder.toString());
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(link));
                return request;
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    private String getFaceBookLink(String conte) {
        String cc = "";

        String fullLink = "";
        String firstIdOne = "";
        String firstIdSecond = "";
        String secondId = "";
        String idOne = "";
        String firstIdThird = "";
        String thirdId = "";
        String firstIdFourth = "";
        String idfour = "";
        String finalId = "";
        String finalFirstId = "";
        String downLink = "";
        String rest = "";
        int one = 0;
        String something = "";
        // Toast.makeText(getApplicationContext(),"one",Toast.LENGTH_LONG).show();
        for (int i = 0; i < conte.length(); i++) {
            if (conte.charAt(i) == '/' && conte.charAt(i + 1) == 'v' && conte.charAt(i + 2) == 'i'
                    && conte.charAt(i + 3) == 'd' && conte.charAt(i + 4) == 'e' && conte.charAt(i + 5) == 'o'
                    && conte.charAt(i + 6) == '_' && conte.charAt(i + 7) == 'r'
                    && conte.charAt(i + 8) == 'e' && conte.charAt(i + 9) == 'd'
                    && conte.charAt(i + 10) == 'i' && conte.charAt(i + 11) == 'r' && conte.charAt(i + 12) == 'e'
                    && conte.charAt(i + 13) == 'c' && conte.charAt(i + 14) == 't' && conte.charAt(i + 15) == '/') {
                cc = conte.substring(i);
//                Log.d("Fav", "cc : " + cc);
                break;
            }
        }

        if (!cc.isEmpty()) {
            String strURL = "";
            strURL = cc.replaceAll("%3A", ":").replaceAll("%2F", "/")  //过滤URL 包含中文
                    .replaceAll("%3F", "?").replaceAll("%3D", "=").replaceAll(
                            "%26", "&");
            int startindex = strURL.indexOf("?src=") + 5;
            int endindex = strURL.indexOf("source=misc") - 1;
            downLink = strURL.substring(startindex, endindex);
        }
        return downLink;
    }
}
