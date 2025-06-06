package com.example.expensetrackingapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class AddEditExpenseActivity : AppCompatActivity() {
    private lateinit var expenseName: EditText
    private lateinit var expenseCategory: Spinner
    private lateinit var expenseCost: EditText
    private lateinit var dateButton: Button
    private lateinit var expenseReason: EditText
    private lateinit var expenseNotes: EditText
    private lateinit var saveButton: Button

    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        expenseName = findViewById(R.id.expense_name)
        expenseCategory = findViewById(R.id.expense_category)
        expenseCost = findViewById(R.id.expense_cost)
        dateButton = findViewById(R.id.date_button)
        expenseReason = findViewById(R.id.expense_reason)
        expenseNotes = findViewById(R.id.expense_notes)
        saveButton = findViewById(R.id.save_button)

        setupCategorySpinner()

        dateButton.setOnClickListener {
            showDatePicker()
        }

        saveButton.setOnClickListener {
            saveExpense()
        }
    }

    private fun setupCategorySpinner() {
        val categories = arrayOf("Food", "Transport", "Shopping", "Entertainment", "Bills", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        expenseCategory.adapter = adapter
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this, { _, y, m, d ->
            selectedDate = String.format("%04d-%02d-%02d", y, m + 1, d)
            dateButton.text = selectedDate
        }, year, month, day)

        datePicker.show()
    }

    private fun saveExpense() {
        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show()
            return
        }

        val name = expenseName.text.toString().trim()
        val costText = expenseCost.text.toString().trim()

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show()
            return
        }

        if (costText.isEmpty()) {
            Toast.makeText(this, "Please enter a cost", Toast.LENGTH_SHORT).show()
            return
        }

        val category = expenseCategory.selectedItem?.toString() ?: "Other"
        val cost = costText.toDoubleOrNull() ?: 0.0
        val reason = expenseReason.text.toString()
        val notes = expenseNotes.text.toString()

        val newExpense = Expense(
            date = selectedDate,
            name = name,
            category = category,
            cost = cost,
            reason = reason,
            notes = notes
        )

        val resultIntent = Intent()
        resultIntent.putExtra("newExpense", newExpense)
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}

