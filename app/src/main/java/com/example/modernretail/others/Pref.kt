package com.example.modernretail.others

import com.marcinmoskala.kotlinpreferences.PreferenceHolder

object Pref: PreferenceHolder() {
    var LoginUserID: String by bindToPreferenceField("", "LoginUserID")
    var LoginPassword: String by bindToPreferenceField("", "LoginPassword")
    var user_id: String by bindToPreferenceField("", "user_id")
    var UserName: String by bindToPreferenceField("", "UserName")
    var UserContactNumber: String by bindToPreferenceField("", "UserContactNumber")

    var IsLogdedIn: Boolean by bindToPreferenceField(false, "IsLogdedIn")
    var IsLoginRemember: Boolean by bindToPreferenceField(false, "IsLoginRemember")
}