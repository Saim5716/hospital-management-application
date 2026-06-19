package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed interface AppScreen {
    object Welcome : AppScreen
    object PatientLogin : AppScreen
    object PatientSignup : AppScreen
    object PatientDashboard : AppScreen
    object DoctorSelection : AppScreen
    object DoctorDashboard : AppScreen
}

class MedicalViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MedicalRepository
    
    // Default today's date as provided in environment
    val todayDateString = "2026-06-19"

    // Backstack history for simple navigation
    private val screenHistory = mutableListOf<AppScreen>()

    private val _currentScreen = MutableStateFlow<AppScreen>(AppScreen.Welcome)
    val currentScreen: StateFlow<AppScreen> = _currentScreen

    // App Data Flows
    val allPatients: StateFlow<List<Patient>>
    val allDoctors: StateFlow<List<Doctor>>

    // Active Sessions
    private val _selectedPatientId = MutableStateFlow<Int?>(null)
    val currentPatient = _selectedPatientId.flatMapLatest { id ->
        if (id == null) flowOf(null) else repository.getPatientById(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _selectedDoctorId = MutableStateFlow<Int?>(null)
    val currentDoctor = _selectedDoctorId.flatMapLatest { id ->
        if (id == null) flowOf(null) else repository.getDoctorById(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Appointments filtered for current logged-in role
    val patientAppointments = _selectedPatientId.flatMapLatest { pid ->
        if (pid == null) flowOf(emptyList()) else repository.getAppointmentsForPatient(pid)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val doctorPendingAppointments = _selectedDoctorId.flatMapLatest { did ->
        if (did == null) flowOf(emptyList()) else repository.getPendingAppointmentsForDoctor(did)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val doctorTodayAppointments = _selectedDoctorId.flatMapLatest { did ->
        if (did == null) flowOf(emptyList()) else repository.getTodayAppointmentsForDoctor(did, todayDateString)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val doctorTreatedPatients = _selectedDoctorId.flatMapLatest { did ->
        if (did == null) flowOf(emptyList()) else repository.getCompletedAppointmentsForDoctor(did)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        val database = MedicalRepository.getDatabase(application)
        repository = MedicalRepository(database)

        allPatients = repository.getAllPatients()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        allDoctors = repository.getAllDoctors()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        // Ensure dummy data exists on launch
        viewModelScope.launch {
            repository.ensureDoctorsPrepopulated()
            ensurePatientsPrepopulated()
        }
    }

    private suspend fun ensurePatientsPrepopulated() {
        allPatients.first { list ->
            if (list.isEmpty()) {
                repository.registerPatient(
                    Patient(
                        name = "Muhammad Ali",
                        phone = "+92-300-9876543",
                        email = "muhammad.ali@gmail.com",
                        age = 32,
                        gender = "Male",
                        bloodGroup = "O+"
                    )
                )
                repository.registerPatient(
                    Patient(
                        name = "Zainab Fatima",
                        phone = "+92-331-4567890",
                        email = "zainab.f@gmail.com",
                        age = 26,
                        gender = "Female",
                        bloodGroup = "B+"
                    )
                )
            }
            true
        }
    }

    // Navigation Methods
    fun navigateTo(screen: AppScreen) {
        screenHistory.add(_currentScreen.value)
        _currentScreen.value = screen
    }

    fun navigateBack() {
        if (screenHistory.isNotEmpty()) {
            _currentScreen.value = screenHistory.removeAt(screenHistory.size - 1)
        } else {
            _currentScreen.value = AppScreen.Welcome
        }
    }

    fun resetNavigation() {
        screenHistory.clear()
        _currentScreen.value = AppScreen.Welcome
    }

    // Patient Actions
    fun createAndLoginPatient(patient: Patient) {
        viewModelScope.launch {
            val fileId = repository.registerPatient(patient)
            _selectedPatientId.value = fileId
            _currentScreen.value = AppScreen.PatientDashboard
        }
    }

    fun loginWithPatient(patientId: Int) {
        _selectedPatientId.value = patientId
        navigateTo(AppScreen.PatientDashboard)
    }

    fun logoutPatient() {
        _selectedPatientId.value = null
        navigateTo(AppScreen.Welcome)
    }

    fun bookAppointment(doctorId: Int, doctorName: String, date: String, slot: String, symptoms: String) {
        val patient = currentPatient.value ?: return
        viewModelScope.launch {
            repository.bookAppointment(
                Appointment(
                    patientId = patient.id,
                    patientName = patient.name,
                    doctorId = doctorId,
                    doctorName = doctorName,
                    date = date,
                    timeSlot = slot,
                    symptoms = symptoms,
                    status = "PENDING"
                )
            )
        }
    }

    fun payBill(appointmentId: Int) {
        viewModelScope.launch {
            val appointment = repository.getAppointmentById(appointmentId)
            if (appointment != null) {
                repository.updateAppointment(appointment.copy(billStatus = "PAID"))
            }
        }
    }

    // Doctor Actions
    fun loginWithDoctor(doctorId: Int) {
        _selectedDoctorId.value = doctorId
        navigateTo(AppScreen.DoctorDashboard)
    }

    fun logoutDoctor() {
        _selectedDoctorId.value = null
        navigateTo(AppScreen.Welcome)
    }

    fun updateAppointmentStatus(appointmentId: Int, newStatus: String) {
        viewModelScope.launch {
            val appointment = repository.getAppointmentById(appointmentId)
            if (appointment != null) {
                var updated = appointment.copy(status = newStatus)
                // If rejecting or setting pending, clear treatment and bills
                if (newStatus == "REJECTED") {
                    updated = updated.copy(billStatus = "NONE")
                }
                repository.updateAppointment(updated)
            }
        }
    }

    fun finalizeConsultationAndBill(
        appointmentId: Int,
        disease: String,
        prescription: String,
        progress: String,
        billAmount: Double
    ) {
        viewModelScope.launch {
            val appointment = repository.getAppointmentById(appointmentId)
            if (appointment != null) {
                repository.updateAppointment(
                    appointment.copy(
                        status = "COMPLETED",
                        disease = disease,
                        prescription = prescription,
                        progress = progress,
                        billAmount = billAmount,
                        billStatus = "UNPAID"
                    )
                )
            }
        }
    }
}
