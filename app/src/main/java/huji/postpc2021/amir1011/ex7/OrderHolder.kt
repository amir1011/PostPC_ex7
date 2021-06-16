package huji.postpc2021.amir1011.ex7

import android.R.id.message
import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.concurrent.schedule


class OrdersHolder(private var context: Context) {

//    var orders: HashMap<String, SandwichOrder> = HashMap<String, SandwichOrder>()

    private val fireStore = FirebaseFirestore.getInstance()
    private var currOrder: SandwichOrder? = null

    private var currOrderID: String? = null
    private var currOrderName: String? = null
    private val sp: SharedPreferences = context.getSharedPreferences(
        "local_db_todoItems",
        Context.MODE_PRIVATE
    )

    init {
        currOrderID = sp.getString("id", null)
        currOrderName = sp.getString("name", null)
    }

    fun setCurrOrder(order: SandwichOrder)
    {
        currOrder = order
    }

    fun getOrderFirebase(idFromSP: String): SandwichOrder?
    {
        if(currOrder == null)
        {
            fireStore.collection("orders").document(idFromSP).get()
                .addOnSuccessListener { document: DocumentSnapshot? ->
                    if (document != null)
                        currOrder = document.toObject(SandwichOrder::class.java)
                }
                .addOnFailureListener { println("Error: (") }
//            do {
//                if (currOrder!=null) break
//            } while (currOrder==null)
//            var counter: Int = 0
//            while(counter<Int.MAX_VALUE)
//                counter++
        }
        return currOrder
    }

    fun putNewOrderFirebase()
    {
        fireStore.collection("orders").add(currOrder!!)
            .addOnSuccessListener {  }
    }

    fun updateOrder(id: String)
    {
        fireStore.collection("orders").document(id).set(currOrder!!)
            .addOnSuccessListener {  }
    }

    fun putIdSp(str: String)
    {
        val editor: SharedPreferences.Editor = sp.edit()
            editor.putString("id", str)
            editor.apply()
    }

    fun getCurrOrderId(): String?
    {
//        val editor: SharedPreferences.Editor = sp.edit()
//        editor.remove("id")
//        editor.apply()
        return currOrderID
    }

    fun putNameSp(str: String)
    {
        val editor: SharedPreferences.Editor = sp.edit()
        editor.putString("name", str)
        editor.apply()
    }

    fun getCurrOrderName(): String?
    {
        return currOrderName
    }

    fun getFarebase(): FirebaseFirestore
    {
        return fireStore
    }

    fun getCurrOrder(): SandwichOrder?
    {
//        if(currOrder==null)
//        {
//        do {
//            if (currOrder!=null) break
//        } while (currOrder==null)
//        }
        return currOrder
    }

//    fun makeNewOrder(name: String, pickles: Int, tahini: Boolean, hummus: Boolean,
//                     comment: String, id: String?){
//        val newOrder: SandwichOrder = SandwichOrder(name, pickles, tahini, hummus, comment, id)
//        orders[newOrder.getSandwichId()] = newOrder
//    }
//
//    fun deleteOrder(orderId: String) {
//        orders.remove(orderId)
//    }



//    fun changeStatus(orderId: String, status: Status){
//        orders[orderId]?.setSandwichStatus(status)
//    }
//
//    fun changePickles(orderId: String, pickles: Int){
//        orders[orderId]?.setSandwichPickles(pickles)
//    }
//
//    fun changeComment(orderId: String, comment: String){
//        orders[orderId]?.setSandwichComment(comment)
//    }




//    fun editOrder(orderId: String, comment: String?, pickles: Int?, status: Status?,
//                  tahini: Boolean?, hummus: Boolean?){
//        val orderToChange = orders[orderId]
//        deleteOrder(orderId)
//        makeNewOrder(orderToChange!!.getSandwichName()!!,
//            pickles ?: orderToChange.getSandwichPickles(),
//            tahini ?: orderToChange.getSandwichTahini(),
//            hummus ?: orderToChange.getSandwichHummus(),
//            comment ?: orderToChange.getSandwichComment(),
//             orderToChange.getSandwichId())
//        //todo update SP (lifeData)
//    }
}