package com.landside.sphtest.ui.mobile

import android.test.suitebuilder.annotation.LargeTest
import android.text.TextUtils
import android.view.View
import androidx.core.util.Preconditions.checkArgument
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.landside.sphtest.R
import com.landside.sphtest.ui.RecyclerViewMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class MobileUsageActivityTest {

    @get:Rule
    var mActivityRule: ActivityTestRule<MobileUsageActivity> =
        ActivityTestRule(MobileUsageActivity::class.java)

    @Test
    fun mobileUsage() {
        onView(withItemText("year:2009")).check(matches(isDisplayed()))
        onView(withItemText("year:2010")).check(matches(isDisplayed()))
        onView(withItemText("year:2011")).check(matches(isDisplayed()))
        onView(RecyclerViewMatcher(rvLayoutId).atPositionOnView(1, R.id.decrease))
            .check(matches(isDisplayed()))
        onView(RecyclerViewMatcher(rvLayoutId).atPositionOnView(1, R.id.decrease))
            .perform(ViewActions.click())
        Thread.sleep(1000)
        onView(withText("has decrease!"))
            .inRoot(withDecorView(not(mActivityRule.activity.window.decorView)))
            .check(matches(isDisplayed()))
    }

    private val rvLayoutId = R.id.usages

    private fun withItemText(itemText: String): Matcher<View?>? {
        checkArgument(!TextUtils.isEmpty(itemText), "itemText cannot be null or empty")
        return object : TypeSafeMatcher<View?>() {
            override fun matchesSafely(item: View?): Boolean {
                return allOf(
                    isDescendantOfA(withId(rvLayoutId)),
                    withText(itemText)
                ).matches(item)
            }

            override fun describeTo(description: Description) {
                description.appendText("is isDescendantOfA RV with text $itemText")
            }
        }
    }

}