package com.vuhungtran.a7minutesworkout

import android.app.Application

class WorkOutApp: Application() {
    val db: HistoryDatabase by lazy {
        HistoryDatabase.getIntance(this)
    }
}