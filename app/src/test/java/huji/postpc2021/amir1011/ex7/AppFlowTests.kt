package huji.postpc2021.amir1011.ex7

import android.app.Activity
import android.os.Looper
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.view.isVisible
import junit.framework.TestCase
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowApplication


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class MainActivityTest : TestCase() {
//    private var mainActController: ActivityController<MainActivity>? = null
    private var newOrderActController: ActivityController<NewOrderActivity>? = null
    private var editActController: ActivityController<EditOrderActivity>? = null
    private var makingActController: ActivityController<MakingOrderActivity>? = null
    private var readyActController: ActivityController<OrderIsReadyActivity>? = null

    @get:Rule
    val executorRule = InstantTaskExecutorRule()
//    @Before
//    fun setup() {
//
////        mainActController = Robolectric.buildActivity(MainActivity::class.java)
////        Robolectric.buildActivity(MainActivity::class.java)
////        Robolectric.buildActivity(OrderApplication::class.java)
////        OrderApplication.getInstance()!!.getHolder()
////        Robolectric.setupActivity(NewOrderActivity::class.java)
////        newOrderActController = Robolectric.buildActivity(NewOrderActivity::class.java)
////        Robolectric.setupActivity(EditOrderActivity::class.java)
////        editActController = Robolectric.buildActivity(EditOrderActivity::class.java)
////        makingActController =
////            Robolectric.buildActivity(MakingOrderActivity::class.java)
////        readyActController = Robolectric.buildActivity(OrderIsReadyActivity::class.java)
//    }

    @Test
    fun when_placeOrder_and_saveOrderButtonsClicked_then_editOrderActivityIsLunched() {
        // setup
        Robolectric.setupActivity(NewOrderActivity::class.java)
        newOrderActController = Robolectric.buildActivity(NewOrderActivity::class.java)
        newOrderActController!!.create().visible()
        var orderButton = newOrderActController!!.get().findViewById<View>(R.id.placeNewOrder)
        orderButton.performClick()
        orderButton = newOrderActController!!.get().findViewById<View>(R.id.saveOrder)
        orderButton.performClick()

//        // verify
//        val waitingActivity: WaitingActivity = assertTrue(userInput.isEmpty())
//        assert(Activity#getWindow().getDecorView().getRootView().isShown())

        Robolectric.setupActivity(EditOrderActivity::class.java)
        editActController = Robolectric.buildActivity(EditOrderActivity::class.java)
//        val editOrderActivity: EditOrderActivity = EditOrderActivity()
        shadowOf(Looper.getMainLooper()).idle()
        assert(editActController!!.get().currentlyRunning)
    }

    @Test
    fun when_previousTest_then_clickEditAndChangeOrder_then_clickUpdate() {
        // setup
//        Robolectric.setupActivity(NewOrderActivity::class.java)
        shadowOf(Looper.getMainLooper()).idle()
        newOrderActController = Robolectric.buildActivity(NewOrderActivity::class.java)
        newOrderActController!!.create().visible()
        var orderButton = newOrderActController!!.get().findViewById<View>(R.id.placeNewOrder)
        orderButton.performClick()
        orderButton = newOrderActController!!.get().findViewById<View>(R.id.saveOrder)
        orderButton.performClick()

        editActController!!.create().visible()
        var orderButton2 = editActController!!.get().findViewById<View>(R.id.placeNewOrder)
        orderButton2.performClick()
        orderButton2 = newOrderActController!!.get().findViewById<View>(R.id.increase)
        orderButton2.performClick()
        orderButton2.performClick()
        orderButton2 = editActController!!.get().findViewById<View>(R.id.saveOrder)
        orderButton2.performClick()

        // verify

//        shadowOf(Looper.getMainLooper()).idle()

//        val waitingActivity: WaitingActivity = assertTrue(userInput.isEmpty())
//        assert(Activity#getWindow().getDecorView().getRootView().isShown())

//        val editOrderActivity: EditOrderActivity = EditOrderActivity()
        assert(editActController!!.get().currentlyRunning &&
                editActController!!.get().findViewById<View>(R.id.deleteOrder).isVisible)
    }


    @Test
    fun when_orderReady_clickGotIt_then_newOrderActivityIsLunched() {
        // setup
        shadowOf(Looper.getMainLooper()).idle()
        Robolectric.setupActivity(OrderIsReadyActivity::class.java)
        readyActController = Robolectric.buildActivity(OrderIsReadyActivity::class.java)
        readyActController!!.create().visible()
        val orderButton = readyActController!!.get().findViewById<View>(R.id.gotIt)
        orderButton.performClick()

        // verify

//        val waitingActivity: WaitingActivity = assertTrue(userInput.isEmpty())
//        assert(Activity#getWindow().getDecorView().getRootView().isShown())

//        val editOrderActivity: EditOrderActivity = EditOrderActivity()

//        shadowOf(Looper.getMainLooper()).idle()

        assert(newOrderActController!!.get().currentlyRunning &&
                newOrderActController!!.get().findViewById<View>(R.id.placeNewOrder).isVisible)
    }

}