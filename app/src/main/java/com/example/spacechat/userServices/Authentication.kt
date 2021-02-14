package com.example.spacechat.userServices

import com.example.spacechat.model.UserModel

interface Authentication {

    fun createUser(user: UserModel)

    fun insertUserInFirestore(user: UserModel)

    fun login(user: UserModel)

    fun verifyData(user: UserModel): Boolean


}