package com.example.recipieapp

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.recipieapp.databinding.ActivityRecipeBinding

class RecipeActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityRecipeBinding.inflate(layoutInflater)
    }
    var imageCrop=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var tittle = intent.getStringExtra("tittle")
        var ing = intent.getStringExtra("ing")
        var img = intent.getStringExtra("img")
        var des = intent.getStringExtra("des")
        Glide.with(this).load(img).into(binding.itemImage)
        binding.desTitle.text = tittle
        binding.ingData.text = ing
        binding.stepData.text = des

        binding.fullScreen.setOnClickListener {
            if (imageCrop) {
            binding.itemImage.scaleType = ImageView.ScaleType.FIT_CENTER
                binding.fullScreen.setColorFilter(Color.BLACK,PorterDuff.Mode.SRC_ATOP)
                Glide.with(this).load(img).into(binding.itemImage)
binding.shadowImage.visibility= View.GONE
                imageCrop=!imageCrop
            }
            else{
                binding.itemImage.scaleType = ImageView.ScaleType.CENTER_CROP
                binding.fullScreen.setColorFilter(null)
                Glide.with(this).load(img).into(binding.itemImage)
                binding.shadowImage.visibility= View.GONE
                imageCrop=!imageCrop
            }
        }
        binding.backDes.setOnClickListener {
            finish()
        }

    }
}