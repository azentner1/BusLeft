package com.andrej.busleft

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import com.andrej.busleft.base.ui.components.TEST_TAG_LOADING_INDICATOR
import com.andrej.busleft.features.route_details.ui.components.TEST_TAG_ROUTE_DETAILS
import com.andrej.busleft.features.route_details.ui.components.TEST_TAG_ROUTE_DETAILS_INFO
import com.andrej.busleft.features.routes.ui.components.TEST_TAG_ARG_ROUTE_LIST_ITEM
import com.andrej.busleft.features.routes.ui.components.TEST_TAG_ROUTE_LIST
import com.andrej.busleft.features.routes.ui.components.TEST_TAG_ROUTE_LIST_ITEM

import org.junit.Test

import org.junit.Rule

@OptIn(ExperimentalTestApi::class)
class UiFlowTest {

    @get:Rule
    val composeRule = createAndroidComposeRule(MainActivity::class.java)

    @Test
    fun appUiFlowTest() {
        // Works with targetApi = 34 & upgraded Gradle plugin - commented out for now, but shows how this should be done

        /***
            // assert loading indicator visible
            appStartLoadingIndicator().assertIsDisplayed()
            // wait for remote call
            composeRule.waitUntilExactlyOneExists(hasTestTag(TEST_TAG_ROUTE_LIST))
            // assert route list has been loaded
            routeListComponent().assertIsDisplayed()
            // click on a route list item to go to details screen
            routeListItemComponent().performClick()
            // wait for details screen to render (and complete remote call)
            composeRule.waitUntilExactlyOneExists(hasTestTag(TEST_TAG_ROUTE_DETAILS))
            // assert UI elements are visible
            routeDetailsComponent().assertIsDisplayed()
            routeDetailsInfoComponent().assertIsDisplayed()
         ***/
    }

    private fun appStartLoadingIndicator(): SemanticsNodeInteraction {
        return composeRule.onNode(hasTestTag(TEST_TAG_LOADING_INDICATOR), true)
    }

    private fun routeListComponent(): SemanticsNodeInteraction {
        return composeRule.onNode(hasTestTag(TEST_TAG_ROUTE_LIST), true)
    }

    private fun routeListItemComponent(): SemanticsNodeInteraction {
        return composeRule.onNode(
            hasTestTag(
                TEST_TAG_ROUTE_LIST_ITEM.replace(
                    TEST_TAG_ARG_ROUTE_LIST_ITEM, "1"
                )
            ), true
        )
    }

    private fun routeDetailsComponent(): SemanticsNodeInteraction {
        return composeRule.onNode(hasTestTag(TEST_TAG_ROUTE_DETAILS), true)
    }

    private fun routeDetailsInfoComponent(): SemanticsNodeInteraction {
        return composeRule.onNode(hasTestTag(TEST_TAG_ROUTE_DETAILS_INFO), true)
    }
}