package by.mrc.schedule.schedule.groups

import by.mrc.schedule.Settings
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.*
import java.io.IOException
import javax.inject.Inject

class GroupRepository @Inject constructor(
    private val okHttpClient: OkHttpClient
) {
    fun getGroups(): Single<List<String>> {
        return Single.create<List<String>> { emitter ->
            val request = Request.Builder()
                .url("${Settings.URL}/user_api/groups")
                .get()
                .build()
            okHttpClient.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    emitter.onError(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseString = response.body?.string() ?: "[]"
                    val res = Gson().fromJson(responseString, Array<String>::class.java).toList()
                    emitter.onSuccess(res)
                }
            })
        }
            .subscribeOn(Schedulers.io())
    }
}