package by.mrc.schedule.teacher

import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.*
import java.io.IOException
import javax.inject.Inject

class TeacherRepository @Inject constructor(
    private val okHttpClient: OkHttpClient
){
    fun getTeacher(): Single<List<Teacher>> {
        return Single.create<List<Teacher>> { emitter ->
            val request = Request.Builder()
                .url("http://192.168.0.25/user_api/teachers")
                .get()
                .build()
            okHttpClient.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    emitter.onError(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseString = response.body?.string() ?: "[]"
                    val res = Gson().fromJson(responseString, Array<Teacher>::class.java).toList()
                    emitter.onSuccess(res)
                }
            })
        }
            .subscribeOn(Schedulers.io())
    }
}