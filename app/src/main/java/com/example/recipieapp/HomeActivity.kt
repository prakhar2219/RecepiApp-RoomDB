package com.example.recipieapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.recipieapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: PopularAdapter
    private lateinit var dataList: ArrayList<Recipe>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.Salad.setOnClickListener {
            val intent = Intent(this@HomeActivity, CategoryActivity::class.java)
            intent.putExtra("TITLE", "Salad")
            intent.putExtra("CATEGORY", "Salad")
            startActivity(intent)

        }
        binding.MainDish.setOnClickListener {
            val intent = Intent(this@HomeActivity, CategoryActivity::class.java)
            intent.putExtra("TITLE", "Main Dish")
            intent.putExtra("CATEGORY", "Dish")
            startActivity(intent)

        }
        binding.Drinks.setOnClickListener {
            val intent = Intent(this@HomeActivity, CategoryActivity::class.java)
            intent.putExtra("TITLE", "Drinks")
            intent.putExtra("CATEGORY", "Drinks")
            startActivity(intent)

        }
        binding.Desert.setOnClickListener {
            val intent = Intent(this@HomeActivity, CategoryActivity::class.java)
            intent.putExtra("TITLE", "Desserts")
            intent.putExtra("CATEGORY", "Desserts")
            startActivity(intent)

        }

        setUpRecyclerView()
        binding.Search.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

    private fun setUpRecyclerView() {
        dataList = ArrayList()

        binding.rvPopular.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        var db = Room.databaseBuilder(this@HomeActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries()//to allow queries to run n main thread no suspend functions
            .fallbackToDestructiveMigration()//to ensure that it works well with the older version
            .createFromAsset("recipe.db")// to specify ki hmare paas databse me pehel se kuch values hai aur tm unko use kr lo
            .build()
        var daoObject = db.getDao()
        var recipes = daoObject.getAll()
        for (i in recipes!!.indices) {
            if (recipes[i]!!.category.contains("Popular")) {
                dataList.add(recipes[i]!!)
            }

        }
        adapter = PopularAdapter(dataList, this)
        binding.rvPopular.adapter = adapter


    }
}