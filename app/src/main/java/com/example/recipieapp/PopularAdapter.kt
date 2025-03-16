package com.example.recipieapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipieapp.databinding.PopularItemBinding

class PopularAdapter(
    var dataList: ArrayList<Recipe>,
    var context: Context
) : RecyclerView.Adapter<PopularAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = PopularItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(dataList.get(position).img).into(holder.binding.popularImg)
        holder.binding.popularText.text = dataList.get(position).tittle
//        hr ek line ko alg alg nikala aur jo line empty hai usko drop kr diya ,aur baaki lines kaa ek array bna diya aur uska first eklement
//        hmara time hoga
        var time = dataList.get(position).ing.split("\n".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        holder.binding.popularTime.text = time.get(0)
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

    inner class ViewHolder(var binding: PopularItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}