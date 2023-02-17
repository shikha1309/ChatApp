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

class LoginActivity : AppCompatActivity() {
    private lateinit var edtEmail:EditText
    private lateinit var edtPassword:EditText
    private lateinit var btnLogin:Button
    private lateinit var btnSignup:Button
    private lateinit var appLogo:ImageView


    private lateinit var mAuth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        FirebaseApp.initializeApp(this)
        mAuth= FirebaseAuth.getInstance()  // Just connecting the firebase
        edtEmail =findViewById(R.id.edtEmail)
        edtPassword=findViewById(R.id.edtPassword)
        btnLogin =findViewById(R.id.btnLogin)
        btnSignup = findViewById(R.id.btnSignup)
        appLogo =findViewById(R.id.appLogo)


        btnSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            login(email, password);
        }
    }

    private fun login(email: String ,password:String){

        // Logic for logging user

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val intent = Intent(this@LoginActivity,MainActivity::class.java )
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@LoginActivity , " User does not Exist", Toast.LENGTH_SHORT).show()
                }
            }

    }

}