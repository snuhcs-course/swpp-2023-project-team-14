package com.example.haengsha.instrumentedTest.navigationTest

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.haengsha.model.route.BoardRoute
import com.example.haengsha.model.viewModel.NavigationViewModel
import com.example.haengsha.model.viewModel.UserViewModel
import com.example.haengsha.model.viewModel.board.BoardApiViewModel
import com.example.haengsha.model.viewModel.board.BoardViewModel
import com.example.haengsha.testConfig.assertCurrentRouteName
import com.example.haengsha.ui.screens.board.Board
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BoardScreenNavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var boardNavController: TestNavHostController
    private lateinit var userViewModel: UserViewModel
    private lateinit var boardViewModel: BoardViewModel
    private lateinit var boardApiViewModel: BoardApiViewModel
    private lateinit var navigationViewModel: NavigationViewModel

    @Before
    fun setupMainNavHost() {
        composeTestRule.setContent {
            boardNavController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            userViewModel = UserViewModel()
            val userUiState by userViewModel.uiState.collectAsState()
            boardViewModel = viewModel()
            boardApiViewModel = viewModel(factory = BoardApiViewModel.Factory)
            navigationViewModel = viewModel()

            Board(
                innerPadding = PaddingValues(0.dp),
                userUiState = userUiState,
                boardViewModel = boardViewModel,
                boardApiViewModel = boardApiViewModel,
                navigationViewModel = navigationViewModel,
                boardNavController = boardNavController,
                isTest = true
            )
        }
    }

    @Test
    fun boardNavController_verifyStartDestination() {
        boardNavController.assertCurrentRouteName(BoardRoute.Board.route)
    }

    @Test
    fun boardNavController_clickEvent_navigateToDetailScreen() {
        composeTestRule.onNodeWithText("test").performClick()
        boardNavController.assertCurrentRouteName(BoardRoute.BoardDetail.route)
    }

    @Test
    fun boardNavController_clickPost_navigateToPostScreen() {
        composeTestRule.onNodeWithContentDescription("event post Button").performClick()
        boardNavController.assertCurrentRouteName(BoardRoute.BoardPost.route)
    }
}