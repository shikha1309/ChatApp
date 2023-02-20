package com.shikha.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

import com.shikha.chatapp.Fragment.ChatFragment
import com.shikha.chatapp.Fragment.SearchFragment
import com.shikha.chatapp.Fragment.SettingsFragment

class  MainActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var textView: TextView
    lateinit var imageView: ImageView
    lateinit var viewPager :ViewPager
    lateinit var tabLayout:TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbarMain)

        textView =findViewById(R.id.userName)
        imageView= findViewById(R.id.profileImage)
        viewPager =findViewById(R.id.viewPager)
        tabLayout=findViewById(R.id.tabLayout)

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(ChatFragment(), "Chats")
        viewPagerAdapter.addFragment(SearchFragment() , "Search")
        viewPagerAdapter.addFragment(SettingsFragment(),"Settings")
        viewPager.adapter=viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        setUpToolbar()

    }

    private fun setUpToolbar() {
       setSupportActionBar(toolbar)
        supportActionBar?.title = " "


    }

    internal  class ViewPagerAdapter(fragmentManager: FragmentManager):
            FragmentPagerAdapter(fragmentManager){

        private val fragments : ArrayList<Fragment>
        private val titles : ArrayList<String>

        init {
            fragments= ArrayList<Fragment>()
            titles= ArrayList<String>()
        }





        override fun getItem(position: Int): Fragment {
        return fragments[position]
        }


        override fun getCount(): Int {
           return fragments.size
        }


        // adding a fragment
        fun addFragment(fragment:Fragment ,title:String)
        {
           fragments.add(fragment)
            titles.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }

    }

}