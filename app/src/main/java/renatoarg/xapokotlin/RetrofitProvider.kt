package renatoarg.xapokotlin

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitProvider {

    private var retrofit: Retrofit? = null
    private const val BASE_URL = "https://api.github.com/"

        val retrofitInstance: Retrofit?

        get() {
            if(retrofit == null) {
                // creates an interceptor for logging
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                val client = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()

                // creates a retrofit instance for requests
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()

            }
        return retrofit
    }

}