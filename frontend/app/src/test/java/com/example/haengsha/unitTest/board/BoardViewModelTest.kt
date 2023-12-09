package com.example.haengsha.unitTest.board

import com.example.haengsha.model.uiState.board.BoardPostUiState
import com.example.haengsha.model.uiState.board.BoardUiState
import com.example.haengsha.model.viewModel.board.BoardViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class BoardViewModelTest {
    private val viewModel = BoardViewModel()

    @Test
    fun boardViewModel_updateEventId_updateIsCorrect() {
        viewModel.updateEventId(1)
        assertEquals(viewModel.eventId.value, 1)
    }

    @Test
    fun boardViewModel_updateInput_updateIsCorrect() {
        viewModel.updateInput("input")
        assertEquals(viewModel.input.value, "input")
    }

    @Test
    fun boardViewModel_setIsSearchedTrue_isSearchedIsTrue() {
        viewModel.setIsSearchedTrue()
        assertTrue(viewModel.isSearched.value)
    }

    @Test
    fun boardViewModel_setIsSearchedFalse_isSearchedIsFalse() {
        viewModel.setIsSearchedFalse()
        assertFalse(viewModel.isSearched.value)
    }

    @Test
    fun boardViewModel_isError_errorIsTrue() {
        viewModel.isError()
        assertTrue(viewModel.isError)
    }

    @Test
    fun boardViewModel_resetError_errorIsFalse() {
        viewModel.isError()
        viewModel.resetError()
        assertFalse(viewModel.isError)
    }

    @Test
    fun boardViewModel_saveToken_saveIsCorrect() {
        viewModel.saveToken("token")
        assertEquals(viewModel.boardUiState.value.token, "Token token")
    }

    @Test
    fun boardViewModel_updateKeyword_updateIsCorrect() {
        viewModel.updateKeyword("keyword")
        assertEquals(viewModel.boardUiState.value.keyword, "keyword")
    }

    @Test
    fun boardViewModel_updateIsFestival_updateIsCorrect() {
        viewModel.updateIsFestival(1)
        assertEquals(viewModel.boardUiState.value.isFestival, 1)
    }

    @Test
    fun boardViewModel_updateFilterStartDate_updateIsCorrect() {
        viewModel.updateFilterStartDate("2023-12-25")
        assertEquals(viewModel.boardUiState.value.startDate, "2023-12-25")
    }

    @Test
    fun boardViewModel_updateFilterEndDate_updateIsCorrect() {
        viewModel.updateFilterEndDate("2023-12-31")
        assertEquals("2023-12-31", viewModel.boardUiState.value.endDate)
    }

    @Test
    fun boardViewModel_savePreviousFilter_cancelFilter_logicIsCorrect() {
        // Test only for savePreviousFilter is private test, so not test independently
        viewModel.updateIsFestival(1)
        viewModel.updateFilterStartDate("2023-12-25")
        viewModel.savePreviousFilter()
        viewModel.updateKeyword("foo")
        viewModel.updateFilterStartDate("2022-01-01")
        viewModel.updateFilterEndDate("2023-12-31")
        viewModel.cancelFilter()
        assertEquals(
            BoardUiState(startDate = "2023-12-25", isFestival = 1),
            viewModel.boardUiState.value
        )
    }

    @Test
    fun boardViewModel_resetFilter_resetIsCorrect() {
        viewModel.saveToken("token")
        viewModel.updateKeyword("keyword")
        viewModel.updateIsFestival(1)
        viewModel.updateFilterStartDate("2023-12-25")
        viewModel.updateFilterEndDate("2023-12-31")
        viewModel.resetFilter()
        assertEquals(
            BoardUiState(
                token = "Token token",
                keyword = "keyword",
                isFestival = 2,
                startDate = "",
                endDate = "",
                initialState = false
            ),
            viewModel.boardUiState.value
        )
    }

    @Test
    fun boardViewModel_resetBoardUiState_resetIsCorrect() {
        viewModel.updateKeyword("keyword")
        viewModel.resetBoardUiState()
        assertEquals(viewModel.boardUiState.value, BoardUiState())
    }

    @Test
    fun boardViewModel_updatePostStartDate_updateIsCorrect() {
        viewModel.updatePostStartDate("2023-12-25")
        assertEquals(viewModel.boardPostUiState.value.startDate, "2023-12-25")
    }

    @Test
    fun boardViewModel_updatePostEndDate_updateIsCorrect() {
        viewModel.updatePostEndDate("2023-12-31")
        assertEquals(viewModel.boardPostUiState.value.endDate, "2023-12-31")
    }

    @Test
    fun boardViewModel_resetBoardPostUiState_updateIsCorrect() {
        viewModel.updatePostStartDate("2023-12-25")
        viewModel.resetBoardPostUiState()
        assertEquals(viewModel.boardPostUiState.value, BoardPostUiState())
    }
}