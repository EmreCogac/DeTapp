package com.example.detapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.detapp.R
import com.example.detapp.model.PostDataModel
import com.example.detapp.model.PostReadModel

class PostAdapter(
    private val postReadModel: List<PostReadModel>,
    private val itemClickListener: (PostReadModel) -> Unit //
): RecyclerView.Adapter<PostAdapter.PostChildHolder>(){

    interface ItemClickListener{
        fun onButtonClick(position: PostReadModel )
    }
    inner class PostChildHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val bookname: TextView = itemView.findViewById(R.id.kitapadiList)
        val user: TextView = itemView.findViewById(R.id.KitapSahipList)
        val btn : Button = itemView.findViewById(R.id.deneme)

        init {

            btn.setOnClickListener {
                val buttonPosition = absoluteAdapterPosition
                if (buttonPosition != RecyclerView.NO_POSITION) {
                    val clickedItem = postReadModel[buttonPosition]
                    itemClickListener(clickedItem)
                }
            }

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
            holder.btn.text = box.uid


        }

        override fun getItemCount(): Int {
            return postReadModel.size
        }

}