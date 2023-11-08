package com.example.haengsha.model.dataSource

import com.example.haengsha.model.network.apiService.BoardApiService
import com.example.haengsha.model.network.dataModel.BoardDetailResponse
import com.example.haengsha.model.network.dataModel.BoardListResponse

interface BoardDataRepository {
    suspend fun getBoardList(startDate: String): List<BoardListResponse>
    suspend fun getBoardDetail(token: String, postId: Int): BoardDetailResponse
}

class NetworkBoardDataRepository(
    private val boardApiService: BoardApiService
) : BoardDataRepository {
    override suspend fun getBoardList(startDate: String): List<BoardListResponse> {
        return boardApiService.getBoardList(startDate)
    }

    override suspend fun getBoardDetail(token: String, postId: Int): BoardDetailResponse {
        return boardApiService.getBoardDetail(token, postId)
    }
}