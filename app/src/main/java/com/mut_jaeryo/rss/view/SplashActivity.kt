package com.mut_jaeryo.rss.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mut_jaeryo.rss.R
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {
    /*
    라이브러리 출처
    jsoup -< https://github.com/jhy/jsoup
    glide -< https://github.com/bumptech/glide
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

       CoroutineScope(Dispatchers.Main).launch {
           delay(1300)  //1.3 second
           val intent  = Intent(this@SplashActivity,MainActivity::class.java)
           startActivity(intent)
           this@SplashActivity.finish()
           this@SplashActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
       }
    }
}
