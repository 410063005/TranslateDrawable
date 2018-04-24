package com.example.cm.translatedrawable

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var mType: String = "a"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        translateDrawable(findViewById(R.id.button))
    }

    fun onClick(view : View) {
        if (mType == "a") {
            translateDrawable(view)
        } else {
            animationDrawable(view)
        }
    }

    private fun animationDrawable(view: View) {
        Log.d("MainActivity", "animationDrawable")

        mType = "a"
        (view as Button).text = "animationDrawable"

        val tvText = findViewById<TextView>(R.id.tv_text) as TextView
        val ad = ContextCompat.getDrawable(this, R.drawable.ic_png) as AnimationDrawable
        tvText.background = ad
        ad.start()
    }

    private fun translateDrawable(view: View) {
        Log.d("MainActivity", "translateDrawable")

        mType = "t"
        (view as Button).text = "translateDrawable"

        val t = TranslateDrawable()
        t.drawable = ContextCompat.getDrawable(this, R.drawable.ic_demo)
        t.setOffset(50)

        val s = ShiningDrawable(ContextCompat.getDrawable(this, R.color.c1), t)
        val tvText = findViewById<TextView>(R.id.tv_text) as TextView
        tvText.background = s

        s.start()
    }
}
