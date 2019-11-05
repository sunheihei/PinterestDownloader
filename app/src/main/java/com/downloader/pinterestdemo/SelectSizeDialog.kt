package com.downloader.pinterestdemo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.core.view.isEmpty
import androidx.fragment.app.DialogFragment
import com.downloader.pinterestdownloader.Pinterest
import kotlinx.android.synthetic.main.sizeselect_dialog_layout.*
import java.io.Serializable

@Suppress("UNCHECKED_CAST")
class SelectSizeDialog : DialogFragment() {

    var ImgSizeList: MutableList<Pinterest>? = null

    companion object {
        public fun newInstance(list: MutableList<Pinterest>): SelectSizeDialog {
            var dialog = SelectSizeDialog()
            val args = Bundle()
            args.putSerializable("list", list as Serializable)
            dialog.setArguments(args)
            return dialog
        }
    }


    internal var mlistener: UrlListener? = null

    interface UrlListener {
        fun geturl(url: String, name: String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth)

        val arguments: Bundle? = getArguments()
        if (arguments != null) {
            ImgSizeList = arguments.getSerializable("list") as MutableList<Pinterest>
        }

//        Log.d("SelectSizeDialog", ImgSizeList!!.size.toString())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val view = inflater.inflate(R.layout.sizeselect_dialog_layout, container)
        return view
    }

    override fun onStart() {
        super.onStart()
        Log.d("SelectSizeDialog", "onStart")
        initveiw()
    }

    private fun initveiw() {
        if ( !button_view.isEmpty()){
            button_view.removeAllViews()
        }

        for (i in 0 until ImgSizeList!!.size) {
            val btn = Button(context)
            btn.setText("${ImgSizeList!!.get(i).width} x ${ImgSizeList!!.get(i).height}")
            btn.setOnClickListener {
                Log.d("SelectSizeDialog", ImgSizeList!!.get(i).url)
                mlistener!!.geturl(ImgSizeList!!.get(i).url,"${ImgSizeList!!.get(i).width} x ${ImgSizeList!!.get(i).height}")
            }
            button_view.addView(btn)
        }
    }


    fun setListener(listener: UrlListener) {
        this.mlistener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}


