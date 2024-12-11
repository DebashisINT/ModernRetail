package com.example.modernretail.others

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Filter
import android.widget.TextView
import com.example.modernretail.R

class DropdownAdapter(context: Context, val items: ArrayList<DropdownData>,var listner: OnClick) :
    ArrayAdapter<DropdownData>(context, 0, items) {

    private var filteredItems: List<DropdownData> = items

    override fun getCount(): Int {
        return filteredItems.size
    }

    override fun getItem(position: Int): DropdownData? {
        return filteredItems[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.selection_dropdown, parent, false)
        val item = getItem(position)
        val textView = view.findViewById<TextView>(R.id.tv_item)
        textView.text = item!!.name
        textView.setOnClickListener {
            listner.onClick(item)
        }

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                val filteredList = if (constraint.isNullOrEmpty()) {
                    items
                } else {
                    items.filter {
                        it.name.contains(constraint.toString(), ignoreCase = true)
                    }
                }
                filterResults.values = filteredList
                filterResults.count = filteredList.size
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredItems = results?.values as List<DropdownData>? ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }

    interface OnClick{
        fun onClick(item: DropdownData)
    }
}