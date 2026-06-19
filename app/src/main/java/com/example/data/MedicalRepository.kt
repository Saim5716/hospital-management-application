package com.example.data

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class MedicalRepository(private val db: AppDatabase) {

    val patientDao = db.patientDao()
    val doctorDao = db.doctorDao()
    val appointmentDao = db.appointmentDao()

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "medical_management_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }

    // Patient Methods
    fun getPatientById(id: Int): Flow<Patient?> = patientDao.getPatientById(id)
    fun getAllPatients(): Flow<List<Patient>> = patientDao.getAllPatients()
    suspend fun registerPatient(patient: Patient): Int {
        return patientDao.insertPatient(patient).toInt()
    }
    suspend fun updatePatient(patient: Patient) = patientDao.updatePatient(patient)

    // Doctor Methods
    fun getDoctorById(id: Int): Flow<Doctor?> = doctorDao.getDoctorById(id)
    fun getAllDoctors(): Flow<List<Doctor>> = doctorDao.getAllDoctors()
    suspend fun insertDoctor(doctor: Doctor) = doctorDao.insertDoctor(doctor)

    // Prepopulate Doctors
    suspend fun ensureDoctorsPrepopulated() {
        if (doctorDao.getDoctorCount() == 0) {
            val list = listOf(
                Doctor(
                    name = "Dr. Tariq Mahmood",
                    specialty = "Cardiologist",
                    phone = "+92-300-1234567",
                    email = "tariq.mahmood@shifacare.com",
                    availableTimings = "10:00 AM - 01:00 PM",
                    fees = 1500.0
                ),
                Doctor(
                    name = "Dr. Aisha Khan",
                    specialty = "Pediatrician",
                    phone = "+92-321-7654321",
                    email = "aisha.khan@shifacare.com",
                    availableTimings = "02:00 PM - 05:00 PM",
                    fees = 1200.0
                ),
                Doctor(
                    name = "Dr. Sajid Rehman",
                    specialty = "Dermatologist",
                    phone = "+92-333-1112223",
                    email = "sajid.rehman@shifacare.com",
                    availableTimings = "06:00 PM - 09:00 PM",
                    fees = 1000.0
                ),
                Doctor(
                    name = "Dr. Fatima Zainab",
                    specialty = "Gynecologist",
                    phone = "+92-345-9998887",
                    email = "fatima.zainab@shifacare.com",
                    availableTimings = "11:00 AM - 03:00 PM",
                    fees = 1600.0
                ),
                Doctor(
                    name = "Dr. Bilal Siddiqui",
                    specialty = "General Physician",
                    phone = "+92-312-3334445",
                    email = "bilal.siddiqui@shifacare.com",
                    availableTimings = "09:00 AM - 12:00 PM",
                    fees = 800.0
                )
            )
            for (doc in list) {
                doctorDao.insertDoctor(doc)
            }
        }
    }

    // Appointment Methods
    fun getAllAppointments(): Flow<List<Appointment>> = appointmentDao.getAllAppointments()
    fun getAppointmentsForPatient(patientId: Int): Flow<List<Appointment>> = appointmentDao.getAppointmentsForPatient(patientId)
    fun getAppointmentsForDoctor(doctorId: Int): Flow<List<Appointment>> = appointmentDao.getAppointmentsForDoctor(doctorId)
    fun getPendingAppointmentsForDoctor(doctorId: Int): Flow<List<Appointment>> = appointmentDao.getPendingAppointmentsForDoctor(doctorId)
    fun getTodayAppointmentsForDoctor(doctorId: Int, date: String): Flow<List<Appointment>> = appointmentDao.getTodayAppointmentsForDoctor(doctorId, date)
    fun getCompletedAppointmentsForDoctor(doctorId: Int): Flow<List<Appointment>> = appointmentDao.getCompletedAppointmentsForDoctor(doctorId)
    fun getAllCompletedAppointments(): Flow<List<Appointment>> = appointmentDao.getAllCompletedAppointments()

    suspend fun bookAppointment(appointment: Appointment) = appointmentDao.insertAppointment(appointment)
    suspend fun updateAppointment(appointment: Appointment) = appointmentDao.updateAppointment(appointment)
    suspend fun getAppointmentById(id: Int): Appointment? = appointmentDao.getAppointmentById(id)
}
