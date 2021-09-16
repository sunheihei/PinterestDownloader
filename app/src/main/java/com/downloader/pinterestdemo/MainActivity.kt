package com.downloader.pinterestdemo

import android.Manifest
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import com.downloader.pinterestdownloader.PinDownloadManager
import com.downloader.pinterestdownloader.PinListener
import com.downloader.pinterestdownloader.Pinterest
import com.sunexample.fbdownloader.FacViDownloader
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import org.jsoup.Jsoup
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), PinListener {

//    val ImageTypeList: List<String> =
//        listOf("736x", "60x60", "474x", "170x", "600x315", "564x", "236x", "136x136", "orig")
//
//    var PinterestList: MutableList<Pinterest> = mutableListOf()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RxPermissions(this).requestEach(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
        ).subscribe { permission ->
            if (permission.granted) {

            }
        }

        /**
         * Now, Facebook and Pinterest are supported
         */
        btn_download.setOnClickListener {
            if (et_pinurl.getText().toString().contains("facebook")) {
                Log.d("FB_url", FacViDownloader(et_pinurl.getText().toString()).url)
            } else {
                val pin = PinDownloadManager()
                pin.getPinUrl(et_pinurl.getText().toString(), this)
            }

        }

        btn_clear.setOnClickListener {
            et_pinurl.setText("")
        }

        btn_paste.setOnClickListener {
            val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = clipBoard.primaryClip
            clipData?.let {
                val item = clipData.getItemAt(0)
                val clipURL = item.text.toString()
                et_pinurl.setText(clipURL + "")
            }
        }
    }


    override fun StartAnalysis() {
        //mainthread
        progressBar.visibility = View.VISIBLE
    }

    override fun AnalysisSuccess(url: String) {
        runOnUiThread {
            val TempleName = "${System.currentTimeMillis()}"
            StartDownload(url, TempleName)

            Toast.makeText(this, "Url:${url}", Toast.LENGTH_LONG).show()

        }
    }


    override fun AnalysisFail() {
        runOnUiThread {
            //dosomething
        }
    }

//    private fun showdialog(PinterestList: MutableList<Pinterest>) {
//        runOnUiThread() {
//            val selectSizeDialog = SelectSizeDialog.newInstance(PinterestList)
//            selectSizeDialog.setListener(object : SelectSizeDialog.UrlListener {
//                override fun geturl(url: String, name: String) {
//                    StartDownload(url, name)
//                    Toast.makeText(this@MainActivity, "Start Downloader..", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            })
//            selectSizeDialog.show(supportFragmentManager, "SelectDialog")
//            progressBar.visibility = View.GONE
//        }
//    }


    private fun StartDownload(downloadUrl: String, name: String) {

        val sd = SimpleDateFormat("yymmhh")
        val date = sd.format(Date())

        var Filename = "${date}_$name.jpg"
        var request: DownloadManager.Request = DownloadManager.Request(Uri.parse(downloadUrl))

        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        request.setTitle(Filename)
        request.setShowRunningNotification(true)
        request.setNotificationVisibility(android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalFilesDir(
            this, "/Download/" + getString(R.string.app_name) + "/",
            name
        )
        var id: Long = downloadManager.enqueue(request)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun onStart() {
        super.onStart()
        val intent = getIntent()
        val action = intent.action
        val type = intent.type
        if (Intent.ACTION_SEND == action && type != null) {
            handleSharedText(intent)
        }
    }

    private fun handleSharedText(intent: Intent) {
        var sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
        Log.d("MainActivity", "shared text is :" + sharedText!!)
        et_pinurl.setText("")
        sharedText?.let {
            et_pinurl.setText(sharedText.substring(sharedText.indexOf("https")))
        }
    }

}
