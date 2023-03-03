package com.shikha.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference

class LoginActivity : AppCompatActivity() {
    private lateinit var edtEmail:EditText
    private lateinit var edtPassword:EditText
    private lateinit var btnLogin:Button
    private lateinit var btnSignup:Button
    private lateinit var appLogo:ImageView

    private lateinit var mAuth:FirebaseAuth
    var firebaseUser :FirebaseUser?=null
    private lateinit var refUsers: DatabaseReference




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        FirebaseApp.initializeApp(this)
        mAuth= FirebaseAuth.getInstance()  // Just connecting the firebase
        edtEmail =findViewById(R.id.edtEmailLogin)
        edtPassword=findViewById(R.id.edtPasswordLogin)
        btnLogin =findViewById(R.id.btnLogin)
        btnSignup = findViewById(R.id.btnSignupFirstPage)
        appLogo =findViewById(R.id.appLogo)


        btnSignup.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnLogin.setOnClickListener {

            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            loginUser()
        }
    }

    override fun onStart() {
        super.onStart()
        firebaseUser= FirebaseAuth.getInstance().currentUser
        if (firebaseUser!=null)  // if user is not equal to null , that means user is already logged in
        {
            val intent = Intent( this@LoginActivity ,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loginUser(){

        val email: String= edtEmail.text.toString()
        val password: String= edtPassword.text.toString()



       if (email=="")
        {
            Toast.makeText(this@LoginActivity, " Please Write Your Email Name", Toast.LENGTH_SHORT).show()
        }
        else if (password=="")
        {
            Toast.makeText(this@LoginActivity, " Please Enter Your  Password", Toast.LENGTH_SHORT).show()
        }

     else{

           // Logic for logging user
           mAuth.signInWithEmailAndPassword(email, password)
               .addOnCompleteListener(this) { task ->
                   if (task.isSuccessful) {
                       // Sign in success, update UI with the signed-in user's information

                       val intent = Intent(this@LoginActivity,MainActivity::class.java )
                       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                       startActivity(intent)
                       finish()

                   }
                   else {
                       // If sign in fails, display a message to the user.
                       Toast.makeText(this@LoginActivity, " Error Message: " + task.exception!!.message.toString(),Toast.LENGTH_SHORT).show()
                   }
               }




     }
    }

}