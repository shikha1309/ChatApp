package com.shikha.chatapp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.shikha.chatapp.ModelClasses.Users
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_message_chat.*

class MessageChatActivity : AppCompatActivity() {
    var useridVisit:String=" "
    var firebaseUser :FirebaseUser ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_chat)
        intent =intent
        useridVisit= intent.getStringExtra("visit id").toString()  //Receiver id by using the intent
        firebaseUser=FirebaseAuth.getInstance().currentUser  // Sender Id

         // Retrieving the User name  and Image
        val reference= FirebaseDatabase.getInstance().reference
            .child("Users").child(useridVisit)
         reference.addValueEventListener(object: ValueEventListener{
             override fun onDataChange(p0: DataSnapshot) {
                 val  user : Users?= p0.getValue(Users::class.java)
                 username_messageChat.text= user!!.getUserName()
                 Picasso.get().load(user.getProfile()).into(profile_image_message_chat)

             }

             override fun onCancelled(p0: DatabaseError) {

             }

         })



        send_message_btn.setOnClickListener {
            val message = text_message.text.toString()
            if (message == "") {
                Toast.makeText(
                    this@MessageChatActivity,
                    " Please write a message, first... ",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                sendMessageToUser( firebaseUser!!.uid, useridVisit ,message)

            }
          }
        attach_image_file.setOnClickListener {
            val intent = Intent()
            intent.action =Intent.ACTION_GET_CONTENT
            intent.type ="image/*"
            startActivityForResult( Intent.createChooser( intent,"Pick Image") ,403)


        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun sendMessageToUser(senderId: String, receiverId: String, message: String)
    {
        val reference  = FirebaseDatabase.getInstance().reference
        val messageKey = reference.push().key

       // All these are the sub child for storing the messages
        val messageHashMap=HashMap<String,Any?>()
        messageHashMap["sender"]= senderId
        messageHashMap["message"]= message
        messageHashMap["receiver"]= receiverId
        messageHashMap["isseen"]= false
        messageHashMap["url"]= ""
        messageHashMap["messageId"]= messageKey
        reference.child("Chats")
            .child(messageKey!!)
            .setValue(messageHashMap)
            .addOnCompleteListener { task->  // if the task is successful
                if (task.isSuccessful)
                {
                 val chatsListReference = FirebaseDatabase.getInstance()
                     .reference
                     .child("ChatList  ")

                    chatsListReference.child("id").setValue(firebaseUser!!.uid)
                    
                    val reference = FirebaseDatabase.getInstance().reference
                        .child("Users").child(firebaseUser!!.uid )

                    // Implementing the Push Notifications
                    
                }

            }





    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==438 &&  resultCode== Activity.RESULT_OK  && data !! .data!=null )
        {
            val loadingBar =ProgressDialog(applicationContext)
            loadingBar.setMessage("Image is Sending....")
            loadingBar.show()

            val fileUri=data.data
            val storageReference=FirebaseStorage.getInstance().reference.child("Chat Images")
            val ref =FirebaseDatabase.getInstance().reference
            val messageId =ref.push().key
            val filePath = storageReference.child("$messageId.jpg")


























        }

    }
}