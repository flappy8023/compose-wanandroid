package com.flappy.wandroid.utils

import com.flappy.wandroid.data.api.ApiException
import com.flappy.wandroid.data.api.ApiResponse
import com.flappy.wandroid.data.api.ApiResult
import com.flappy.wandroid.data.api.ExceptionHandle


/**
 * @Author: luweiming
 * @Description:
 * @Date: Created in 17:58 2022/9/6
 */
suspend fun <T : Any> safeApiCall(call: suspend () -> ApiResponse<T>): Result<T?> {
    return Result.runCatching {
        val response = call.invoke()
        if (response.isSuccess) {
            return success(response.data)
        }
        return failure(ApiException(response.errorCode, response.errorMsg))
    }
}

suspend fun <T : Any> safeCall(call: suspend () -> ApiResponse<T>): ApiResult<T?> {
    return try {
        val response = call.invoke()
        if (response.isSuccess) {
            ApiResult.Success(response.data)
        } else {
            ApiResult.Failure(ApiException(response.errorCode, response.errorMsg))
        }
    } catch (e: Exception) {
        ApiResult.Failure(ExceptionHandle.handleException(e))
    }
}