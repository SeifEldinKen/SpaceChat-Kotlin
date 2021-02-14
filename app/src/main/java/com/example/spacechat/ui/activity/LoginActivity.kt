package com.example.spacechat.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.spacechat.databinding.ActivityLoginBinding
import com.example.spacechat.model.UserModel
import com.example.spacechat.userServices.AuthWithEmailAndPassword

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val auth: AuthWithEmailAndPassword by lazy {
        AuthWithEmailAndPassword(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLogin!!.setOnClickListener {
            login()
        }

    }

    private fun login() {
        auth.login(getDataFromUI())
    }

    private fun getDataFromUI(): UserModel {
        return UserModel().apply {
            email = binding.editTextEmail!!.text.toString().trim()
            password = binding.editTextPassword!!.text.toString().trim()
        }
    }



}