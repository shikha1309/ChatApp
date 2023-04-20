package com.shikha.chatapp.Fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.shikha.chatapp.AdapterClasses.UserAdapter
import com.shikha.chatapp.ModelClasses.Users
import com.shikha.chatapp.R
import java.util.*
import kotlin.collections.ArrayList


class SearchFragment : Fragment() {
    // These are the instance of the User Adapter
    // mUser Array list save the data which is retrieve from database
    private var userAdapter:UserAdapter? =null
    private var mUsers:List<Users>? = null
    private var recyclerView : RecyclerView?=null
    private var searchUsersEdt:EditText ?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
          val view:View = inflater.inflate(R.layout.fragment_search, container, false)
        recyclerView = view .findViewById(R.id.searchList)
        //  What setHasFixedSize does is that it makes sure (by user input) that this change of size of RecyclerView is constant
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        searchUsersEdt =view.findViewById(R.id.searchUsersEdt)


        mUsers =ArrayList()
        retrieveAllUsers()


        searchUsersEdt?.addTextChangedListener( object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            // This will show all the users from the fun searchForUsers
            override fun onTextChanged(cs: CharSequence?, start: Int, before: Int, count: Int) {
                searchForUsers(cs.toString().toLowerCase(Locale.ROOT))
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        return  view

    }

    // This retrieve function work only if search edit text is empty
    // when user typed in box the searchUser function will call
    private fun retrieveAllUsers()
    {
        var firebaseUserID = FirebaseAuth.getInstance().currentUser!! .uid
        val refUsers= FirebaseDatabase.getInstance().reference.child("Users")

        refUsers.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(p0: DataSnapshot)
             {
                 (mUsers as ArrayList<Users>).clear()
                  // if edit text is empty then it will retrieve all the Users
                 if(searchUsersEdt?.text.toString()==" "){
                     for(snapshot in p0.children)
                     {
                         val user : Users? = snapshot.getValue(Users :: class.java)
                         // This will add all users in mUsers Array list except me
                         if(!(user!!.getUID()).equals(firebaseUserID))
                         {
                             (mUsers as ArrayList<Users>).add(user)
                         }
                     }
                     // Retrieving all the Users in the Array List
                     userAdapter = UserAdapter(context!! , mUsers!! , false)
                     recyclerView!!.adapter = userAdapter
                 }

            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }

    // this will give text view for search  // for this i will use search Query
     private fun searchForUsers (str:String)
     {
              var firebaseUserID = FirebaseAuth.getInstance().currentUser!!.uid
               val queryUsers = FirebaseDatabase.getInstance().reference
                 .child("Users").orderByChild("search")
                 .startAt(str)    // str = user will search for editText
                   .endAt(str +  "\uf8ff")

         queryUsers .addValueEventListener(object:ValueEventListener {
             override fun onDataChange(p0: DataSnapshot) {
                 (mUsers as ArrayList<Users>).clear()
                 for(snapshot in p0.children)
                 {
                     val user:Users? = snapshot.getValue(Users :: class.java)
                     // This will add all users in mUsers Array list except me
                     if(!(user!!.getUID()).equals(firebaseUserID))
                     {
                         (mUsers as ArrayList<Users>).add(user)
                     }
                 }
                 userAdapter = UserAdapter(context!! , mUsers!! , false)
                 recyclerView!!.adapter = userAdapter
             }

             override fun onCancelled(p0: DatabaseError) {

             }

         })


    }


}