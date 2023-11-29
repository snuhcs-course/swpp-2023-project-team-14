package com.example.haengsha.instrumentedTest.navigationTest

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.haengsha.model.route.FavoriteRoute
import com.example.haengsha.model.viewModel.NavigationViewModel
import com.example.haengsha.model.viewModel.UserViewModel
import com.example.haengsha.model.viewModel.board.BoardApiViewModel
import com.example.haengsha.testConfig.assertCurrentRouteName
import com.example.haengsha.ui.screens.favorite.Favorite
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavoriteScreenNavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var favoriteNavController: TestNavHostController
    private lateinit var userViewModel: UserViewModel
    private lateinit var boardApiViewModel: BoardApiViewModel
    private lateinit var navigationViewModel: NavigationViewModel

    @Before
    fun setupMainNavHost() {
        composeTestRule.setContent {
            favoriteNavController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            userViewModel = UserViewModel()
            val userUiState by userViewModel.uiState.collectAsState()
            boardApiViewModel = viewModel(factory = BoardApiViewModel.Factory)
            navigationViewModel = viewModel()

            Favorite(
                innerPadding = PaddingValues(0.dp),
                userUiState = userUiState,
                boardApiViewModel = boardApiViewModel,
                navigationViewModel = navigationViewModel,
                favoriteNavController = favoriteNavController,
                isTest = true
            )
        }
    }

    @Test
    fun favoriteNavController_verifyStartDestination() {
        favoriteNavController.assertCurrentRouteName(FavoriteRoute.FavoriteBoard.route)
    }

    @Test
    fun favoriteNavController_clickFavoriteEvent_navigateToFavoriteDetailScreen() {
        composeTestRule.onNodeWithText("test").performClick()
        favoriteNavController.assertCurrentRouteName(FavoriteRoute.FavoriteDetail.route)
    }

    // NavigateUp 테스트는 코드 구조상 MainScreen에서 테스트함
}