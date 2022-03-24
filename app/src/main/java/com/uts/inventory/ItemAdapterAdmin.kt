package com.uts.inventory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ItemAdapterAdmin(val context: Context, val items: ArrayList<AdminModelClass>) :
    RecyclerView.Adapter<ItemAdapterAdmin.ViewHolder>() {


    /**
     * Inflates the item views which is designed in the XML layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_row_admin,
                parent,
                false
            )
        )
    }

    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items.get(position)

        holder.tvNameAdmin.text = item.name
        holder.tvEmail.text = item.email
        holder.tvPassword.text = item.password

        // Updating the background color according to the odd/even positions in list.
        if (position % 2 == 0) {
            holder.llMainAdmin.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorLightGray
                )
            )
        } else {
            holder.llMainAdmin.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))
        }

        holder.ivEditAdmin.setOnClickListener { view ->

            if (context is AdminActivity) {
                context.updateRecordDialog(item)
            }
        }

        holder.ivDeleteAdmin.setOnClickListener { view ->

            if (context is AdminActivity) {
                context.deleteRecordAlertDialog(item)
            }
        }
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return items.size
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each item to
        val llMainAdmin : LinearLayout = view.findViewById(R.id.llMainAdmin)
        val tvNameAdmin : TextView = view.findViewById(R.id.tvNameAdmin)
        val tvEmail : TextView = view.findViewById(R.id.tvEmail)
        val tvPassword : TextView = view.findViewById(R.id.tvPassword)
        val ivEditAdmin : ImageView = view.findViewById(R.id.ivEditAdmin)
        val ivDeleteAdmin : ImageView = view.findViewById(R.id.ivDeleteAdmin)
    }
}