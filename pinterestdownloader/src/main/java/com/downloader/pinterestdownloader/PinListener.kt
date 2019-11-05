package com.downloader.pinterestdownloader

interface PinListener {

    fun StartAnalysis()

    fun AnalysisSuccess(PinterestList: MutableList<Pinterest>)

    fun AnalysisFail()

}