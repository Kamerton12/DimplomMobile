package by.mrc.schedule.tools

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics


val Int.dp: Float
    get() {
        return this * Resources.getSystem().displayMetrics.density
    }
