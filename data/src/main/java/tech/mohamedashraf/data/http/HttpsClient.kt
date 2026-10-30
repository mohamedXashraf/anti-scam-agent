package tech.mohamedashraf.data.http

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.mohamedashraf.data.BuildConfig
import java.util.concurrent.TimeUnit

class HttpsClient {
    companion object {
        fun create(): Retrofit {
            val loggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

            val headerInterceptor = Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("Authorization", "Bearer ${BuildConfig.MUSE_API_KEY}")
                    .header("Accept", "application/json").build()
                chain.proceed(request)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://api.meta.ai/v1/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}