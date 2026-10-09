package ru.asr.firstapplication

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)

        findViewById<Button>(R.id.btnOpenSecond).setOnClickListener { openSecondActivity() }
        findViewById<Button>(R.id.btnCall).setOnClickListener { callFriend() }
        findViewById<Button>(R.id.btnShare).setOnClickListener { shareText() }
    }

    private fun openSecondActivity() {
        val text = getValidatedText() ?: return

        val intent = Intent(this, SecondActivity::class.java).apply {
            putExtra(SecondActivity.EXTRA_TEXT, text)
        }
        startActivity(intent)
    }

    private fun callFriend() {
        val phone = editText.text.toString().trim()

        if (phone.isEmpty()) {
            showError(getString(R.string.error_empty))
            return
        }
        if (!PHONE_REGEX.matches(phone)) {
            showError(getString(R.string.error_invalid_phone))
            return
        }

        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:" + Uri.encode(phone))
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, R.string.error_no_dialer, Toast.LENGTH_SHORT).show()
        }
    }

    private fun shareText() {
        val text = getValidatedText() ?: return

        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        startActivity(Intent.createChooser(sendIntent, getString(R.string.share_title)))
    }

    private fun getValidatedText(): String? {
        val text = editText.text.toString().trim()
        if (text.isEmpty()) {
            showError(getString(R.string.error_empty))
            return null
        }
        return text
    }

    private fun showError(message: String) {
        editText.error = message
        editText.requestFocus()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private companion object {
        val PHONE_REGEX = Regex("^\\+?[0-9\\s\\-()]{5,20}$")
    }
}