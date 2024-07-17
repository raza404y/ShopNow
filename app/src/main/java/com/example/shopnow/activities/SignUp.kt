package com.example.shopnow.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.myloadingbutton.MyLoadingButton
import com.example.shopnow.Constants
import com.example.shopnow.models.Users
import com.example.shopnow.databinding.ActivitySignupBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class SignUp : AppCompatActivity(),MyLoadingButton.MyLoadingButtonClick {
    private val binding: ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.signupBtn.setMyButtonClickListener(this@SignUp)

        binding.gotoSignInBtn.setOnClickListener {
            startActivity(Intent(this@SignUp, Login::class.java))
        }

    }

    override fun onMyLoadingButtonClick() {
        val firstName = binding.firstName.text.toString().trim()
        val lastName = binding.lastName.text.toString().trim()
        val email = binding.emailEt.text.toString().trim()
        val password = binding.createPasswordEt.text.toString().trim()
        val confirmPassword = binding.confirmPasswordEt.text.toString().trim()

        when {
            firstName.isEmpty() -> showToast("Enter First Name")
            firstName.length > 20 -> showToast("first Name too long")
            firstName.length < 3 -> showToast("Name is too short")
            lastName.isEmpty() -> showToast("Enter last Name")
            lastName.length > 20 -> showToast("last Name too long")
            lastName.length < 3 -> showToast("last Name is too short")
            email.isEmpty() -> showToast("Enter Email")
            !isValidEmail(email) -> showToast("Email is not valid")
            password.isEmpty() -> showToast("Create Password")
            confirmPassword.isEmpty() -> showToast("Enter confirm password")
            password != confirmPassword -> showToast("Password not matching")
            else -> createUser(firstName,lastName,email,password)
        }
    }

    private fun createUser(firstName: String, lastName: String, email: String, password: String) {
        binding.signupBtn.showLoadingButton()
        Firebase.auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{ task ->
            if (task.isSuccessful){
                val users = Users(firstName, lastName, email, password)
                Firebase.firestore.collection(Constants.users).document(task.result.user!!.uid).set(users).addOnCompleteListener{task ->
                    if (task.isSuccessful){
                       startActivity(Intent(this@SignUp,Login::class.java))
                        finish()
                        showToast("Account created")
                    }
                    else{
                        binding.signupBtn.showNormalButton()
                        task.exception?.localizedMessage?.let { showToast(it) }
                    }
                }
            }else{
                binding.signupBtn.showNormalButton()
                task.exception?.localizedMessage.let { showToast(it.toString()) }
            }
        }

    }

    private fun showToast(message: String) {
        binding.signupBtn.showNormalButton()
        Toast.makeText(this@SignUp, message, Toast.LENGTH_SHORT).show()
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


}