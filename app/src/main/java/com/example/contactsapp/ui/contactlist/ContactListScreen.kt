package com.example.contactsapp.ui.contactlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.contactsapp.ui.components.ContactItem
import com.example.contactsapp.viewmodel.ContactViewModel
import com.example.contactsapp.viewmodel.ContactViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(
    onAddContact: () -> Unit,
    onEditContact: (Long) -> Unit,
    viewModel: ContactViewModel = viewModel(
        factory = ContactViewModelFactory(
            androidx.compose.ui.platform.LocalContext.current.applicationContext
                .let { it as com.example.contactsapp.ContactsApplication }
                .repository
        )
    )
) {
    val contacts by viewModel.contacts.collectAsState()
    val searchText by viewModel.searchText.collectAsState()
    var contactToDelete by remember { mutableStateOf<com.example.contactsapp.data.Contact?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contacts") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddContact
            ) {
                Icon(Icons.Default.Add, "Add Contact")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchText,
                onValueChange = { viewModel.searchContacts(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search contacts...") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Contact List
            if (contacts.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text(
                        text = if (searchText.isNotEmpty())
                            "No contacts found"
                        else
                            "No contacts yet",
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(contacts) { contact ->
                        ContactItem(
                            contact = contact,
                            onEdit = { onEditContact(contact.id) },
                            onDelete = { contactToDelete = contact }
                        )
                    }
                }
            }
        }
    }

    // Delete Confirmation Dialog
    if (contactToDelete != null) {
        AlertDialog(
            onDismissRequest = { contactToDelete = null },
            title = { Text("Delete Contact") },
            text = { Text("Are you sure you want to delete this contact?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        contactToDelete?.let { viewModel.deleteContact(it) }
                        contactToDelete = null
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { contactToDelete = null }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}