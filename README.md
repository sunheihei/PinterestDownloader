# PinterestDownloader

### How to Use









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

