package com.downloader.pinterestdownloader

interface PinListener {

    fun StartAnalysis()

    fun AnalysisSuccess(url: String)

    fun AnalysisFail()

}