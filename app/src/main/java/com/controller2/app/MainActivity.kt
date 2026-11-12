package com.controller2.app

import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        Toast.makeText(this, "Controller 2 Active (Hidden Mode)", Toast.LENGTH_LONG).show()
    }

    // Volume Up ya Down dabane par app foreground me aa jayegi
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            Toast.makeText(this, "Shortcut Triggered!", Toast.LENGTH_SHORT).show()
            // Yahan aap app ka main control panel ya dashboard open kar sakte hain
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
