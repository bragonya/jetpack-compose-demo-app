package com.bragonya.unsplashdemoapp.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bragonya.unsplashdemoapp.models.ImageRoot
import com.bragonya.unsplashdemoapp.models.RemoteKeys
import com.bragonya.unsplashdemoapp.network.UnsplashAPI
import com.bragonya.unsplashdemoapp.persistency.AppDatabase
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException
const val INITIAL_PAGE = 1
@OptIn(ExperimentalPagingApi::class)
class UnsplashRemoteMediator(
    val pageSize: Int,
    val dataBase: AppDatabase,
    val service: UnsplashAPI
): RemoteMediator<Int, ImageRoot>() {

    private val imagesDAO = dataBase.imagesDao()
    private val remoteKeysDAO = dataBase.remoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageRoot>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(true)
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                        ?: throw InvalidObjectException("Result is empty")
                    remoteKeys.nextKey ?: return MediatorResult.Success(true)
                }
            }

            val response = service.getImages(page, pageSize)

            val endOfPaginationReached = response.size < pageSize

            dataBase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    imagesDAO.clearAll()
                    remoteKeysDAO.clearRemoteKeys()
                }

                val prevKey = if (page == INITIAL_PAGE) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = response.map {
                    RemoteKeys(imageId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                remoteKeysDAO.insertAll(keys)
                imagesDAO.insertAll(response)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ImageRoot>): RemoteKeys? {
        return state.lastItemOrNull()?.let { news ->
            dataBase.withTransaction { remoteKeysDAO.remoteKeysByNewsId(news.id) }
        }
    }
    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ImageRoot>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                dataBase.withTransaction { remoteKeysDAO.remoteKeysByNewsId(id) }
            }
        }
    }
}