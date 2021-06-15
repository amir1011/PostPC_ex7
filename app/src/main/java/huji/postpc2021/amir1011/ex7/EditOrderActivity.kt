package huji.postpc2021.amir1011.ex7

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class EditOrderActivity(order: SandwichOrder): AppCompatActivity(){

    private var currOrder: SandwichOrder = order
    private var comment : EditText? = null
    private var addPickle : Button? = null
    private var subtractPickle : Button? = null
    private var editOrder : Button? = null
    private var updateOrder : Button? = null
    private var pickleCounter : TextView? = null
    private var hummusCheck : CheckBox? = null
    private var tahiniCheck : CheckBox? = null
    private var deleteOrder : Button? = null
    private var status : TextView? = null
    private var nameText : EditText? = null

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
        deleteOrder = findViewById(R.id.deleteOrder)
        addPickle = findViewById(R.id.increase)
        subtractPickle = findViewById(R.id.decrease)
        subtractPickle = findViewById(R.id.decrease)
        pickleCounter = findViewById(R.id.integer_number)
        hummusCheck = findViewById(R.id.hummusCheckBox)
        val hummus = findViewById<EditText>(R.id.hummus)
        tahiniCheck = findViewById(R.id.tahiniCheckBox)
        val tahini = findViewById<EditText>(R.id.tahini)
        comment = findViewById(R.id.comment)
        val pickles = findViewById<EditText>(R.id.pickles)
        editOrder = findViewById(R.id.placeNewOrder)
        updateOrder = findViewById(R.id.saveOrder)
        status = findViewById(R.id.orderStatus)
        nameText = findViewById(R.id.name)

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

        updateOrder!!.setOnClickListener{
            //todo go to another activity and update firebase
        }

        editOrder!!.setOnClickListener {

            editOrder!!.visibility = View.GONE
            deleteOrder!!.visibility = View.GONE
            status!!.visibility = View.GONE
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
            nameText!!.visibility = View.VISIBLE

            nameText!!.setText(currOrder.getSandwichName())
            pickleCounter!!.setText(currOrder.getSandwichPickles())
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
    }
}