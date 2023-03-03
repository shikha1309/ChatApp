package com.shikha.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

import com.shikha.chatapp.Fragment.ChatFragment
import com.shikha.chatapp.Fragment.SearchFragment
import com.shikha.chatapp.Fragment.SettingsFragment
import com.shikha.chatapp.ModelClasses.Users
import com.squareup.picasso.Picasso


class  MainActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var viewPager: ViewPager
    private lateinit var profileImage: ImageView
    lateinit var userName:TextView
    lateinit var tabLayout: TabLayout
    private var refUsers : DatabaseReference?=null
    private var firebaseUser: FirebaseUser? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbarMain)
        userName= findViewById(R.id.user_name)
        profileImage = findViewById(R.id.profileImage)
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)
          val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(ChatFragment(), "Chats")
        viewPagerAdapter.addFragment(SearchFragment(), "Search")
        viewPagerAdapter.addFragment(SettingsFragment(), "Settings")


        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        setUpToolbar()
         // This need  for Display Name and Profile  Picture Initializations
        firebaseUser =FirebaseAuth.getInstance().currentUser
        refUsers= firebaseUser?.let {
            FirebaseDatabase.getInstance().reference.child("Users").child(
                it.uid)
        }

        // Display Name and Profile Picture
        refUsers!!.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //  For accessing the profile and name // we have to create the data class that is model classes //and form where we retrieve the profile and Name here
              if (snapshot.exists()) {
                  // This will import user model class
                  val  user:Users? =snapshot.getValue(Users:: class.java)
                  if (user != null) {
                      userName.text=user.getUserName()
                  }
                  if (user != null) {
                      Picasso.get().load(user.getProfile()).into(profileImage)
                  }



              }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


    }


    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "  "
    }

    internal class ViewPagerAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(fragmentManager) {

        private val fragments: ArrayList<Fragment>
        private val titles: ArrayList<String>

        init {
            fragments = ArrayList<Fragment>()
            titles = ArrayList<String>()
        }


        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }


        override fun getCount(): Int {
            return fragments.size
        }


        // adding a fragment
        fun addFragment(fragment: Fragment, title: String) {
            fragments.add(fragment)
            titles.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout_main -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
                return true
        }
    }
    return false
}
}