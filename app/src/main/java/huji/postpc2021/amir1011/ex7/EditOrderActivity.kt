package huji.postpc2021.amir1011.ex7

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class EditOrderActivity: AppCompatActivity(){

    private val currHolder: OrdersHolder = OrderApplication.getInstance()!!.getHolder()!!
    private var currOrder: SandwichOrder = currHolder.getCurrOrder()!!
    private var nameText : EditText? = null
    private var comment : EditText? = null
    private var addPickle : Button? = null
    private var subtractPickle : Button? = null
    private var editOrder : Button? = null
    private var updateOrder : Button? = null
    private var pickleCounter : TextView? = null
    private var hummusCheck : CheckBox? = null
    private var tahiniCheck : CheckBox? = null

    private fun getNumPickles(num: Int): Int{
        return when {
            num >= 10 -> 10
            num <= 0 -> 0
            else -> num
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_activity_order)

        /* find all UI elements */
//        val orderButton = findViewById<Button>(R.id.placeNewOrder)
        nameText = findViewById(R.id.name)
        val perName = findViewById<TextView>(R.id.perName)
        addPickle = findViewById(R.id.increase)
        subtractPickle = findViewById(R.id.decrease)
        subtractPickle = findViewById(R.id.decrease)
        pickleCounter = findViewById(R.id.integer_number)
        hummusCheck = findViewById(R.id.hummusCheckBox)
        val hummus = findViewById<TextView>(R.id.hummus)
        tahiniCheck = findViewById(R.id.tahiniCheckBox)
        val tahini = findViewById<TextView>(R.id.tahini)
        val pickles = findViewById<TextView>(R.id.pickles)
        val delete = findViewById<Button>(R.id.deleteOrder)
        val status = findViewById<TextView>(R.id.orderStatus)
        comment = findViewById(R.id.comment)
        editOrder = findViewById(R.id.placeNewOrder)
        updateOrder = findViewById(R.id.saveOrder)

        status!!.text = "Status of the order: Waiting"
        updateOrder!!.text = "Update this order"
        editOrder!!.text = "Edit your order"

        addPickle!!.visibility = View.GONE
        subtractPickle!!.visibility = View.GONE
        subtractPickle!!.visibility = View.GONE
        pickleCounter!!.visibility = View.GONE
        pickles!!.visibility = View.GONE
        hummus.visibility = View.GONE
        tahini.visibility = View.GONE
        hummusCheck!!.visibility = View.GONE
        tahiniCheck!!.visibility = View.GONE
        comment!!.visibility = View.GONE
        updateOrder!!.visibility = View.GONE
        nameText!!.visibility = View.GONE
        perName.visibility = View.GONE

        perName.text = nameText!!.text

        /* define on click listeners */
        addPickle!!.setOnClickListener{
            val numPickles = getNumPickles(pickleCounter!!.text.toString().toInt()+1)
            pickleCounter!!.text = (numPickles).toString()
            currOrder.setSandwichPickles(numPickles)
        }

        subtractPickle!!.setOnClickListener{
            val numPickles = getNumPickles(pickleCounter!!.text.toString().toInt()-1)
            pickleCounter!!.text = (numPickles).toString()
            currOrder.setSandwichPickles(numPickles)
        }

        hummusCheck!!.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked)
                currOrder.setSandwichHummus(true)
            else
                currOrder.setSandwichHummus(false)
        }

        tahiniCheck!!.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked)
                currOrder.setSandwichTahini(true)
            else
                currOrder.setSandwichTahini(false)
        }

        comment!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                currOrder.setSandwichComment(comment!!.text.toString())
            }
        })

        delete!!.setOnClickListener{
            currHolder.getFarebase().collection("orders")
                    .document(currOrder.getSandwichId()).delete()
                    .addOnSuccessListener {
                        currHolder.removeOrderFromSp()
                        tahiniCheck!!.isChecked = false
                        hummusCheck!!.isChecked = false
                        startActivity(Intent(this, NewOrderActivity::class.java))
                        finish()
                    }
        }

        updateOrder!!.setOnClickListener{
            //todo go to another activity and update firebase
            currHolder.setCurrOrder(currOrder)
            currHolder.updateOrder(currOrder.getSandwichId())

            editOrder!!.visibility = View.VISIBLE
            delete.visibility = View.VISIBLE
            status.visibility = View.VISIBLE

            addPickle!!.visibility = View.GONE
            subtractPickle!!.visibility = View.GONE
            subtractPickle!!.visibility = View.GONE
            pickleCounter!!.visibility = View.GONE
            pickles.visibility = View.GONE
            hummus.visibility = View.GONE
            tahini.visibility = View.GONE
            hummusCheck!!.visibility = View.GONE
            tahiniCheck!!.visibility = View.GONE
            comment!!.visibility = View.GONE
            updateOrder!!.visibility = View.GONE
            perName.visibility = View.GONE
        }

        editOrder!!.setOnClickListener {

            editOrder!!.visibility = View.GONE
            delete.visibility = View.GONE
            status.visibility = View.GONE
            addPickle!!.visibility = View.VISIBLE
            subtractPickle!!.visibility = View.VISIBLE
            subtractPickle!!.visibility = View.VISIBLE
            pickleCounter!!.visibility = View.VISIBLE
            pickles.visibility = View.VISIBLE
            hummusCheck!!.visibility = View.VISIBLE
            tahiniCheck!!.visibility = View.VISIBLE
            comment!!.visibility = View.VISIBLE
            updateOrder!!.visibility = View.VISIBLE
            hummus.visibility = View.VISIBLE
            tahini.visibility = View.VISIBLE
            perName.visibility = View.VISIBLE


//            nameText!!.setText(currOrder.getSandwichName())
            perName!!.text = currOrder.getSandwichName()
            pickleCounter!!.text = currOrder.getSandwichPickles().toString()
            comment!!.setText(currOrder.getSandwichComment())
            nameText!!.setText(currOrder.getSandwichName())
            hummusCheck!!.isChecked = currOrder.getSandwichHummus()
            tahiniCheck!!.isChecked = currOrder.getSandwichTahini()

//            orderButton.text = "Update Order"
//            MainApp.instance.getDataBase().getOrderStatusLiveData(newOrder.getID()).observe(
//                this, {
//                    val nextActivityIntent = Intent(this, OrderInProgressActivity::class.java)
//                    nextActivityIntent.putExtra("order_id", newOrder.getID())
//                    finish()
//                }
        }

        //listener on status changes in order to open a right activity
        currHolder.getFarebase().collection("orders")
                .document(currOrder.getSandwichId()).addSnapshotListener { snapshot, e ->
            if (e != null) {
                e.printStackTrace()
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                val order = snapshot.toObject(SandwichOrder::class.java)
                if (order!!.getSandwichStatus() == "in-progress")
                {
                    currHolder.getCurrOrder()!!.setSandwichStatus(order.getSandwichStatus())
                    startActivity(Intent(this, MakingOrderActivity::class.java))
                    finish()
                } else if (order.getSandwichStatus() == "ready")
                {
                    currHolder.getCurrOrder()!!.setSandwichStatus(order.getSandwichStatus())
                    startActivity(Intent(this, OrderIsReadyActivity::class.java))
                    finish()
                }
            }

        }
    }
}