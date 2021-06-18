package huji.postpc2021.amir1011.ex7

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class OrderIsReadyActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_is_ready)

        findViewById<Button>(R.id.gotIt).setOnClickListener{
            val currHolder = OrderApplication.getInstance()!!.getHolder()!!
            currHolder.getFarebase().collection("orders")
                    .document(currHolder.getCurrOrder()!!.getSandwichId()).delete()
                    .addOnSuccessListener {
                        currHolder.removeOrderFromSp()
                        startActivity(Intent(this, NewOrderActivity::class.java))
                        finish()
                    }
        }
    }
}