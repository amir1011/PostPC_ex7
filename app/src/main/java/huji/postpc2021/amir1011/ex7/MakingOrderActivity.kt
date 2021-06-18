package huji.postpc2021.amir1011.ex7

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MakingOrderActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_making_order)

        //listener on status changes in order to open a right activity
        val currHolder = OrderApplication.getInstance()!!.getHolder()
        currHolder!!.getFarebase().collection("orders")
                .document(currHolder.getCurrOrder()!!.getSandwichId())
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        e.printStackTrace()
                        return@addSnapshotListener
                    }
                    if (snapshot != null && snapshot.exists()) {
                        val order = snapshot.toObject(SandwichOrder::class.java)
                        if (order!!.getSandwichStatus() == "ready") {
                            currHolder.getCurrOrder()!!.setSandwichStatus(order.getSandwichStatus())
                            startActivity(Intent(this, OrderIsReadyActivity::class.java))
                            finish()
                        }
                    }
                }
    }
}