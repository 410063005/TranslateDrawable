package com.example.cm.translatedrawable

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_translate_demo.*

/**
 * Created by kingcmchen on 2018/4/26.
 */
class TranslateDemoActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, TranslateDemoActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translate_demo)


        demoProgress()
        demoBall()
        demoRectangle()
    }

    private fun demoRectangle() {
        val t = TranslateDrawable()
        t.drawable = ContextCompat.getDrawable(this, R.drawable.ic_red_rect)
        t.setWidth(100)

        val s = ShiningDrawable(ContextCompat.getDrawable(this, R.color.colorPrimaryDark), t)
        view_line3.background = s
        s.setLevelStep(100)
        s.setReverse(true)
        s.start()    }

    private fun demoBall() {
        val t = TranslateDrawable()
        t.drawable = ContextCompat.getDrawable(this, R.drawable.ic_green_ball)

        val s = ShiningDrawable(ContextCompat.getDrawable(this, R.color.c1), t)
        view_line2.background = s
        s.setLevelStep(100)
        s.start()
    }

    private fun demoProgress() {
        val t = TranslateDrawable()
        t.drawable = ContextCompat.getDrawable(this, R.color.colorAccent)

        val s = ShiningDrawable(ContextCompat.getDrawable(this, R.color.c1), t)
        view_line.background = s
        s.setLevelStep(50)
        s.start()
    }

}