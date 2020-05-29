package by.mrc.schedule.teacher

import by.mrc.schedule.Settings
import by.mrc.schedule.teacher.db.TeacherDao
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.*
import java.io.IOException
import javax.inject.Inject

class TeacherRepository @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val teacherDao: TeacherDao
) {
    fun getTeachers(): Single<Pair<List<Teacher>, Boolean>> {
        return Single.create<List<Teacher>> { emitter ->
            val request = Request.Builder()
                .url("${Settings.URL}/user_api/teachers")
                .get()
                .build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    emitter.onError(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseString = response.body?.string() ?: "[]"
                    val res =
                        Gson().fromJson(responseString, Array<Teacher>::class.java).toList()
                    emitter.onSuccess(res)
                }
            })
        }
            .doOnSuccess {
                teacherDao.replaceAllTeachers(it)
            }
            .map { it to false }
            .onErrorResumeNext {
                getTeachersFromCache()
                    .map { it to true }
            }
            .subscribeOn(Schedulers.io())
    }

    fun getTeachersFromCache(): Single<List<Teacher>> {
        return Single.fromCallable { teacherDao.getAllTeachers() }
            .subscribeOn(Schedulers.io())
    }
}