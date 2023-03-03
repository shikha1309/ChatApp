package com.shikha.chatapp.AdapterClasses

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shikha.chatapp.ModelClasses.Users
import com.shikha.chatapp.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

// Note-- I have to retrieve the users form the database and store it in a list and pass that list to the User Adapter
// There will be parameter to this class // First is context // Second will be list that is model list // third will be chat check

class UserAdapter(
    mContext: Context,
    mUsers: List<Users>, // This list will pass to the adapter class
    isChatCheck:Boolean
)  : RecyclerView.Adapter<UserAdapter.ViewHolder?>()

{
    private val mContext:Context
    private val mUsers:List<Users>
   private val  isChatCheck:Boolean

   init{
       this.mContext =mContext
       this.mUsers = mUsers
       this.isChatCheck=isChatCheck


   }


    override fun onCreateViewHolder(viewGroup : ViewGroup, viewType: Int): ViewHolder {
        val view :View = LayoutInflater.from(mContext).inflate(R.layout.user_search_item_layout ,viewGroup ,false)
        return  UserAdapter.ViewHolder(view)  // now userAdapter has know that this layout has these Controllers
    }

    override fun getItemCount(): Int {
        return mUsers.size

    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        // data which will Display
        val  user:Users =mUsers[i]
       holder.userNameTxt.text= user.getUserName()
        Picasso.get().load(user.getProfile()).placeholder(R.drawable.person).into(holder.profileImageView)



    }
    class ViewHolder (itemView: View):RecyclerView.ViewHolder(itemView  ){
        // all these controller are Accessing in this view Holder
        var userNameTxt: TextView
        var lastMessageTxt: TextView
        var profileImageView:CircleImageView
        var onlineImageView:CircleImageView
        var offlineImageView:CircleImageView



        init{
            userNameTxt=itemView.findViewById(R.id.username)
            lastMessageTxt=itemView.findViewById(R.id.message_last)
            profileImageView=itemView.findViewById(R.id.profileImage)
            onlineImageView =itemView.findViewById(R.id.image_online)
            offlineImageView =itemView.findViewById(R.id.image_offline)
        }
    }


}