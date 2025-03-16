package com.example.recipieapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipieapp.databinding.CategoryItemBinding

class CategoryAdapter(
    var dataList: ArrayList<Recipe>,
    var context: Context
): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        var view=CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.binding.foodName.text=dataList[position].tittle
        var time= dataList[position].ing.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        holder.binding.foodTime.text= time[0]
        Glide.with(context).load(dataList[position].img).into(holder.binding.imageCategory)
        holder.binding.nextButton.setOnClickListener{
            var intent= Intent(context,RecipeActivity::class.java)
            intent.putExtra("tittle",dataList[position].tittle)
            intent.putExtra("ing",dataList[position].ing)
            intent.putExtra("img",dataList[position].img)
            intent.putExtra("des",dataList[position].des)
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    inner class ViewHolder(var binding: CategoryItemBinding) :RecyclerView.ViewHolder(binding.root) {

    }
}