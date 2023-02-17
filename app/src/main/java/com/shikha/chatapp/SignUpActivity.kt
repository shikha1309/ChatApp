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

class SignUpActivity : AppCompatActivity() {

    private lateinit var appLogo: ImageView
    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtConfirmPassword: EditText
    private lateinit var btnSignup: Button

    private lateinit var mAuth:FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        FirebaseApp.initializeApp(this)
        mAuth= FirebaseAuth.getInstance()  // Just connecting the firebase

       edtName = findViewById(R.id.edtName)
        edtEmail =findViewById(R.id.edtEmail)
        edtPassword=findViewById(R.id.edtPassword)
        edtConfirmPassword= findViewById(R.id.edtConfirmPassword)
        btnSignup = findViewById(R.id.btnSignup)
        appLogo =findViewById(R.id.appLogo)


        btnSignup.setOnClickListener {
              val email = edtEmail.text.toString()
               val password =edtPassword.text.toString()

            signUp(email,password)
        }
    }

    private fun signUp(email:String,password:String){
        // logic for creating user

        mAuth  .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    // code for jumping to home activity or main activity
                    // So intent will needed

                    val intent = Intent(this@SignUpActivity,MainActivity::class.java )
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@SignUpActivity, "Some Error Occurred",Toast.LENGTH_SHORT).show()
                }
            }
    }
}