package by.mrc.schedule.schedule

import by.mrc.schedule.Settings
import by.mrc.schedule.schedule.db.ScheduleDao
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
    private val okHttpClient: OkHttpClient,
    private val scheduleDao: ScheduleDao
) {
    //true - cache
    //false - fetched
    fun getSchedule(groupName: String): Single<Pair<List<Schedule>, Boolean>> {
        return Single.create<List<Schedule>> { emitter ->
            val date = Calendar.getInstance()
            if (date.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                date.add(Calendar.DAY_OF_MONTH, -2)
            } else {
                date.add(Calendar.DAY_OF_MONTH, -1)
            }
            date.set(Calendar.HOUR_OF_DAY, 0)
            date.set(Calendar.MINUTE, 0)
            date.set(Calendar.SECOND, 0)
            date.set(Calendar.MILLISECOND, 0)
            val request = Request.Builder()
                .url("${Settings.URL}/user_api/lessons?from=${date.timeInMillis}")
                .get()
                .build()
            okHttpClient.newCall(request).enqueue(object : Callback {
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
            .map { list ->
                list.filter { it.group == groupName }
            }
            .doOnSuccess { scheduleDao.replaceAllSchedule(it) }
            .map { it to false }
            .onErrorResumeNext {
                loadFromCache()
                    .map { it to true }
            }
            .subscribeOn(Schedulers.io())
    }

    fun loadFromCache(): Single<List<Schedule>> {
        return Single.fromCallable { scheduleDao.getAllSchedule() }
            .subscribeOn(Schedulers.io())
    }

    fun getScheduleByIdFromCache(id: Int): Single<Schedule> {
        return Single.fromCallable { scheduleDao.getById(id) }
            .subscribeOn(Schedulers.io())
    }
}