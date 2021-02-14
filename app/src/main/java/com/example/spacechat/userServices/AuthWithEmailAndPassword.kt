package com.example.spacechat.userServices

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.spacechat.model.UserModel
import com.example.spacechat.ui.activity.MainActivity
import com.example.spacechat.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthWithEmailAndPassword(private val context: Context): Authentication {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }


    override fun createUser(user: UserModel) {
        if (verifyData(user)) {

            firebaseAuth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    insertUserInFirestore(user)
                    toMainActivity()

                } else {
                    Toast.makeText(context, task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                }
            }

        }
    }

    override fun insertUserInFirestore(user: UserModel) {

        user.userId = firebaseAuth.currentUser!!.uid

        firestore.collection(Constants.USERS).document(firebaseAuth.currentUser!!.uid).set(user).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                Toast.makeText(context, "you have been added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, task.exception!!.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun login(user: UserModel) {
        if (verifyData(user)) {
            firebaseAuth.signInWithEmailAndPassword(user.email, user.password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    toMainActivity()
                } else {
                    Toast.makeText(context, task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun verifyData(user: UserModel): Boolean {
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

    private fun toMainActivity() {
        context.startActivity(Intent(context, MainActivity::class.java))
    }
}