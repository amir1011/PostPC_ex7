package huji.postpc2021.amir1011.ex7

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import junit.framework.TestCase.*
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class AndroidAppFlowTests {
    private lateinit var activityController: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        OrderApplication.getInstance()!!.getHolder()!!.removeOrderFromSp()
//        App.instance!!.order?.status = "NewOrder"
        activityController = launch(MainActivity::class.java)
    }

    @After
    fun clean(){
        OrderApplication.getInstance()!!.getHolder()!!.removeOrderFromSp()
    }

    private fun createNewOrder(name: String, withH: Boolean, withT: Boolean, pickles: Int, comment: String)
    {
//        OrderApplication.getInstance()!!.getHolder()!!.removeOrderFromSp()
        onView(withId(R.id.placeNewOrder)).perform(ViewActions.click())
        if(name != "")
        {
            onView(withId(R.id.name)).perform(ViewActions.clearText())
            onView(withId(R.id.name)).perform(ViewActions.typeText(name))
            closeSoftKeyboard()
        }
        if(withH)
            onView(withId(R.id.hummusCheckBox)).perform(ViewActions.click())
        if(withT)
            onView(withId(R.id.tahiniCheckBox)).perform(ViewActions.click())
        var temp = pickles
        while(temp>0)
        {
            onView(withId(R.id.increase)).perform(ViewActions.click())
            temp-=1
        }
        if(comment != "") {
            onView(withId(R.id.comment)).perform(ViewActions.typeText(comment))
            closeSoftKeyboard()
        }
        onView(withId(R.id.saveOrder)).perform(ViewActions.click())
    }

    @Test
    fun createNewOrder_then_orderSavedInApp() {

//        onView(withId(R.id.placeNewOrder)).perform(ViewActions.click())

        createNewOrder("Alfred ", withH = false, withT = true, 5,"First test")

        assertFalse(OrderApplication.getInstance()!!.getHolder()!!.getCurrOrder()!!.getSandwichHummus())
        assertTrue(OrderApplication.getInstance()!!.getHolder()!!.getCurrOrder()!!.getSandwichTahini())
        assertEquals(5,
            OrderApplication.getInstance()!!.getHolder()!!.getCurrOrder()!!.getSandwichPickles())
        assertEquals("First test",
            OrderApplication.getInstance()!!.getHolder()!!.getCurrOrder()!!.getSandwichComment())
        assertEquals("Alfred ",
            OrderApplication.getInstance()!!.getHolder()!!.getCurrOrder()!!.getSandwichName())

        OrderApplication.getInstance()!!.getHolder()!!.deleteCurOrder()
    }

    @Test
    fun createOrder_then_inEditScreen_deleteOrder_thenMainScreenShouldBeShown() {

        createNewOrder("Robin", withH = true, withT = true, 9,"Second test")
        assertTrue(OrderApplication.getInstance()!!.getHolder()!!.getCurrOrder()!!.getSandwichHummus())
        assertTrue(OrderApplication.getInstance()!!.getHolder()!!.getCurrOrder()!!.getSandwichTahini())
        assertEquals(9,
            OrderApplication.getInstance()!!.getHolder()!!.getCurrOrder()!!.getSandwichPickles())
        assertEquals("Robin",
            OrderApplication.getInstance()!!.getHolder()!!.getCurrOrder()!!.getSandwichName())
        assertEquals("Second test",
            OrderApplication.getInstance()!!.getHolder()!!.getCurrOrder()!!.getSandwichComment())

        onView(withId(R.id.deleteOrder)).check(matches(isDisplayed()))
        onView(withId(R.id.deleteOrder)).perform(ViewActions.click())
        Thread.sleep(1000)
        onView(withId(R.id.placeNewOrder)).check(matches(isDisplayed()))
        assertTrue(OrderApplication.getInstance()!!.getHolder()!!.spIsEmpty())
        onView(withId(R.id.deleteOrder)).check(matches(not(isDisplayed())))
    }

    @Test fun createOrder_then_changeStatusInFireBase_then_MakingOrderActivityCheck_then_changeStatusInFireBase_then_gotItClickAndBackToMainScreen() {
        createNewOrder("Batman", withH = true, withT = false, 3,"Third test")
        OrderApplication.getInstance()!!.getHolder()!!.updateOrderStatus("in-progress")
        Thread.sleep(1000)
        onView(withId(R.id.orderIsReady)).check(matches(isDisplayed()))
        onView(withId(R.id.explanation)).check(matches(isDisplayed()))
        OrderApplication.getInstance()!!.getHolder()!!.updateOrderStatus("ready")
        Thread.sleep(1000)
        onView(withId(R.id.orderIsReady)).check(matches(isDisplayed()))
        onView(withId(R.id.gotIt)).check(matches(isDisplayed()))
        onView(withId(R.id.gotIt)).perform(ViewActions.click())
        Thread.sleep(1000)
        onView(withId(R.id.placeNewOrder)).check(matches(isDisplayed()))
        assertTrue(OrderApplication.getInstance()!!.getHolder()!!.spIsEmpty())
        onView(withId(R.id.deleteOrder)).check(matches(not(isDisplayed())))
    }
}