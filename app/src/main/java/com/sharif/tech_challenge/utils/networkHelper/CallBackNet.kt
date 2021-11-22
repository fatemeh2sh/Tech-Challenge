package com.sharif.tech_challenge.utils.networkHelper

import com.sharif.tech_challenge.data.model.CheckErrorApiClass
import retrofit2.Response
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

object CallBackNet {
    suspend fun <T> apiCall(call: suspend () -> Response<T>): ResultNet<T> {
        val response: Response<T>
        try {
            response = call.invoke()
        }catch (t:UnknownError){
            return ResultNet.ErrorNetwork(HandleErrorNet.mapNetworkThrowable(t))
        } catch (t:UnknownHostException){
            return ResultNet.ErrorNetwork(HandleErrorNet.mapNetworkThrowable(t))
        } catch (t:TimeoutException){
            return ResultNet.ErrorNetwork(HandleErrorNet.mapNetworkThrowable(t))
        } catch (t: Throwable) {
            return ResultNet.ErrorException(t.message)
        }

        return if (!response.isSuccessful) {
            val errorBody = response.errorBody()

            ResultNet.ErrorApi(
                HandleErrorNet.parseCustomError(
                    errorBodyJson = errorBody?.string() ?: "")
            )
        } else {
            return if (response.body() == null) {
                ResultNet.ErrorApi(
                    CheckErrorApiClass(
                        status = -1,
                        message_data = HandleErrorNet.nullBodyException(),
                        data = null
                    )
                )
            } else {
                ResultNet.Success(response.body()!!)
            }
        }
    }
}