package com.example.contactsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModelProvider
import com.example.contactsapp.data.Contact
import com.example.contactsapp.repository.ContactRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ContactViewModel(
    private val repository: ContactRepository
) : ViewModel() {

    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts: StateFlow<List<Contact>> = _contacts.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    init {
        loadContacts()
    }

    fun loadContacts() {
        viewModelScope.launch {
            repository.getAllContacts().collect { contactsList ->
                _contacts.value = contactsList
            }
        }
    }

    fun searchContacts(query: String) {
        _searchText.value = query
        viewModelScope.launch {
            if (query.isNotEmpty()) {
                repository.searchContacts(query).collect { contactsList ->
                    _contacts.value = contactsList
                }
            } else {
                loadContacts()
            }
        }
    }

    fun addContact(contact: Contact) {
        viewModelScope.launch {
            repository.insertContact(contact)
            loadContacts()
        }
    }

    fun updateContact(contact: Contact) {
        viewModelScope.launch {
            repository.updateContact(contact)
            loadContacts()
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            repository.deleteContact(contact)
            loadContacts()
        }
    }
}

class ContactViewModelFactory(
    private val repository: ContactRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            return ContactViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}