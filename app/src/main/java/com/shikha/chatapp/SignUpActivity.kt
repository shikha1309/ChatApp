package com.shikha.chatapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar


import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {


    private lateinit var toolbar:Toolbar
    private lateinit var mAuth:FirebaseAuth
    private lateinit var refUsers:DatabaseReference
    private var firebaseUserID: String=""
     lateinit var userName:EditText
    private lateinit var edtEmail:EditText
    private  lateinit var edtPassword:EditText
    private lateinit var edtConfirmPassword:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        FirebaseApp.initializeApp(this)
          mAuth= FirebaseAuth.getInstance()       // Just connecting the firebase   // Initializing the firebase
          userName = findViewById(R.id.userNameSignUp)
          edtEmail=findViewById(R.id.edtEmail)
          edtPassword=findViewById(R.id.edtPassword)
         edtConfirmPassword= findViewById(R.id.edtConfirmPassword)
        val btnSignup: Button= findViewById(R.id.btnSignUp)
        toolbar=findViewById(R.id.toolbarSignup)
        setUpToolbar()


        btnSignup.setOnClickListener {

            signUp()              // Calling my Function
            val intent = Intent(this@SignUpActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

      private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = " SIGN IN  "
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener{
            val intent = Intent (this@SignUpActivity , LoginActivity:: class.java)
            startActivity(intent)
            finish()
        }
    }


    // Creating my function for signUp  User
    private fun signUp(){

        val username: String= userName.text.toString()
        val email: String= edtEmail.text.toString()
        val password: String= edtPassword.text.toString()
        val confirmPassword: String= edtConfirmPassword.text.toString()

        // if anything is empty then this toast massage will appear
        if(username =="")
        {
         Toast.makeText(this@SignUpActivity, " Please Write Your User Name", Toast.LENGTH_SHORT).show(            )
        }
        else if (email=="")
        {
            Toast.makeText(this@SignUpActivity, " Please Write Your Email Name", Toast.LENGTH_SHORT).show()
        }
        else if (password=="")
        {
            Toast.makeText(this@SignUpActivity, " Please Enter Your  Password", Toast.LENGTH_SHORT).show()
        }
        else if (confirmPassword=="")
        {
            Toast.makeText(this@SignUpActivity, " Please  Confirm Your Password", Toast.LENGTH_SHORT).show()
        }
        // if all ok the save the data inside the firebase database
        else
        {


            // logic for creating user
        mAuth  .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                   firebaseUserID=mAuth.currentUser!!.uid
                    refUsers=FirebaseDatabase.getInstance("https://chatapp-c6f87-default-rtdb.firebaseio.com/").reference.child("Users").child(firebaseUserID)
                    val  userHashMap  = HashMap<String,Any>()
                    userHashMap ["uid"]= firebaseUserID
                    userHashMap["username"] = username
                      userHashMap["cover"] =  " https://firebasestorage.googleapis.com/v0/b/chatapp-c6f87.appspot.com/o/cover.jpg?alt=media&token=c8ca84b2-7da9-4b18-9259-65b0711e6ec0  "
                    userHashMap["profile"] =  "https://firebasestorage.googleapis.com/v0/b/chatapp-c6f87.appspot.com/o/profile.png?alt=media&token=96a701b1-d378-4b9f-bf72-306f6503134b"
                    userHashMap["status"] =  "offline"
                    userHashMap["search"] = username.toLowerCase()
                    userHashMap["facebook"] =  "https://m.facebook.com"
                    userHashMap["instagram"] =  "https://m.instagram.com"
                    userHashMap["portfolio"] =  "https://www.google.com "

                    refUsers.updateChildren(userHashMap)
                        .addOnCompleteListener{ task->
                            if (task.isSuccessful)
                            {
                                val intent = Intent(this@SignUpActivity,MainActivity::class.java )
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                finish()


                            }

                        }
                }
                else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@SignUpActivity, " Error Message " + task.exception!!.message.toString(),Toast.LENGTH_SHORT).show()
                }
            }


        }




    }
}