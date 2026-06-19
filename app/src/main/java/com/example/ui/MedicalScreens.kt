package com.example.ui

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.*

@Composable
fun MedicalManagementApp(viewModel: MedicalViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        AnimatedContent(
            targetState = currentScreen,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            label = "ScreenTransition"
        ) { screen ->
            when (screen) {
                is AppScreen.Welcome -> WelcomeScreen(
                    onChoosePatient = { viewModel.navigateTo(AppScreen.PatientLogin) },
                    onChooseDoctor = { viewModel.navigateTo(AppScreen.DoctorSelection) }
                )
                is AppScreen.PatientLogin -> PatientLoginScreen(
                    viewModel = viewModel,
                    onNavigateBack = { viewModel.navigateBack() },
                    onNavigateToSignup = { viewModel.navigateTo(AppScreen.PatientSignup) }
                )
                is AppScreen.PatientSignup -> PatientSignupScreen(
                    viewModel = viewModel,
                    onNavigateBack = { viewModel.navigateBack() }
                )
                is AppScreen.PatientDashboard -> PatientDashboardScreen(
                    viewModel = viewModel
                )
                is AppScreen.DoctorSelection -> DoctorSelectionScreen(
                    viewModel = viewModel,
                    onNavigateBack = { viewModel.navigateBack() }
                )
                is AppScreen.DoctorDashboard -> DoctorDashboardScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}

// ==========================================
// 1. WELCOME SCREEN
// ==========================================
@Composable
fun WelcomeScreen(
    onChoosePatient: () -> Unit,
    onChooseDoctor: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Decorative Hero Logo Spot
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.MedicalServices,
                contentDescription = "Medical Care",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(52.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "شفا کئیر • Sehat Care",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.testTag("app_welcome_title")
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Modern Medical Management Portal\nIslamic Republic of Pakistan",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Role option 1: Patient Panel
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onChoosePatient() }
                .testTag("welcome_patient_card"),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Patient Mode",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Patient Panel (مریض کا لاگ ان)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Book appointments, view treatment prescriptions & pay bills",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Go",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Role option 2: Doctor Panel
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onChooseDoctor() }
                .testTag("welcome_doctor_card"),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Healing,
                        contentDescription = "Doctor Mode",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Doctor Panel (ڈاکٹر کا لاگ ان)",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                    Text(
                        text = "Manage incoming bookings, consult patients, diagnose & issue digital bills",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                }
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Go",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Safe • Secure • Reliable Healthcare Systems",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
        )
    }
}

// ==========================================
// 2. PATIENT LOGIN SCREEN
// ==========================================
@Composable
fun PatientLoginScreen(
    viewModel: MedicalViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToSignup: () -> Unit
) {
    val patients by viewModel.allPatients.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            OptInTopAppBar(
                title = "Select Patient Profile",
                onBackClick = onNavigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to Patient Services",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Select an existing clinical file or create a brand new account below.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (patients.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(patients) { patient ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.loginWithPatient(patient.id) }
                                .testTag("patient_profile_card_${patient.id}"),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = patient.name.take(2).uppercase(),
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontSize = 14.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = patient.name,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Age: ${patient.age}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                            text = "Blood Group: ${patient.bloodGroup}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                                Icon(
                                    imageVector = Icons.Default.Login,
                                    contentDescription = "Select Profile",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onNavigateToSignup,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("create_patient_button"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(imageVector = Icons.Default.PersonAdd, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Create Patient Profile (نیا مریض بنائیں)")
            }
        }
    }
}

