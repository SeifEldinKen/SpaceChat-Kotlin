package com.example.spacechat.userServices

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.spacechat.model.UserModel
import com.example.spacechat.ui.activity.MainActivity
import com.example.spacechat.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Register(private val context: Context) {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun createUser(user: UserModel) {

        if (validateForm(user)) {

            firebaseAuth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    insertUserInFirestore(user)

                    Toast.makeText(context, "insert user", Toast.LENGTH_SHORT).show()

                    toMainActivity()

                } else {

                    Toast.makeText(context, task.exception!!.message, Toast.LENGTH_LONG).show()
                }

            }

        }

    }

    private fun insertUserInFirestore(user: UserModel) {
        firestore.collection(Constants.USERS).document(getCurrentUserId()).set(user).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                
            } else {
                Toast.makeText(context, task.exception!!.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateForm(user: UserModel): Boolean {
        return when {
            user.username.isEmpty() -> problemRegisteringNewUserFrom("Please enter username")

            user.email.isEmpty() -> problemRegisteringNewUserFrom("Please enter email")

            user.password.isEmpty() -> problemRegisteringNewUserFrom("Please enter password")

            else -> true
        }
    }

    private fun problemRegisteringNewUserFrom(problem: String): Boolean {
        Toast.makeText(
                context,
                problem,
                Toast.LENGTH_SHORT
        ).show()
        return false
    }

    private fun getCurrentUserId(): String {
        return firebaseAuth.currentUser!!.uid
    }

    private fun toMainActivity() {
        context.startActivity(Intent(context, MainActivity::class.java))
    }

}