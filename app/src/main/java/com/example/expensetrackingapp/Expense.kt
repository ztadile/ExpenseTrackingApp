package com.example.expensetrackingapp

import java.io.Serializable

data class Expense(
    val date: String,
    val name: String,
    val category: String,
    val cost: Double,
    val reason: String,
    val notes: String
) : Serializable
