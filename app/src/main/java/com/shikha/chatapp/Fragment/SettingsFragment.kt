package com.shikha.chatapp.Fragment

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.shikha.chatapp.ModelClasses.Users
import com.shikha.chatapp.R
import com.shikha.chatapp.databinding.FragmentSettingsBinding

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_settings.view.*


class SettingsFragment : Fragment() {


    private lateinit var binding :FragmentSettingsBinding
    private var usersReference: DatabaseReference? = null
    var firebaseUser: FirebaseUser? = null
    private val RequestCode = 438
    private var imageUri: Uri? = null
    private var storageRef: StorageReference? = null   // need a reference for firebase storage  where I put all the Images.
    private var coverChecker: String? =  "" // for user is going to upload profile Image or Cover Image.


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
            binding = FragmentSettingsBinding.inflate(inflater, container, false)
           val view =binding.root




        firebaseUser = FirebaseAuth.getInstance().currentUser
        usersReference = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        storageRef = FirebaseStorage.getInstance().reference.child("User Images")

        usersReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val user: Users? = p0.getValue(Users::class.java)

                    if (context != null) {
                        view.username_settings.text = user!!.getUserName()
                        Picasso.get().load(user.getProfile()).into(view.profile_Image_settings)
                        Picasso.get().load(user.getCover()) .placeholder(R.drawable.cover).into(view.cover_image)
                    }
                }

            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })


    view.profile_Image_settings.setOnClickListener {
            setImage( )
        }

        view.cover_image.setOnClickListener {
            coverChecker = "cover"
            setImage()
        }
        return view
    }

    private fun setImage() {
        //  we open mobile phone Gallery and  choose an Image // Fn or this we used a intent
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_PICK
        startActivityForResult(intent, RequestCode)  //  opening for upload
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RequestCode && resultCode == Activity.RESULT_OK && data!!.data  != null) {
            // data .data will pass an image in ImageUri
            imageUri = data.data
            Toast.makeText(context, "uploading......", Toast.LENGTH_LONG).show()
            // this function will upload Image in the firebase Storage
            uploadImageToDatabase()


        }
    }

    private fun uploadImageToDatabase() {
        val progressBar = ProgressDialog(context)   // will show  while image is uploading
        progressBar.setMessage("Image is Uploading , please wait")
        progressBar.show()

        if (imageUri != null) {
            // at time of uploading we will create a unique Key
            // for this use system.current time


            val fileRef = storageRef!!.child(System.currentTimeMillis().toString() + ".jpg")
            var uploadTask: StorageTask<*>
            uploadTask = fileRef.putFile(imageUri!!)
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful)   {
                    task.exception?.let {
                        throw it
                    }
                }

                return@Continuation fileRef.downloadUrl
            }) .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUrl = task.result
                    val url = downloadUrl.toString()

                    // if cover checker is equal to cover then upload cover image else profile image
                    if(coverChecker =="cover")
                    {
                      val mapCoverImg = HashMap<String,Any>()
                        mapCoverImg["cover"] =url
                        usersReference!!.updateChildren(mapCoverImg)
                        coverChecker = ""


                    }
                    else
                    {
                        val mapProfileImg = HashMap<String,Any>()
                        mapProfileImg["profile"] =url
                        usersReference!!.updateChildren(mapProfileImg)
                        coverChecker = ""

                    }
                    progressBar.dismiss()
                }
            }
        }
    }
}