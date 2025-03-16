package com.example.recipieapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.recipieapp.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityCategoryBinding.inflate(layoutInflater)
    }
    private lateinit var adapter: CategoryAdapter
    private lateinit var dataList: ArrayList<Recipe>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.heading.text = intent.getStringExtra("TITLE").toString()
        binding.goBcak.setOnClickListener { finish() }
        setUpRecyclerView()
    }
    private fun setUpRecyclerView() {
        dataList= ArrayList()

        binding.rvCategory.layoutManager= LinearLayoutManager(this)
        var db= Room.databaseBuilder(this@CategoryActivity,AppDatabase::class.java,"db_name")
            .allowMainThreadQueries()//to allow queries to run n main thread no suspend functions
            .fallbackToDestructiveMigration()//to ensure that it works well with the older version
            .createFromAsset("recipe.db")// to specify ki hmare paas databse me pehel se kuch values hai aur tm unko use kr lo
            .build()
        var daoObject=db.getDao()
        var recipes=daoObject.getAll()
        for(i in recipes!!.indices){
            if (recipes[i]!!.category.contains(intent.getStringExtra("CATEGORY")!!)){
                dataList.add(recipes[i]!!)
            }

        }
        adapter=CategoryAdapter(dataList,this)
        binding.rvCategory.adapter=adapter

}}