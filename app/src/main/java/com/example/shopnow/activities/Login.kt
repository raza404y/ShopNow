package com.example.shopnow.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.myloadingbutton.MyLoadingButton
import com.example.shopnow.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class Login : AppCompatActivity(),MyLoadingButton.MyLoadingButtonClick {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginBtn.setMyButtonClickListener(this@Login)

        binding.gotoSignUpBtn.setOnClickListener {
            val intent = Intent(this@Login, SignUp::class.java)
            startActivity(intent)
        }

    }

    override fun onMyLoadingButtonClick() {
        val email = binding.emailEt.text.toString().trim()
        val password = binding.passwordEt.text.toString().trim()
        when{
            email.isEmpty() -> showToast("Enter Email")
            !isValidEmail(email) -> showToast("Enter valid email")
            password.isEmpty() -> showToast("Enter password")
            else -> signInUser(email,password)
        }

    }

    private fun signInUser(email: String, password: String) {
        binding.loginBtn.showLoadingButton()
        Firebase.auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { it ->
            if (it.isSuccessful){
                binding.loginBtn.showNormalButton()
                startActivity(Intent(this@Login,MainActivity::class.java))
                finish()
            }else{
                binding.loginBtn.showNormalButton()
                it.exception?.localizedMessage.let { showToast(it.toString()) }
            }
        }
    }

    private fun showToast(message: String){
        binding.loginBtn.showNormalButton()
        Toast.makeText(this@Login, message, Toast.LENGTH_SHORT).show()
    }
    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}