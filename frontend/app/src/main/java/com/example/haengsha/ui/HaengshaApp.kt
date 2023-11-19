package com.example.haengsha.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.haengsha.model.route.MainRoute
import com.example.haengsha.model.uiState.login.LoginApiUiState
import com.example.haengsha.model.viewModel.NavigationViewModel
import com.example.haengsha.model.viewModel.UserViewModel
import com.example.haengsha.model.viewModel.board.BoardApiViewModel
import com.example.haengsha.model.viewModel.board.BoardViewModel
import com.example.haengsha.model.viewModel.login.LoginApiViewModel
import com.example.haengsha.ui.screens.dashBoard.Board
import com.example.haengsha.ui.screens.favorite.Favorite
import com.example.haengsha.ui.screens.home.Home
import com.example.haengsha.ui.screens.login.Login
import com.example.haengsha.ui.uiComponents.ConfirmDialog
import com.example.haengsha.ui.uiComponents.HaengshaBottomAppBar
import com.example.haengsha.ui.uiComponents.HaengshaTopAppBar
import es.dmoral.toasty.Toasty

@Composable
fun HaengshaApp() {
    val userViewModel: UserViewModel = viewModel()
    val userUiState by userViewModel.uiState.collectAsState()
    val loginApiViewModel: LoginApiViewModel = viewModel(factory = LoginApiViewModel.Factory)
    val loginApiUiState = loginApiViewModel.loginApiUiState
    val boardViewModel: BoardViewModel = viewModel()
    val boardApiViewModel: BoardApiViewModel = viewModel(factory = BoardApiViewModel.Factory)
    val navigationViewModel: NavigationViewModel = viewModel()
    val navigationUiState by navigationViewModel.uiState.collectAsState()

    val mainNavController = rememberNavController()
    val currentScreenName = navigationUiState.screen
    val currentScreenType = navigationUiState.type
    val canNavigateBack = currentScreenName == "Details" || currentScreenName == "Write"

    var isLogoutClick by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            if (currentScreenName != "Login") {
                HaengshaTopAppBar(
                    currentScreen = currentScreenName,
                    canNavigateBack = canNavigateBack,
                    navigateBack = {
                        if (currentScreenType == "Board") mainNavController.navigate(MainRoute.Dashboard.route)
                        else mainNavController.navigate(MainRoute.Favorite.route)
                    },
                    logout = {
                        isLogoutClick = true
                    }
                )
            }
        },
        bottomBar = {
            if (currentScreenName != "Login") {
                HaengshaBottomAppBar(
                    navigateFavorite = { mainNavController.navigate(MainRoute.Favorite.route) },
                    navigateHome = { mainNavController.navigate(MainRoute.Home.route) },
                    navigateBoard = { mainNavController.navigate(MainRoute.Dashboard.route) }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = mainNavController,
            startDestination = MainRoute.Login.route
        ) {
            composable(MainRoute.Login.route) {
                navigationViewModel.updateRouteUiState("Main", MainRoute.Login.route)
                Login(
                    userViewModel = userViewModel,
                    loginApiViewModel = loginApiViewModel,
                    loginApiUiState = loginApiUiState,
                    boardViewModel = boardViewModel,
                    mainNavController = mainNavController
                )
            }
            composable(MainRoute.Home.route) {
                navigationViewModel.updateRouteUiState("Main", MainRoute.Home.route)
                Home(
                    innerPadding = innerPadding,
                    userUiState = userUiState,
                )
            }
            composable(MainRoute.Dashboard.route) {
                navigationViewModel.updateRouteUiState("Main", MainRoute.Dashboard.route)
                Board(
                    innerPadding = innerPadding,
                    userUiState = userUiState,
                    boardViewModel = boardViewModel,
                    boardApiViewModel = boardApiViewModel,
                    navigationViewModel = navigationViewModel
                )
            }
            composable(MainRoute.Favorite.route) {
                navigationViewModel.updateRouteUiState("Main", MainRoute.Favorite.route)
                Favorite(
                    innerPadding = innerPadding,
                    userUiState = userUiState,
                    boardApiViewModel = boardApiViewModel,
                    navigationViewModel = navigationViewModel
                )
            }
//        composable(MainRoute.Setting.route) {
//            Setting(
//                userViewModel = userViewModel,
//                userUiState = userUiState,
//                mainNavController = mainNavController
//            )
//        }
        }
        if (isLogoutClick) {
            ConfirmDialog(
                onDismissRequest = { isLogoutClick = false },
                onClick = {
                    loginApiViewModel.logout(userUiState.token)
                },
                text = "로그아웃 하시겠어요?"
            )
        }
        when (loginApiUiState) {
            is LoginApiUiState.Success -> {
                userViewModel.resetUserData()
                isLogoutClick = false
                mainNavController.navigate(MainRoute.Login.route) {
                    popUpTo(mainNavController.graph.id) { inclusive = true }
                }
                Toasty.success(context, "로그아웃 되었습니다.", Toasty.LENGTH_SHORT).show()
            }

            is LoginApiUiState.HttpError -> {
                Toasty.warning(context, loginApiUiState.message, Toasty.LENGTH_SHORT).show()
            }

            is LoginApiUiState.NetworkError -> {
                Toasty.error(context, "인터넷 연결을 확인해주세요.", Toasty.LENGTH_SHORT).show()
            }

            else -> { /*do nothing*/
            }
        }
    }
}
