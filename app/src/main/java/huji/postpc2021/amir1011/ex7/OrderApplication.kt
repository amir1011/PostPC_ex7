package huji.postpc2021.amir1011.ex7

import android.app.Application
import com.google.firebase.FirebaseApp

class OrderApplication: Application() {

    private var currOrder: OrdersHolder? = null

    companion object {
        private var instance: OrderApplication? = null
        fun getInstance(): OrderApplication? {
            return instance
        }
    }


    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        instance = this
        currOrder = OrdersHolder(this)
    }

    fun getHolder(): OrdersHolder? {
        return currOrder
    }

}