package me.davidcosta.tmdb.data.api

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import me.davidcosta.tmdb.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*


object ApiService {

    val instance: Api by lazy {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val deserializer: JsonDeserializer<Date?> =
            JsonDeserializer<Date?> { json, typeOfT, context ->
                val strDate = json.toString().replace("\"", "")
                val formatter = SimpleDateFormat("yyyy-MM-dd")
                val date = formatter.parse(strDate)
                date
            }

        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(Date::class.java, deserializer)
        val gson = gsonBuilder.create()

//        val gson = GsonBuilder().setLenient().create()


        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.TMDB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))

            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(httpClient.build())
            .build()

        retrofit.create(Api::class.java)
    }


}