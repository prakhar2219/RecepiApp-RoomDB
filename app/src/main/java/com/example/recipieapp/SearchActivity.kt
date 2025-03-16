package com.example.recipieapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.recipieapp.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var adapter: SearchAdapter
    private lateinit var dataList: ArrayList<Recipe>
    private lateinit var recipes: List<Recipe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.searchView.requestFocus()
        var db = Room.databaseBuilder(this@SearchActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries()//to allow queries to run n main thread no suspend functions
            .fallbackToDestructiveMigration()//to ensure that it works well with the older version
            .createFromAsset("recipe.db")// to specify ki hmare paas databse me pehel se kuch values hai aur tm unko use kr lo
            .build()
        var daoObject = db.getDao()
        recipes = daoObject.getAll()

//        text watcher to watch if something(text) has changed
        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                s mtlb jo change hone ke baad data bn rha hai
//                pehle check krenge ki kya wo null hai ya nhi,mtlb saamne waale ne kuch text daala hua hai
                if (s.toString() != "") {
                    filterData(s.toString())
                }
                else{
                    setUpRecyclerView()
                }

            }


            override fun afterTextChanged(s: Editable?) {
            }

        })
//        to get and hide the keyboard after search typing
        val inputMethodManagement = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        binding.searchView.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) { // Ensures it only triggers on touch
                inputMethodManagement.hideSoftInputFromWindow(v.windowToken, 0)
            }
            false // Return false to allow further event processing
        }
        binding.searchView.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
            }
        }
        binding.rvSearch.setOnTouchListener { v, event ->
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
            false
        }


        binding.backButton.setOnClickListener {
finish()
        }
        setUpRecyclerView()
    }

    private fun filterData(filterText: String) {
//        filtertext real time data btayegaa jo text aa rha hai,iske hiaasb se recpies ke data ko update krenge
//        filterlist to specify ki kya kya chenge krna hai
        var filterData = ArrayList<Recipe>()
        for (i in recipes.indices) {
            if (recipes[i].tittle.lowercase().contains(filterText.lowercase())) {
                filterData.add(recipes[i]!!)
            }
            adapter.filterList(filterList = filterData)
        }
    }
    private fun setUpRecyclerView() {
        dataList = ArrayList()

        binding.rvSearch.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )

//        object creation and then accessing the mtehods through it

        for (i in recipes!!.indices) {
            if (recipes[i]!!.category.contains("Popular")) {
                dataList.add(recipes[i]!!)
            }

        }
        adapter = SearchAdapter(dataList, this)
        binding.rvSearch.adapter = adapter


    }

}