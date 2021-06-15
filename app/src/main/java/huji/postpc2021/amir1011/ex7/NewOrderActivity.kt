package huji.postpc2021.amir1011.ex7

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class NewOrderActivity: AppCompatActivity(){

    private var currOrder: SandwichOrder? = null
    private var nameText : EditText? = null
    private var comment : EditText? = null
    private var addPickle : Button? = null
    private var subtractPickle : Button? = null
    private var placeOrder : Button? = null
    private var saveOrder : Button? = null
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
        addPickle = findViewById(R.id.increase)
        subtractPickle = findViewById(R.id.decrease)
        subtractPickle = findViewById(R.id.decrease)
        pickleCounter = findViewById(R.id.integer_number)
        hummusCheck = findViewById(R.id.hummusCheckBox)
        val hummus = findViewById<EditText>(R.id.hummus)
        tahiniCheck = findViewById(R.id.tahiniCheckBox)
        val tahini = findViewById<EditText>(R.id.tahini)
        val pickles = findViewById<EditText>(R.id.pickles)
        comment = findViewById(R.id.comment)
        placeOrder = findViewById(R.id.placeNewOrder)
        saveOrder = findViewById(R.id.saveOrder)


        nameText!!.visibility = View.GONE
        addPickle!!.visibility = View.GONE
        subtractPickle!!.visibility = View.GONE
        subtractPickle!!.visibility = View.GONE
        pickleCounter!!.visibility = View.GONE
        hummusCheck!!.visibility = View.GONE
        tahiniCheck!!.visibility = View.GONE
        comment!!.visibility = View.GONE
        saveOrder!!.visibility = View.GONE
        hummus.visibility = View.GONE
        tahini.visibility = View.GONE
        pickles.visibility = View.GONE

        /* define on click listeners */
        addPickle!!.setOnClickListener{
            val numPickles = getNumPickles(pickleCounter!!.text.toString().toInt()+1)
            pickleCounter!!.text = (numPickles).toString()
            currOrder!!.setSandwichPickles(numPickles)
        }

        subtractPickle!!.setOnClickListener{
            val numPickles = getNumPickles(pickleCounter!!.text.toString().toInt()-1)
            pickleCounter!!.text = (numPickles).toString()
            currOrder!!.setSandwichPickles(numPickles)
        }

        hummusCheck!!.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked)
                currOrder!!.setSandwichHummus(true)
            else
                currOrder!!.setSandwichHummus(false)
        }

        tahiniCheck!!.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked)
                currOrder!!.setSandwichTahini(true)
            else
                currOrder!!.setSandwichTahini(false)
        }

        nameText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
//                if(currOrder!!.getSandwichName() == null)
                    currOrder!!.setSandwichName(nameText!!.text.toString())
//                else
//                    nameText!!.setText(currOrder!!.getSandwichName())
            }
        })

        comment!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                currOrder!!.setSandwichComment(comment!!.text.toString())
            }
        })

        saveOrder!!.setOnClickListener{
            //todo go to another activity and update firebase
        }

        placeOrder!!.setOnClickListener {
            currOrder = SandwichOrder(null,
                                        pickleCounter!!.text.toString().toInt(),
                                        tahiniCheck!!.isChecked,
                                        hummusCheck!!.isChecked,
                              comment?.text?.toString()?:"",
                                        null)

            placeOrder!!.visibility = View.GONE
            nameText!!.visibility = View.VISIBLE
            addPickle!!.visibility = View.VISIBLE
            subtractPickle!!.visibility = View.VISIBLE
            subtractPickle!!.visibility = View.VISIBLE
            pickleCounter!!.visibility = View.VISIBLE
            pickles.visibility = View.VISIBLE
            hummusCheck!!.visibility = View.VISIBLE
            tahiniCheck!!.visibility = View.VISIBLE
            comment!!.visibility = View.VISIBLE
            saveOrder!!.visibility = View.VISIBLE
            hummus.visibility = View.VISIBLE
            tahini.visibility = View.VISIBLE

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