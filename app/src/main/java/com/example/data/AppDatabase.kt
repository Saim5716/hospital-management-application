package com.example.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {
    @Query("SELECT * FROM patients WHERE id = :id")
    fun getPatientById(id: Int): Flow<Patient?>

    @Query("SELECT * FROM patients ORDER BY name ASC")
    fun getAllPatients(): Flow<List<Patient>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPatient(patient: Patient): Long

    @Update
    suspend fun updatePatient(patient: Patient)
}

@Dao
interface DoctorDao {
    @Query("SELECT * FROM doctors WHERE id = :id")
    fun getDoctorById(id: Int): Flow<Doctor?>

    @Query("SELECT * FROM doctors ORDER BY name ASC")
    fun getAllDoctors(): Flow<List<Doctor>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDoctor(doctor: Doctor)

    @Query("SELECT COUNT(*) FROM doctors")
    suspend fun getDoctorCount(): Int
}

@Dao
interface AppointmentDao {
    @Query("SELECT * FROM appointments ORDER BY id DESC")
    fun getAllAppointments(): Flow<List<Appointment>>

    @Query("SELECT * FROM appointments WHERE patientId = :patientId ORDER BY id DESC")
    fun getAppointmentsForPatient(patientId: Int): Flow<List<Appointment>>

    @Query("SELECT * FROM appointments WHERE doctorId = :doctorId ORDER BY id DESC")
    fun getAppointmentsForDoctor(doctorId: Int): Flow<List<Appointment>>

    @Query("SELECT * FROM appointments WHERE doctorId = :doctorId AND status = 'PENDING' ORDER BY id DESC")
    fun getPendingAppointmentsForDoctor(doctorId: Int): Flow<List<Appointment>>

    @Query("SELECT * FROM appointments WHERE doctorId = :doctorId AND date = :date ORDER BY id DESC")
    fun getTodayAppointmentsForDoctor(doctorId: Int, date: String): Flow<List<Appointment>>

    @Query("SELECT * FROM appointments WHERE doctorId = :doctorId AND status = 'COMPLETED' ORDER BY id DESC")
    fun getCompletedAppointmentsForDoctor(doctorId: Int): Flow<List<Appointment>>

    @Query("SELECT * FROM appointments WHERE status = 'COMPLETED' ORDER BY id DESC")
    fun getAllCompletedAppointments(): Flow<List<Appointment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointment(appointment: Appointment)

    @Update
    suspend fun updateAppointment(appointment: Appointment)

    @Query("SELECT * FROM appointments WHERE id = :id")
    suspend fun getAppointmentById(id: Int): Appointment?
}

@Database(entities = [Patient::class, Doctor::class, Appointment::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun patientDao(): PatientDao
    abstract fun doctorDao(): DoctorDao
    abstract fun appointmentDao(): AppointmentDao
}
