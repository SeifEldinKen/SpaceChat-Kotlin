package com.example.spacechat.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.spacechat.R
import com.example.spacechat.databinding.ActivityRegisterBinding
import com.example.spacechat.model.UserModel
import com.example.spacechat.userServices.Register

class RegisterActivity : AppCompatActivity() {

    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val register: Register by lazy {
        Register(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSignUp!!.setOnClickListener {
            createNewUser()
        }


    }

    private fun createNewUser() {
        register.createUser(getDataFromUI())
    }

    private fun getDataFromUI(): UserModel {
        return UserModel().apply {
            username = binding.editTextUsername!!.text.toString().trim()
            email = binding.editTextEmail!!.text.toString().trim()
            password = binding.editTextPassword!!.text.toString().trim()
        }
    }
}