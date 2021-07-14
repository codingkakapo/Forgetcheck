package jp.codingkakapo.forgetcheck

import android.app.Application
import android.content.Context
import android.util.Log
import jp.codingkakapo.forgetcheck.model.ForgetCheckDB
import jp.codingkakapo.forgetcheck.utils.Const

class ForgetCheckApplication : Application() {

    val DB by lazy { ForgetCheckDB.getDB(this) }
}