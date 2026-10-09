package ru.asr.firstapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val text = intent.getStringExtra(EXTRA_TEXT)

        findViewById<TextView>(R.id.tvReceived).text =
            if (text.isNullOrBlank()) getString(R.string.no_data) else text
    }

    companion object {
        const val EXTRA_TEXT = "extra_text"
    }
}