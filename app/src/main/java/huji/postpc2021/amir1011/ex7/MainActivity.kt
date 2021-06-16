package huji.postpc2021.amir1011.ex7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.DocumentSnapshot

class MainActivity : AppCompatActivity() {
    var currOrder: SandwichOrder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        val idFromSP: String? = OrderApplication.getInstance()!!.getHolder()!!.getCurrOrderId()

        if(idFromSP == null)
        {
            startActivity(Intent(this, NewOrderActivity::class.java))
            finish()
        }
        else
        {
            OrderApplication.getInstance()!!.getHolder()!!.getFarebase().collection("orders").
                document(idFromSP).get()
                .addOnSuccessListener {  document: DocumentSnapshot? ->
                    if (document != null) {
                        currOrder = document.toObject(SandwichOrder::class.java)
                        OrderApplication.getInstance()!!.getHolder()!!.setCurrOrder(currOrder!!)
                    }
                    when (currOrder!!.getSandwichStatus()) {
                        Status.Waiting -> startActivity(Intent(this, EditOrderActivity::class.java))
                        Status.InProgress -> startActivity(Intent(this, MakingOrderActivity::class.java))
//                Status.Ready -> startActivity(Intent(this, OrderIsReadyActivity::class.java))

//                Status.Done -> { // Note the block
//                    print("x is neither 1 nor 2")
                        else -> startActivity(Intent(this, OrderIsReadyActivity::class.java))
                }


//            OrderApplication.getInstance()!!.getHolder()!!.getOrderFirebase(idFromSP)
//            currOrder = OrderApplication.getInstance()!!.getHolder()!!.getCurrOrder()
//            when (currOrder!!.getSandwichStatus()) {
//                Status.Waiting -> startActivity(Intent(this, EditOrderActivity::class.java))
//                Status.InProgress -> startActivity(Intent(this, MakingOrderActivity::class.java))
////                Status.Ready -> startActivity(Intent(this, OrderIsReadyActivity::class.java))
//
////                Status.Done -> { // Note the block
////                    print("x is neither 1 nor 2")
//                else -> startActivity(Intent(this, OrderIsReadyActivity::class.java))
            }
        }
    }
}