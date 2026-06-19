package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patients")
data class Patient(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val phone: String,
    val email: String,
    val age: Int,
    val gender: String,
    val bloodGroup: String
)

@Entity(tableName = "doctors")
data class Doctor(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val specialty: String,
    val phone: String,
    val email: String,
    val availableTimings: String,
    val fees: Double
)

@Entity(tableName = "appointments")
data class Appointment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val patientId: Int,
    val patientName: String,
    val doctorId: Int,
    val doctorName: String,
    val date: String, // e.g. "2026-06-19"
    val timeSlot: String,
    val symptoms: String,
    val status: String = "PENDING", // PENDING, ACCEPTED, REJECTED, COMPLETED
    val disease: String = "",
    val prescription: String = "",
    val progress: String = "",
    val billAmount: Double = 0.0,
    val billStatus: String = "NONE" // NONE, UNPAID, PAID
)
