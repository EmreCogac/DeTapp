package com.example.detapp.adapter
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.detapp.R
import com.example.detapp.model.PostReadModel
import com.example.detapp.viewmodel.ProfileViewModel
import kotlin.coroutines.coroutineContext

class PostAdapter(
    private var originalPostReadModel: List<PostReadModel>,
    private var postReadModel: List<PostReadModel>,
    private val State: String,
    private val application: Context,
    private val itemClickListener: (PostReadModel) -> Unit //

): RecyclerView.Adapter<PostAdapter.PostChildHolder>(){
    interface ItemClickListener{
        fun onButtonClick(position: PostReadModel )
    }
    @SuppressLint("NotifyDataSetChanged")
    fun filter(text: String) {
        postReadModel = if (text.isEmpty()) {
            originalPostReadModel
        } else {
            originalPostReadModel.filter { it.bookname.contains(text, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }

//    @SuppressLint("NotifyDataSetChanged")
//    fun setData(data: List<PostReadModel>) {
//        originalPostReadModel = data
//        postReadModel = data
//        notifyDataSetChanged()
//    }

    inner class PostChildHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val bookname: TextView = itemView.findViewById(R.id.kitapadiList)
        val user: TextView = itemView.findViewById(R.id.KitapSahipList)
        val btn : ImageButton = itemView.findViewById(R.id.deneme)

        init {
            btn.setOnClickListener {
                val buttonPosition = absoluteAdapterPosition
                if (buttonPosition != RecyclerView.NO_POSITION) {
                    val clickedItem = originalPostReadModel[buttonPosition]
                    itemClickListener(clickedItem)
                    when(State){
                        "profile" -> {
                            val builder : AlertDialog.Builder = AlertDialog.Builder(application)
                            builder
                                .setTitle("Simek istediğine emin misin ?")
                                .setPositiveButton("Evet") { dialog, which ->
                                    originalPostReadModel = originalPostReadModel.toMutableList().apply { removeAt(buttonPosition) }
                                    postReadModel = postReadModel.toMutableList().apply { removeAt(buttonPosition) }
                                    notifyItemRemoved(buttonPosition)
                                }
                                .setNegativeButton("Hayır") { dialog, which ->
                                    // Do something else.
                                }
                            val dialog: AlertDialog = builder.create()
                            dialog.show()
                        }
                    }
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
            when(State){
                "profile" -> {
                    holder.btn.setBackgroundColor(Color.parseColor("#FFA50B0B"))
                    holder.btn.setImageResource(R.drawable.baseline_delete_outline_24)
                }
                "post" -> {
                    holder.btn.setBackgroundColor(Color.parseColor("#0F2C59"))
                    holder.btn.setImageResource(R.drawable.baseline_mail_outline_24)
                }
            }


        }

        override fun getItemCount(): Int {
            return postReadModel.size
        }

}