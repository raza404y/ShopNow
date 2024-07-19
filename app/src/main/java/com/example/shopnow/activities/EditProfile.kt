package com.example.shopnow.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.myloadingbutton.MyLoadingButton
import com.example.shopnow.Constants
import com.example.shopnow.R
import com.example.shopnow.databinding.ActivityEditProfileBinding
import com.example.shopnow.fragments.Profile
import com.example.shopnow.models.UserProfile
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class EditProfile : AppCompatActivity(), MyLoadingButton.MyLoadingButtonClick {
    lateinit var binding: ActivityEditProfileBinding
    var mProfileUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SaveBtn.setMyButtonClickListener(this@EditProfile)


        binding.profileImg.setOnClickListener {
            ImagePicker.with(this)
                .compress(400)   //Final image size will be less than 1 MB(Optional)
                .crop()
                .maxResultSize(
                    1080,
                    1080
                )  //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }

        Firebase.database.reference.child(Constants.USERS_INFO).child(Firebase.auth.currentUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        val userinfo = snapshot.getValue(UserProfile::class.java)
                        Glide.with(this@EditProfile)
                            .load(userinfo?.profileImg)
                            .into(binding.profileImg)

                        userinfo?.apply {
                            binding.nameEt.text = name?.toEditable()
                            binding.emailEt.text = email?.toEditable()
                            binding.phoneEt.text = phone?.toEditable()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

    }
    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)


    override fun onMyLoadingButtonClick() {
        binding.apply {
            val name = nameEt.text.toString().trim()
            val email = emailEt.text.toString().trim()
            val phone = phoneEt.text.toString().trim()
            when {
                mProfileUri == null -> showToast("Select profile image")
                name.isEmpty() -> showToast("Enter name")
                email.isEmpty() -> showToast("Enter mail")
                phone.isEmpty() || phone.length < 5 -> showToast("Phone field wrong")
                else -> saveProfileDetails(name, email, phone, mProfileUri!!)
            }
        }
    }

    private fun saveProfileDetails(name: String, email: String, phone: String, profileUri: Uri) {
        binding.SaveBtn.showLoadingButton()
        Firebase.storage.reference.child(Constants.USERS_INFO)
            .child(Firebase.auth.currentUser!!.uid)
            .putFile(profileUri).addOnCompleteListener {
                if (it.isSuccessful) {
                    it.result.storage.downloadUrl.addOnSuccessListener {
                        val url = it.toString()
                        val userInfo = UserProfile(name, email, phone, url)
                        Firebase.database.reference.child(Constants.USERS_INFO)
                            .child(Firebase.auth.currentUser!!.uid)
                            .setValue(userInfo).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    showToast("Profile Saved")
                                    binding.apply {
                                        nameEt.setText("")
                                        emailEt.setText("")
                                        phoneEt.setText("")
                                    }
                                } else {
                                    it.exception!!.localizedMessage?.let { it1 -> showToast(it1) }
                                    binding.SaveBtn.showNormalButton()
                                }
                            }
                    }
                }
            }
    }

    fun showToast(message: String) {
        binding.SaveBtn.showNormalButton()
        Toast.makeText(this@EditProfile, message, Toast.LENGTH_SHORT).show()
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!
                mProfileUri = fileUri
                binding.profileImg.setImageURI(mProfileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
}
