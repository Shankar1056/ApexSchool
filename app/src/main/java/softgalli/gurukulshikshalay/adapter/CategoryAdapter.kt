package softgalli.gurukulshikshalay.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import softgalli.gurukulshikshalay.R
import softgalli.gurukulshikshalay.intrface.ClickPosition
import softgalli.gurukulshikshalay.model.QuizCategory


import java.util.ArrayList


class CategoryAdapter(private val context: Context, private val imageList: ArrayList<QuizCategory>, private val clickPosition: ClickPosition) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.category_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = imageList[position]
        val context = holder.itemView.context

//        Picasso.with(context).load(item.catIcon).into(holder.cat_image)
        holder.cat_text.text = item.catName
        holder.itemView.setOnClickListener { clickPosition.pos(position) }
    }

    override fun getItemCount(): Int {
        return imageList.size

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var cat_text: TextView
        var cat_image: ImageView

        init {
            cat_text = itemView.findViewById<View>(R.id.cat_text) as TextView
            cat_image = itemView.findViewById<View>(R.id.cat_image) as ImageView

        }
    }
}
