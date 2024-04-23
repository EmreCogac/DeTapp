package com.example.detapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.detapp.R
import com.example.detapp.model.PostDataModel
import com.example.detapp.model.PostReadModel

class PostAdapter(
    private val postReadModel: List<PostReadModel>,
): RecyclerView.Adapter<PostAdapter.PostChildHolder>(){

    interface ItemClickListener{

    }
    inner class PostChildHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val bookname: TextView = itemView.findViewById(R.id.kitapadiList)
        val user: TextView = itemView.findViewById(R.id.KitapSahipList)


        init {

        }
    }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostChildHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.post_list, parent, false)
            return PostChildHolder(view)
        }

        override fun onBindViewHolder(holder: PostChildHolder, position: Int) {
            val box = postReadModel.get(position)

            holder.bookname.text = box.bookname
            holder.user.text = box.usermail


        }

        override fun getItemCount(): Int {
            return postReadModel.size
        }

}