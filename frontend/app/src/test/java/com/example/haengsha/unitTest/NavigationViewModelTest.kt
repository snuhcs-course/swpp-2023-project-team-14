package com.example.haengsha.unitTest

import com.example.haengsha.model.viewModel.NavigationViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Test

class NavigationViewModelTest {
    private val viewModel = NavigationViewModel()

    @Test
    fun navigationViewModel_updateRouteUiState_updateIsCorrect() {
        viewModel.updateRouteUiState("type", "screen")
        assertEquals(viewModel.uiState.value.type, "type")
        assertEquals(viewModel.uiState.value.screen, "screen")
    }
}