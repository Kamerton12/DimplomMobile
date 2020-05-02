package by.mrc.schedule.schedule

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.*
import java.io.IOException
import java.util.*
import javax.inject.Inject

private val gson: Gson by lazy {
    val timestampDeserializer = JsonDeserializer { src, typeOfSrc, context ->
        Date(src.asLong)
    }

    GsonBuilder()
        .registerTypeAdapter(Date::class.java, timestampDeserializer)
        .create()
}

class ScheduleRepository @Inject constructor(
    private val okHttpClient: OkHttpClient
) {
    fun getSchedule(): Single<List<Schedule>> {
        return Single.create<List<Schedule>> { emitter ->
            val request = Request.Builder()
                .url("http://192.168.0.25/user_api/lessons")
                .get()
                .build()
            okHttpClient.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    emitter.onError(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseString = response.body?.string() ?: "[]"
                    val res = gson.fromJson(responseString, Array<Schedule>::class.java).toList()
                    emitter.onSuccess(res)
                }
            })
        }
            .subscribeOn(Schedulers.io())
    }
}