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
    fun boardViewBoard_updateEventId_updateIsCorrect() {
        viewModel.updateEventId(1)
        assertEquals(viewModel.eventId.value, 1)
    }

    @Test
    fun boardViewBoard_updateInput_updateIsCorrect() {
        viewModel.updateInput("input")
        assertEquals(viewModel.input.value, "input")
    }

    @Test
    fun boardViewBoard_setIsSearchedTrue_isSearchedIsTrue() {
        viewModel.setIsSearchedTrue()
        assertTrue(viewModel.isSearched.value)
    }

    @Test
    fun boardViewBoard_setIsSearchedFalse_isSearchedIsFalse() {
        viewModel.setIsSearchedFalse()
        assertFalse(viewModel.isSearched.value)
    }

    @Test
    fun boardViewBoard_isError_errorIsTrue() {
        viewModel.isError()
        assertTrue(viewModel.isError)
    }

    @Test
    fun boardViewBoard_resetError_errorIsFalse() {
        viewModel.isError()
        viewModel.resetError()
        assertFalse(viewModel.isError)
    }

    @Test
    fun boardViewBoard_saveToken_saveIsCorrect() {
        viewModel.saveToken("token")
        assertEquals(viewModel.boardUiState.value.token, "Token token")
    }

    @Test
    fun boardViewBoard_updateKeyword_updateIsCorrect() {
        viewModel.updateKeyword("keyword")
        assertEquals(viewModel.boardUiState.value.keyword, "keyword")
    }

    @Test
    fun boardViewBoard_updateIsFestival_updateIsCorrect() {
        viewModel.updateIsFestival(1)
        assertEquals(viewModel.boardUiState.value.isFestival, 1)
    }

    @Test
    fun boardViewBoard_updateFilterStartDate_updateIsCorrect() {
        viewModel.updateFilterStartDate("2023-12-25")
        assertEquals(viewModel.boardUiState.value.startDate, "2023-12-25")
    }

    @Test
    fun boardViewBoard_updateFilterEndDate_updateIsCorrect() {
        viewModel.updateFilterEndDate("2023-12-31")
        assertEquals(viewModel.boardUiState.value.endDate, "2023-12-31")
    }

    @Test
    fun boardViewBoard_updateFilterInitialState_initialStateIsFalse() {
        viewModel.updateFilterInitialState()
        assertFalse(viewModel.boardUiState.value.initialState)
    }

    @Test
    fun boardViewBoard_resetFilterInitialState_initialStateIsTrue() {
        viewModel.updateFilterInitialState()
        viewModel.resetFilterInitialState()
        assertTrue(viewModel.boardUiState.value.initialState)
    }

    @Test
    fun boardViewBoard_resetBoardUiState_resetIsCorrect() {
        viewModel.updateKeyword("keyword")
        viewModel.resetBoardUiState()
        assertEquals(viewModel.boardUiState.value, BoardUiState())
    }

    @Test
    fun boardViewBoard_updatePostStartDate_updateIsCorrect() {
        viewModel.updatePostStartDate("2023-12-25")
        assertEquals(viewModel.boardPostUiState.value.startDate, "2023-12-25")
    }

    @Test
    fun boardViewBoard_updatePostEndDate_updateIsCorrect() {
        viewModel.updatePostEndDate("2023-12-31")
        assertEquals(viewModel.boardPostUiState.value.endDate, "2023-12-31")
    }

    @Test
    fun boardViewBoard_resetBoardPostUiState_updateIsCorrect() {
        viewModel.updatePostStartDate("2023-12-25")
        viewModel.resetBoardPostUiState()
        assertEquals(viewModel.boardPostUiState.value, BoardPostUiState())
    }
}