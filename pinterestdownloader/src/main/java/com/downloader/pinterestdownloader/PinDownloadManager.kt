package com.downloader.pinterestdownloader

import android.util.Log
import org.json.JSONObject
import org.jsoup.Jsoup
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.regex.Matcher
import java.util.regex.Pattern

class PinDownloadManager public constructor() {

    val TAG = "PinDownloadManager"

    private val ImageTypeList: List<String> =
        listOf("736x", "60x60", "474x", "170x", "600x315", "564x", "236x", "136x136", "orig")

    public fun getPinUrl(url: String, pinListener: PinListener) {
        var PinterestList: MutableList<Pinterest> = mutableListOf()
        if (url.length != 0) {
            pinListener.StartAnalysis()
            Thread(Runnable {
                try {
//                    Log.d(TAG, "url:${url}")
                    var tempURL = getRedirectUrl(url)
//                    Log.d(TAG, "tempURL:${tempURL}")
                    var realUrl = tempURL.substring(0, tempURL.indexOf("sent"))
//                    Log.d(TAG, "realUrl:${realUrl}")
                    val doc = Jsoup.connect(realUrl).get()
                    val element = doc.getElementsByAttributeValue("rel","preload").attr("href")
                    Log.d(TAG, "element : ${element}")
                    pinListener.AnalysisSuccess(element)


//                    val elements = doc.getElementById("initial-state")
//                    val array = JSONObject(elements.data()).getJSONArray("resourceResponses")
//                    val obj = array.getJSONObject(0).getJSONObject("response").getJSONObject("data")
//                        .getJSONObject("images")
//
//                    PinterestList.clear()
//                    for (i in 0 until ImageTypeList.size) {
//                        var pinterest = getPinterset(obj.getJSONObject(ImageTypeList[i]))
//                        PinterestList.add(pinterest)
//                    }
//                    pinListener.AnalysisSuccess(PinterestList)
                } catch (e: Exception) {
                    pinListener.AnalysisFail()
                }
            }).start()
        }
    }

//    private fun getPinterset(value: JSONObject): Pinterest {
//        var url = value.optString("url")
//        var width = value.optString("width")
//        var height = value.optString("height")
//        return Pinterest(url, width, height)
//    }

    private fun getRedirectUrl(path: String): String {
        var url: String? = null;
        try {
            val conn: HttpURLConnection = URL(path).openConnection() as HttpURLConnection
            conn.setInstanceFollowRedirects(false);
            conn.setConnectTimeout(5000);
            url = conn.getHeaderField("Location");
            conn.disconnect();
        } catch (e: IOException) {
            e.printStackTrace();
        }
        return url!!;
    }


}