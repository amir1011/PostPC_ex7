package huji.postpc2021.amir1011.ex7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    var currOrder: SandwichOrder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        if(currOrder == null) {
            startActivity(Intent(this, NewOrderActivity::class.java))
            finish()
        }
    }
}