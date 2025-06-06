package com.example.expensetrackingapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    private val expenses = mutableListOf<Expense>()
    private lateinit var adapter: ExpenseAdapter
    private lateinit var addExpenseLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.expense_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ExpenseAdapter(expenses)
        recyclerView.adapter = adapter

        loadExpenses()

        addExpenseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                @Suppress("DEPRECATION")
                val newExpense = data?.getSerializableExtra("newExpense") as? Expense
                newExpense?.let {
                    expenses.add(it)
                    adapter.notifyItemInserted(expenses.size - 1)
                    saveExpenses()
                }
            }
        }

        val addButton = findViewById<Button>(R.id.add_expense_button)
        addButton.setOnClickListener {
            val intent = Intent(this, AddEditExpenseActivity::class.java)
            addExpenseLauncher.launch(intent)
        }
    }

    private fun saveExpenses() {
        val sharedPref = getSharedPreferences("ExpenseTrackerPrefs", MODE_PRIVATE)
        val editor = sharedPref.edit()
        val gson = Gson()
        val json = gson.toJson(expenses)
        editor.putString("expenses_list", json)
        editor.apply()
    }

    private fun loadExpenses() {
        val sharedPref = getSharedPreferences("ExpenseTrackerPrefs", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPref.getString("expenses_list", null)
        val type = object : TypeToken<MutableList<Expense>>() {}.type
        val loadedExpenses: MutableList<Expense>? = gson.fromJson(json, type)
        loadedExpenses?.let {
            expenses.addAll(it)
        }
    }
}
