package com.superhapp.fairusers.data.remote

import android.content.Context
import com.superhapp.fairusers.BuildConfig
import com.superhapp.fairusers.Utils
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient(private val context: Context){

    private var apiService: ApiService? = null

    fun build(): ApiService {
        if (apiService == null) {
            val cacheSize = (5 * 1024 * 1024).toLong()
            val myCache = Cache(context.cacheDir, cacheSize)

            val okHttpClient = OkHttpClient.Builder()
                .cache(myCache)
                .addInterceptor(makeOfflineInterceptor())
                .addNetworkInterceptor(makeOnlineInterceptor())
                .addInterceptor(makeLoggingInterceptor())
                .addInterceptor(makeApiInterceptor())
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            apiService = retrofit.create(ApiService::class.java)
        }

        return apiService as ApiService
    }

    private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return logging
    }

    private fun makeApiInterceptor(): Interceptor {
        return Interceptor {
            val request = it.request().newBuilder()
                .header("app-id", APP_ID)
                .build()
            it.proceed(request)
        }
    }

    private fun makeOfflineInterceptor() : Interceptor {
        return Interceptor {
            var request: Request = it.request()
            if (!Utils.isInternetAvailable(context)) {
                val maxStale = 60 * 60 * 24 * 30 // Offline cache available for 30 days
                request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .removeHeader("Pragma")
                    .build()
            }
            it.proceed(request)
        }
    }

    private fun makeOnlineInterceptor() : Interceptor {
        return Interceptor {
            var request: Request = it.request()
            val maxAge = 60 // read from cache for 60 seconds even if there is internet connection
            request = request.newBuilder()
                .header("Cache-Control", "public, max-age=$maxAge")
                .removeHeader("Pragma")
                .build()
            it.proceed(request)
        }
    }

    companion object {
        private const val BASE_URL = "https://dummyapi.io/data/api/"
        private const val APP_ID = "6104543c1dc6e68fe34f31d4"
    }
}

