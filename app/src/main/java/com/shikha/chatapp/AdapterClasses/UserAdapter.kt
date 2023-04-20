package com.shikha.chatapp.AdapterClasses

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shikha.chatapp.MessageChatActivity
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
         return  ViewHolder(view)  // now userAdapter has know that this layout has these Controllers
    }

    override fun getItemCount(): Int {
        return mUsers.size

    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        // data which will Display
        val  user:Users =mUsers[i]
       holder.userNameTxt.text= user!!.getUserName()
        Picasso.get().load(user.getProfile()).placeholder(R.drawable.person).into(holder.profileImageView)

        holder.itemView.setOnClickListener {
            val options = arrayOf<CharSequence>(
                "Send Message",
                 "Visit Profile"

            )
            val builder  : AlertDialog.Builder = AlertDialog.Builder(mContext)
            builder.setTitle("You want to?")
            builder.setItems(options, DialogInterface.OnClickListener{ dialog, which ->
                if ( which==0) {
                    // This will open Chat Activity
                    // visit id = that person that i will click


                    val intent = Intent(mContext ,MessageChatActivity::class.java)
                    intent.putExtra("visit_id" , user.getUID())
                    mContext.startActivity(intent   )
                }


            })


        }



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