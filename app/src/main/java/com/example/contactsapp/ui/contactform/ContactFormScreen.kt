package com.example.contactsapp.ui.contactform

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.contactsapp.data.Contact
import com.example.contactsapp.viewmodel.ContactViewModel
import com.example.contactsapp.viewmodel.ContactViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactFormScreen(
    navController: NavController,
    contactId: Long?,
    viewModel: ContactViewModel = viewModel(
        factory = ContactViewModelFactory(
            androidx.compose.ui.platform.LocalContext.current.applicationContext
                .let { it as com.example.contactsapp.ContactsApplication }
                .repository
        )
    )
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    // Load contact data if editing
    LaunchedEffect(contactId) {
        if (contactId != null && contactId > 0) {
            val contacts = viewModel.contacts.value
            val contact = contacts.find { it.id == contactId }
            contact?.let {
                firstName = it.firstName
                lastName = it.lastName
                phoneNumber = it.phoneNumber
                email = it.email ?: ""
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(if (contactId == null) "Add Contact" else "Edit Contact")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // First Name
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("First Name") }
            )

            // Last Name
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Last Name") }
            )

            // Phone Number
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Phone Number") }
            )

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Email (Optional)") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Save Button
            Button(
                onClick = {
                    if (firstName.isNotBlank() && phoneNumber.isNotBlank()) {
                        val contact = Contact(
                            id = contactId ?: 0,
                            firstName = firstName.trim(),
                            lastName = lastName.trim(),
                            phoneNumber = phoneNumber.trim(),
                            email = email.trim().takeIf { it.isNotEmpty() }
                        )

                        if (contactId == null) {
                            viewModel.addContact(contact)
                        } else {
                            viewModel.updateContact(contact)
                        }
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Contact")
            }
        }
    }
}