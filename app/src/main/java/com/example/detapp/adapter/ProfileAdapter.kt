package com.example.detapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.detapp.R
import com.example.detapp.model.PostReadModel

class ProfileAdapter (
    private var postReadModel: List<PostReadModel>,
    private var itemClickListener: (PostReadModel,Int,MutableList<PostReadModel>) -> Unit //
): RecyclerView.Adapter<ProfileAdapter.ProfileChildHolder>(){
    interface ItemClickListener{
        fun onButtonClick(position: PostReadModel , absulatePosition : Int, postList: MutableList<PostReadModel>)
    }
    inner class ProfileChildHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val bookname: TextView = itemView.findViewById(R.id.kitapadiList)
        val user: TextView = itemView.findViewById(R.id.KitapSahipList)
        val btn : ImageButton = itemView.findViewById(R.id.deneme)

        init {
            btn.setOnClickListener {
                val buttonPosition = absoluteAdapterPosition
                if (buttonPosition != RecyclerView.NO_POSITION) {
                    val clickedItem = postReadModel[buttonPosition]
                    postReadModel = postReadModel.toMutableList()
                    itemClickListener(clickedItem,buttonPosition,
                        postReadModel as MutableList<PostReadModel>
                    )
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAdapter.ProfileChildHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_list, parent, false)
        return ProfileChildHolder(view)
    }

    override fun getItemCount(): Int {
        return postReadModel.size
    }

    override fun onBindViewHolder(holder: ProfileAdapter.ProfileChildHolder, position: Int) {
        val box = postReadModel.get(position)
        holder.bookname.text = box.bookname
        holder.user.text = box.usermail
        holder.btn.setBackgroundColor(Color.parseColor("#FFA50B0B"))
        holder.btn.setImageResource(R.drawable.baseline_delete_outline_24)


    }

}