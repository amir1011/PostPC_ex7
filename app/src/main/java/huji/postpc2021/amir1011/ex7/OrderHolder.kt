package huji.postpc2021.amir1011.ex7

import java.util.*
import kotlin.collections.HashMap

class OrdersHolder {
    var orders: HashMap<String, SandwichOrder> = HashMap<String, SandwichOrder>()


    fun makeNewOrder(name: String, pickles: Int, tahini: Boolean, hummus: Boolean,
                     comment: String, id: String?){
        val newOrder: SandwichOrder = SandwichOrder(name, pickles, tahini, hummus, comment, id)
        orders[newOrder.getSandwichId()] = newOrder
    }

    fun deleteOrder(orderId: String) {
        orders.remove(orderId)
    }

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

    fun editOrder(orderId: String, comment: String?, pickles: Int?, status: Status?,
                  tahini: Boolean?, hummus: Boolean?){
        val orderToChange = orders[orderId]
        deleteOrder(orderId)
        makeNewOrder(orderToChange!!.getSandwichName()!!,
            pickles ?: orderToChange.getSandwichPickles(),
            tahini ?: orderToChange.getSandwichTahini(),
            hummus ?: orderToChange.getSandwichHummus(),
            comment ?: orderToChange.getSandwichComment(),
             orderToChange.getSandwichId())
        //todo update SP (lifeData)
    }
}