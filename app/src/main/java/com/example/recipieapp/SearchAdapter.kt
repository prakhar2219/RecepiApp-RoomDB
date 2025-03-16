package com.example.recipieapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipieapp.databinding.SearchItemBinding

class SearchAdapter(var dataList: ArrayList<Recipe>, var context: Context) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    //    function jo ki kaam kre jb datalist update ho aur datalist ko update kr de accordigly
    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filterList: ArrayList<Recipe>) {
        dataList = filterList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(dataList[position].img).into(holder.binding.searchItemImage)
        holder.binding.searchItemText.text = dataList[position].tittle
        holder.itemView.setOnClickListener {
            var intent = Intent(context, RecipeActivity::class.java)
            intent.putExtra("tittle", dataList[position].tittle)
            intent.putExtra("ing", dataList[position].ing)
            intent.putExtra("img", dataList[position].img)
            intent.putExtra("des", dataList[position].des)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    inner class ViewHolder(var binding: SearchItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

}