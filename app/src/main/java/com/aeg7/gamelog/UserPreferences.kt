package com.aeg7.gamelog

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserPreferences(
    var ownership: String = "false",
    var physicalCopy: String = "false",
    var digitalCopy: String = "false",
    var collectors: String = "false",
    var extra: String = "false",
    var date: String = "",
    var status: String ="",
    var mark: String = "0",
    var console: String = "",
    var comments: String = "") : Parcelable {
}