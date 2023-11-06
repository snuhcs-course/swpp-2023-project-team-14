package com.example.haengsha.model.dataSource

import com.example.haengsha.model.network.apiService.BoardApiService
import com.example.haengsha.model.network.dataModel.BoardResponse

interface BoardDataRepository {
    suspend fun getBoardList(token: String, startDate: String): BoardResponse
    suspend fun getBoardDetail(postId: Int): BoardResponse
}

class NetworkBoardDataRepository(
    private val boardApiService: BoardApiService
) : BoardDataRepository {
    override suspend fun getBoardList(token: String, startDate: String): BoardResponse {
        return boardApiService.getBoardList(token, startDate)
    }

    override suspend fun getBoardDetail(postId: Int): BoardResponse {
        return boardApiService.getBoardDetail(postId)
    }
}