// ==========================================
// 3. PATIENT SIGNUP SCREEN
// ==========================================
@Composable
fun PatientSignupScreen(
    viewModel: MedicalViewModel,
    onNavigateBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Male") }
    var bloodGroup by remember { mutableStateOf("O+") }

    val genders = listOf("Male", "Female", "Other")
    val bloodGroups = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")

    var genderExpanded by remember { mutableStateOf(false) }
    var bloodExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            OptInTopAppBar(
                title = "Create New Patient File",
                onBackClick = onNavigateBack
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Medical Profile Details",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Please enter accurate physiological and contact parameters.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Patient Full Name") },
                    placeholder = { Text("e.g. Sajid Khan") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("signup_name_input"),
                    singleLine = true
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = age,
                        onValueChange = { if (it.all { char -> char.isDigit() }) age = it },
                        label = { Text("Age") },
                        placeholder = { Text("e.g. 35") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("signup_age_input"),
                        singleLine = true
                    )

                    // Gender Selection
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = gender,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Gender") },
                            trailingIcon = {
                                IconButton(onClick = { genderExpanded = true }) {
                                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("signup_gender_input")
                        )
                        DropdownMenu(
                            expanded = genderExpanded,
                            onDismissRequest = { genderExpanded = false }
                        ) {
                            genders.forEach { g ->
                                DropdownMenuItem(
                                    text = { Text(g) },
                                    onClick = {
                                        gender = g
                                        genderExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Phone Number") },
                        placeholder = { Text("e.g. +923001234567") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                        modifier = Modifier
                            .weight(1.3f)
                            .testTag("signup_phone_input"),
                        singleLine = true
                    )

                    // Blood Group Selection
                    Box(modifier = Modifier.weight(0.7f)) {
                        OutlinedTextField(
                            value = bloodGroup,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Blood") },
                            trailingIcon = {
                                IconButton(onClick = { bloodExpanded = true }) {
                                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("signup_blood_input")
                        )
                        DropdownMenu(
                            expanded = bloodExpanded,
                            onDismissRequest = { bloodExpanded = false }
                        ) {
                            bloodGroups.forEach { bg ->
                                DropdownMenuItem(
                                    text = { Text(bg) },
                                    onClick = {
                                        bloodGroup = bg
                                        bloodExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address") },
                    placeholder = { Text("e.g. client@shifa.org") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("signup_email_input"),
                    singleLine = true
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (name.isNotBlank() && age.isNotBlank() && phone.isNotBlank()) {
                            viewModel.createAndLoginPatient(
                                Patient(
                                    name = name,
                                    phone = phone,
                                    email = email.ifBlank { "info@shifa.org" },
                                    age = age.toIntOrNull() ?: 25,
                                    gender = gender,
                                    bloodGroup = bloodGroup
                                )
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("signup_submit_button"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Finalize Profile Registration & Log In", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// ==========================================
// 4. PATIENT DASHBOARD SCREEN
// ==========================================
@Composable
fun PatientDashboardScreen(viewModel: MedicalViewModel) {
    val patientState by viewModel.currentPatient.collectAsStateWithLifecycle()
    val appointments by viewModel.patientAppointments.collectAsStateWithLifecycle()
    val doctors by viewModel.allDoctors.collectAsStateWithLifecycle()

    var selectedTab by remember { mutableStateOf(0) } // 0: Profile, 1: Book, 2: History (Treatments & Bills)

    if (patientState == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val patient = patientState!!

    Scaffold(
        topBar = {
            OptInMediumTopAppBar(
                title = "Patient: ${patient.name}",
                subtitle = "Health ID: CC-${patient.id + 1000}",
                onLogoutClick = { viewModel.logoutPatient() }
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.navigationBarsPadding()
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Outlined.AssignmentInd, contentDescription = null) },
                    label = { Text("My Profile") },
                    modifier = Modifier.testTag("patient_tab_profile")
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Outlined.EditCalendar, contentDescription = null) },
                    label = { Text("Book Appt") },
                    modifier = Modifier.testTag("patient_tab_book")
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Outlined.ReceiptLong, contentDescription = null) },
                    label = { Text("History") },
                    modifier = Modifier.testTag("patient_tab_history")
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> PatientProfileTab(patient)
                1 -> PatientBookingTab(
                    doctors = doctors,
                    appointments = appointments,
                    viewModel = viewModel
                )
                2 -> PatientHistoryTab(
                    appointments = appointments,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun PatientProfileTab(patient: Patient) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = patient.name.take(2).uppercase(),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 24.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = patient.name,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Cardholder File No: SCH-${1000 + patient.id}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        item {
            Text(
                text = "Physiological Credentials",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ProfileInfoCard(
                    label = "Age",
                    value = "${patient.age} Years",
                    icon = Icons.Outlined.Cake,
                    modifier = Modifier.weight(1f)
                )
                ProfileInfoCard(
                    label = "Gender",
                    value = patient.gender,
                    icon = Icons.Outlined.Diversity3,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ProfileInfoCard(
                    label = "Blood Group",
                    value = patient.bloodGroup,
                    icon = Icons.Outlined.WaterDrop,
                    modifier = Modifier.weight(1f),
                    valueColor = MaterialTheme.colorScheme.error
                )
                ProfileInfoCard(
                    label = "Clinic Registration",
                    value = "Verified",
                    icon = Icons.Outlined.Verified,
                    modifier = Modifier.weight(1f),
                    valueColor = MaterialTheme.colorScheme.primary
                )
            }
        }

        item {
            Text(
                text = "Contact Specification",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = CardDefaults.outlinedCardBorder(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Phone, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Mobile Number", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(patient.phone, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                        }
                    }
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Email Address", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(patient.email, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileInfoCard(
    label: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    valueColor: Color = Color.Unspecified
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = value, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = valueColor)
        }
    }
}

@Composable
fun PatientBookingTab(
    doctors: List<Doctor>,
    appointments: List<Appointment>,
    viewModel: MedicalViewModel
) {
    var selectedDocIdx by remember { mutableStateOf(-1) }
    var inputSymptoms by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("2026-06-19") }
    var selectedTimeSlot by remember { mutableStateOf("11:00 AM") }

    var doctorMenuExpanded by remember { mutableStateOf(false) }
    var slotExpanded by remember { mutableStateOf(false) }

    val timeSlots = listOf("09:30 AM", "11:00 AM", "12:30 PM", "02:30 PM", "04:00 PM", "05:30 PM", "07:00 PM")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                border = CardDefaults.outlinedCardBorder(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Book A Medical Appointment",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    // Doctor Selector Dropdown
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = if (selectedDocIdx >= 0) "${doctors[selectedDocIdx].name} (${doctors[selectedDocIdx].specialty})" else "Select Specialization / Doctor",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Consulting Practitioner") },
                            leadingIcon = { Icon(Icons.Default.Healing, contentDescription = null) },
                            trailingIcon = {
                                IconButton(onClick = { doctorMenuExpanded = true }) {
                                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("appointment_doctor_picker")
                        )
                        DropdownMenu(
                            expanded = doctorMenuExpanded,
                            onDismissRequest = { doctorMenuExpanded = false }
                        ) {
                            doctors.forEachIndexed { idx, doc ->
                                DropdownMenuItem(
                                    text = {
                                        Column {
                                            Text(doc.name, fontWeight = FontWeight.SemiBold)
                                            Text("${doc.specialty} • Fee: PKR ${doc.fees}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        }
                                    },
                                    onClick = {
                                        selectedDocIdx = idx
                                        doctorMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Date and Time Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Date picker simulated text input (autofilled today's date)
                        OutlinedTextField(
                            value = selectedDate,
                            onValueChange = { selectedDate = it },
                            label = { Text("Appointment Date") },
                            leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("appointment_date_input"),
                            singleLine = true
                        )

                        // Time Slot
                        Box(modifier = Modifier.weight(1f)) {
                            OutlinedTextField(
                                value = selectedTimeSlot,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Time Slot") },
                                trailingIcon = {
                                    IconButton(onClick = { slotExpanded = true }) {
                                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("appointment_time_picker")
                            )
                            DropdownMenu(
                                expanded = slotExpanded,
                                onDismissRequest = { slotExpanded = false }
                            ) {
                                timeSlots.forEach { slot ->
                                    DropdownMenuItem(
                                        text = { Text(slot) },
                                        onClick = {
                                            selectedTimeSlot = slot
                                            slotExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // Symptoms
                    OutlinedTextField(
                        value = inputSymptoms,
                        onValueChange = { inputSymptoms = it },
                        label = { Text("Symptoms / Reasons for Appointment") },
                        placeholder = { Text("Describe fever, pain, checkups, etc.") },
                        leadingIcon = { Icon(Icons.Default.Description, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("appointment_symptoms_input"),
                        maxLines = 3
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Request Button
                    Button(
                        onClick = {
                            if (selectedDocIdx >= 0 && inputSymptoms.isNotBlank()) {
                                val doc = doctors[selectedDocIdx]
                                viewModel.bookAppointment(
                                    doctorId = doc.id,
                                    doctorName = doc.name,
                                    date = selectedDate,
                                    slot = selectedTimeSlot,
                                    symptoms = inputSymptoms
                                )
                                inputSymptoms = ""
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("book_appointment_submit"),
                        shape = RoundedCornerShape(10.dp),
                        enabled = selectedDocIdx >= 0 && inputSymptoms.isNotBlank()
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Confirm Appointment Booking Request", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        item {
            Text(
                text = "Appointment Directives & Status Flow",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        if (appointments.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No clinical bookings listed yet. Submit a booking request above.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            items(appointments) { appt ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = appt.doctorName,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            StatusBadge(status = appt.status)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.CalendarToday, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(appt.date, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(appt.timeSlot, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = MaterialTheme.colorScheme.outlineVariant)
                        Text(
                            text = "Presented Complaints: ${appt.symptoms}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )

                        if (appt.status == "COMPLETED") {
                            Spacer(modifier = Modifier.height(8.dp))
                            Card(
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Treatment Issued & Completed. View History tab for Details.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PatientHistoryTab(
    appointments: List<Appointment>,
    viewModel: MedicalViewModel
) {
    val completedAppts = appointments.filter { it.status == "COMPLETED" }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Clinical Archives (Completed Consultations)",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Review past prescriptions, diagnosis records, and make outstanding bill payments.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
            )
        }

        if (completedAppts.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = Icons.Default.MedicalServices, contentDescription = null, modifier = Modifier.size(56.dp), tint = MaterialTheme.colorScheme.outline)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No completed medical consultations found.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            items(completedAppts) { appt ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = appt.doctorName,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )
                                Text("Consultation Date: ${appt.date}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            StatusBadge(status = "COMPLETED")
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.outlineVariant)

                        // Treatment History
                        Text("1. Treatments & Diagnostic Log", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary))
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Diagnosed Disease", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(appt.disease.ifBlank { "Not Specified" }, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Clinical Progress", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(appt.progress.ifBlank { "Steady" }, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Active Prescription Issued:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            border = CardDefaults.outlinedCardBorder()
                        ) {
                            Text(
                                text = appt.prescription.ifBlank { "Rx: Rest and drink warm fluids" },
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(12.dp),
                                fontFamily = FontFamily.Monospace,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.outlineVariant)

                        // Bills History
                        Text("2. Billing Statement & Payment", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary))
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Base + Diagnostic Charges", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("PKR ${String.format("%.2f", appt.billAmount)}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            }

                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                if (appt.billStatus == "PAID") {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color(0xFF2E7D32).copy(alpha = 0.15f))
                                            .padding(horizontal = 12.dp, vertical = 6.dp)
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Paid", color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        }
                                    }
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(MaterialTheme.colorScheme.error.copy(alpha = 0.15f))
                                            .padding(horizontal = 12.dp, vertical = 6.dp)
                                    ) {
                                        Text("Unpaid", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    }

                                    Button(
                                        onClick = { viewModel.payBill(appt.id) },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                                        contentPadding = PaddingValues(horizontal = 16.dp),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.testTag("pay_bill_btn_${appt.id}")
                                    ) {
                                        Text("Pay Bill", color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// 5. DOCTOR SELECTION SCREEN
// ==========================================
@Composable
fun DoctorSelectionScreen(
    viewModel: MedicalViewModel,
    onNavigateBack: () -> Unit
) {
    val doctors by viewModel.allDoctors.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            OptInTopAppBar(
                title = "Select Doctor Account",
                onBackClick = onNavigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            Text(
                text = "Registry of Practicing Staff",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Select your medical practitioner profile to access bookings and consultation boards.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            if (doctors.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(doctors) { doc ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.loginWithDoctor(doc.id) }
                                .testTag("doctor_profile_card_${doc.id}"),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = CardDefaults.outlinedCardBorder(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.MedicalServices,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = doc.name,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Text(
                                        text = doc.specialty,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = "Base fee: PKR ${doc.fees}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = "LogIn",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// 6. DOCTOR DASHBOARD SCREEN
// ==========================================
@Composable
fun DoctorDashboardScreen(viewModel: MedicalViewModel) {
    val doctorState by viewModel.currentDoctor.collectAsStateWithLifecycle()
    val pendingAppts by viewModel.doctorPendingAppointments.collectAsStateWithLifecycle()
    val todayAppts by viewModel.doctorTodayAppointments.collectAsStateWithLifecycle()
    val treatedPatients by viewModel.doctorTreatedPatients.collectAsStateWithLifecycle()

    var selectedTab by remember { mutableStateOf(0) } // 0: Profile, 1: Pending, 2: Today / Consult, 3: PatientHistory
    var activeConsultAppointment by remember { mutableStateOf<Appointment?>(null) }

    if (doctorState == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val doctor = doctorState!!

    Scaffold(
        topBar = {
            OptInMediumTopAppBar(
                title = doctor.name,
                subtitle = "Practitioner: ${doctor.specialty}",
                onLogoutClick = { viewModel.logoutDoctor() }
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.navigationBarsPadding()
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.ContactPage, contentDescription = null) },
                    label = { Text("Profile") },
                    modifier = Modifier.testTag("doctor_tab_profile")
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { 
                        BadgedBox(badge = { if (pendingAppts.isNotEmpty()) Badge { Text("${pendingAppts.size}") } }) {
                            Icon(Icons.Default.HourglassEmpty, contentDescription = null)
                        }
                    },
                    label = { Text("Pending") },
                    modifier = Modifier.testTag("doctor_tab_pending")
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = {
                        BadgedBox(badge = { if (todayAppts.isNotEmpty()) Badge { Text("${todayAppts.size}") } }) {
                            Icon(Icons.Default.CalendarViewDay, contentDescription = null)
                        }
                    },
                    label = { Text("Today Appts") },
                    modifier = Modifier.testTag("doctor_tab_today")
                )
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = { Icon(Icons.Default.History, contentDescription = null) },
                    label = { Text("History logs") },
                    modifier = Modifier.testTag("doctor_tab_history")
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> DoctorProfileTab(doctor)
                1 -> DoctorPendingTab(
                    pendingAppointments = pendingAppts,
                    onAccept = { id -> viewModel.updateAppointmentStatus(id, "ACCEPTED") },
                    onReject = { id -> viewModel.updateAppointmentStatus(id, "REJECTED") }
                )
                2 -> DoctorTodayTab(
                    todayAppointments = todayAppts,
                    onStartConsultation = { appt -> activeConsultAppointment = appt },
                    onAccept = { id -> viewModel.updateAppointmentStatus(id, "ACCEPTED") },
                    onReject = { id -> viewModel.updateAppointmentStatus(id, "REJECTED") }
                )
                3 -> DoctorPatientHistoryTab(
                    treatedPatients = treatedPatients
                )
            }
        }

        // Consult Dialog (For HistoryUpdate and GeneratBill)
        activeConsultAppointment?.let { appt ->
            ConsultationDialog(
                appointment = appt,
                baseFees = doctor.fees,
                onDismiss = { activeConsultAppointment = null },
                onSubmitConsultation = { disease, prescription, progress, totalBill ->
                    viewModel.finalizeConsultationAndBill(
                        appointmentId = appt.id,
                        disease = disease,
                        prescription = prescription,
                        progress = progress,
                        billAmount = totalBill
                    )
                    activeConsultAppointment = null
                }
            )
        }
    }
}

@Composable
fun DoctorProfileTab(doctor: Doctor) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MedicalServices,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = doctor.name,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Senior ${doctor.specialty}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        item {
            Text(
                text = "Professional Profile Attributes",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ProfileInfoCard(
                    label = "Standard Fee",
                    value = "PKR ${doctor.fees}",
                    icon = Icons.Outlined.Paid,
                    modifier = Modifier.weight(1.3f),
                    valueColor = MaterialTheme.colorScheme.primary
                )
                ProfileInfoCard(
                    label = "OPD Timings",
                    value = doctor.availableTimings,
                    icon = Icons.Outlined.AccessTime,
                    modifier = Modifier.weight(1.7f)
                )
            }
        }

        item {
            Text(
                text = "Secure Hospital Contact Access",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = CardDefaults.outlinedCardBorder(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Phone, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Direct Contact Call sign", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(doctor.phone, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                        }
                    }
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Official Hospital Email", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(doctor.email, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DoctorPendingTab(
    pendingAppointments: List<Appointment>,
    onAccept: (Int) -> Unit,
    onReject: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Pending Registrations (${pendingAppointments.size})",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Approve or decline clinical consultation requests booked at your facility.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
            )
        }

        if (pendingAppointments.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = Icons.Default.TaskAlt, contentDescription = null, modifier = Modifier.size(56.dp), tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "All clean! No pending appointments require approval.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            items(pendingAppointments) { appt ->
                Card(
                    modifier = Modifier.fillMaxWidth().testTag("pending_appointment_card_${appt.id}"),
                    shape = RoundedCornerShape(16.dp),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = appt.patientName,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            StatusBadge(status = "PENDING")
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.CalendarToday, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(appt.date, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(appt.timeSlot, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.outlineVariant)

                        Text(
                            text = "Presented Symptoms:",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = appt.symptoms,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedButton(
                                onClick = { onReject(appt.id) },
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("reject_btn_${appt.id}")
                            ) {
                                Icon(Icons.Default.Clear, contentDescription = "Reject", modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Reject")
                            }

                            Button(
                                onClick = { onAccept(appt.id) },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("accept_btn_${appt.id}")
                            ) {
                                Icon(Icons.Default.Check, contentDescription = "Accept", modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Accept")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DoctorTodayTab(
    todayAppointments: List<Appointment>,
    onStartConsultation: (Appointment) -> Unit,
    onAccept: (Int) -> Unit,
    onReject: (Int) -> Unit
) {
    // Current day filter from environment: "2026-06-19"
    val todayDate = "2026-06-19"

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Today's Clinic Board (آج کے اپائنٹمنٹس)",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Active appointments registered for clinical cycle on date $todayDate.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
            )
        }

        if (todayAppointments.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = Icons.Default.EventNote, contentDescription = null, modifier = Modifier.size(56.dp), tint = MaterialTheme.colorScheme.outline)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No appointments booked for today yet.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            items(todayAppointments) { appt ->
                Card(
                    modifier = Modifier.fillMaxWidth().testTag("today_appointment_card_${appt.id}"),
                    shape = RoundedCornerShape(16.dp),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = appt.patientName,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            StatusBadge(status = appt.status)
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(appt.timeSlot, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Coronavirus, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(appt.symptoms.take(25), maxLines = 1, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.outlineVariant)

                        if (appt.status == "PENDING") {
                            Text("Pending request decision required:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                TextButton(
                                    onClick = { onReject(appt.id) },
                                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                                ) {
                                    Text("Reject Appointment")
                                }
                                Button(
                                    onClick = { onAccept(appt.id) },
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("Accept Appointment")
                                }
                            }
                        } else if (appt.status == "ACCEPTED") {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Awaiting Diagnosis Cycle", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                                Button(
                                    onClick = { onStartConsultation(appt) },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.testTag("start_consult_btn_${appt.id}")
                                ) {
                                    Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Start Consultation", fontWeight = FontWeight.Bold)
                                }
                            }
                        } else if (appt.status == "COMPLETED") {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF2E7D32).copy(alpha = 0.08f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Consultation Finalized & Bill Dispatched", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32), style = MaterialTheme.typography.bodySmall)
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text("Diagnosed: ${appt.disease}", style = MaterialTheme.typography.bodySmall)
                                    Text("Prescription: ${appt.prescription}", style = MaterialTheme.typography.bodySmall)
                                }
                            }
                        } else {
                            Text("This appointment was rejected/cancelled", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DoctorPatientHistoryTab(
    treatedPatients: List<Appointment>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Treated Patients History (مریضوں کی ہسٹری)",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Logs of all patients successfully treated under your clinical license.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
            )
        }

        if (treatedPatients.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = Icons.Default.FolderOpen, contentDescription = null, modifier = Modifier.size(56.dp), tint = MaterialTheme.colorScheme.outline)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No treated patient history recorded.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            items(treatedPatients) { appt ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = appt.patientName,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )
                                Text("Treatment Cycle Completed: ${appt.date}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text("ID: #${appt.patientId}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            }
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.outlineVariant)

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Diagnosis", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(appt.disease, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Patient Progress", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(appt.progress, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Active Rx / Prescription:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            shape = RoundedCornerShape(8.dp),
                            border = CardDefaults.outlinedCardBorder()
                        ) {
                            Text(
                                text = appt.prescription,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(12.dp),
                                fontFamily = FontFamily.Monospace,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Billed Amount: PKR ${appt.billAmount}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (appt.billStatus == "PAID") Color(0xFF2E7D32).copy(alpha = 0.12f) else MaterialTheme.colorScheme.error.copy(alpha = 0.12f))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = if (appt.billStatus == "PAID") "Paid" else "Unpaid Bill",
                                    color = if (appt.billStatus == "PAID") Color(0xFF2E7D32) else MaterialTheme.colorScheme.error,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// 7. DIALOG COMPONENT (HEALTH HISTORY CREATION + BILL GENERATION)
// ==========================================
@Composable
fun ConsultationDialog(
    appointment: Appointment,
    baseFees: Double,
    onDismiss: () -> Unit,
    onSubmitConsultation: (String, String, String, Double) -> Unit
) {
    var disease by remember { mutableStateOf("") }
    var prescription by remember { mutableStateOf("") }
    var progress by remember { mutableStateOf("Stable recovery") }
    var additionalCharges by remember { mutableStateOf("") }

    val computedTotal = baseFees + (additionalCharges.toDoubleOrNull() ?: 0.0)

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Text(
                        text = "Clinical Cycle & Consultation",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Patient: ${appointment.patientName} (Age: ${1000 + appointment.patientId % 50 /* just mock/info */})",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    HorizontalDivider()
                }

                item {
                    OutlinedTextField(
                        value = disease,
                        onValueChange = { disease = it },
                        label = { Text("Diagnosed Disease / Pathology") },
                        placeholder = { Text("e.g. Acute Viral Bronchitis") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("dialog_disease_input"),
                        singleLine = true
                    )
                }

                item {
                    OutlinedTextField(
                        value = prescription,
                        onValueChange = { prescription = it },
                        label = { Text("Prescription (Rx Medicine & Dosage)") },
                        placeholder = { Text("e.g. Tab. Azithromycin 500mg, Syp. Cofcol 2 tsp") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("dialog_prescription_input"),
                        maxLines = 4
                    )
                }

                item {
                    OutlinedTextField(
                        value = progress,
                        onValueChange = { progress = it },
                        label = { Text("Physiological Progress Comments") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("dialog_progress_input"),
                        singleLine = true
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = additionalCharges,
                            onValueChange = { if (it.all { char -> char.isDigit() }) additionalCharges = it },
                            label = { Text("Medication Cost (PKR)") },
                            placeholder = { Text("e.g. 350") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("dialog_additional_input"),
                            singleLine = true
                        )

                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("Total Bill (PKR)", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSecondaryContainer)
                                Text("PKR $computedTotal", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.secondary)
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = onDismiss) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                if (disease.isNotBlank() && prescription.isNotBlank()) {
                                    onSubmitConsultation(disease, prescription, progress, computedTotal)
                                }
                            },
                            enabled = disease.isNotBlank() && prescription.isNotBlank(),
                            modifier = Modifier.testTag("dialog_submit_btn")
                        ) {
                            Text("Deliver Bill & Complete", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// DECORATIVE AND REUSABLE MINI COMPONENTS
// ==========================================
@Composable
fun StatusBadge(status: String) {
    val containerColor = when (status) {
        "PENDING" -> MaterialTheme.colorScheme.secondaryContainer
        "ACCEPTED" -> MaterialTheme.colorScheme.primaryContainer
        "COMPLETED" -> Color(0xFFC8E6C9)
        else -> MaterialTheme.colorScheme.errorContainer
    }
    val contentColor = when (status) {
        "PENDING" -> MaterialTheme.colorScheme.onSecondaryContainer
        "ACCEPTED" -> MaterialTheme.colorScheme.onPrimaryContainer
        "COMPLETED" -> Color(0xFF1B5E20)
        else -> MaterialTheme.colorScheme.onErrorContainer
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(containerColor)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = status,
            color = contentColor,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptInTopAppBar(title: String, onBackClick: () -> Unit) {
    TopAppBar(
        title = { Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptInMediumTopAppBar(title: String, subtitle: String, onLogoutClick: () -> Unit) {
    TopAppBar(
        title = {
            Column {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(subtitle, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
        actions = {
            IconButton(onClick = onLogoutClick, modifier = Modifier.testTag("logout_button")) {
                Icon(imageVector = Icons.Default.Logout, contentDescription = "Log Out")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}
