# PinterestDownloader

### How to Use



- [1](https://github.com/sunheihei/PinterestDownloader/blob/master/Screenshot_20191105-150909.png)

- [2](https://github.com/sunheihei/PinterestDownloader/blob/master/Screenshot_20191105-150930.png)





gradle:

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

```
dependencies {
	        implementation 'com.github.sunheihei:PinterestDownloader:1.0.0'
	}
```





```kotlin
 val pin = PinDownloadManager()
 pin.getPinUrl(et_pinurl.getText().toString(), this)           
```



#### interface

```kotlin
override fun StartAnalysis() {
        //mainthread
        progressBar.visibility = View.VISIBLE
    }

 override fun AnalysisSuccess(PinterestList: MutableList<Pinterest>) {
        runOnUiThread {
            //dosomething
            //PinterestList Contains images of different sizes
            //Pinterest(url: String, var width: String, var height: String) 
        }
    }

    override fun AnalysisFail() {
        runOnUiThread {
            //dosomething
        }
    }
```

