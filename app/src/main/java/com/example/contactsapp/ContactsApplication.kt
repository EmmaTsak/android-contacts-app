package com.example.contactsapp

import android.app.Application
import com.example.contactsapp.data.ContactDatabase
import com.example.contactsapp.repository.ContactRepository

class ContactsApplication : Application() {

    val database by lazy { ContactDatabase.getDatabase(this) }
    val repository by lazy { ContactRepository(database.contactDao()) }
}