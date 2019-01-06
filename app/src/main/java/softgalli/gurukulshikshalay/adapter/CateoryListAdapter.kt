package softgalli.gurukulshikshalay.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.util.ArrayList
import softgalli.gurukulshikshalay.R
import softgalli.gurukulshikshalay.intrface.ClickPosition
import softgalli.gurukulshikshalay.model.QuizSubCategory

/**
 * Created by Shankar on 12/28/2017.
 */

class CateoryListAdapter(private val context: Context, private val imageList: ArrayList<QuizSubCategory>, private val clickPosition: ClickPosition) : RecyclerView.Adapter<CateoryListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.subcategory_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.cat_text.text = imageList[position].subCatNme

        holder.itemView.setOnClickListener { clickPosition.pos(position) }
    }

    override fun getItemCount(): Int {
        return imageList.size

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var cat_text: TextView

        init {
            cat_text = itemView.findViewById<View>(R.id.cat_text) as TextView

        }
    }
}
