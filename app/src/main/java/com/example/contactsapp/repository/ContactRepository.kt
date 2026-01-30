package com.example.contactsapp.repository

import com.example.contactsapp.data.Contact
import com.example.contactsapp.data.ContactDao
import kotlinx.coroutines.flow.Flow

class ContactRepository(private val contactDao: ContactDao) {

    fun getAllContacts(): Flow<List<Contact>> = contactDao.getAllContacts()

    fun getContactById(contactId: Long): Flow<Contact?> = contactDao.getContactById(contactId)

    fun searchContacts(query: String): Flow<List<Contact>> = contactDao.searchContacts(query)

    suspend fun insertContact(contact: Contact): Long = contactDao.insert(contact)

    suspend fun updateContact(contact: Contact) = contactDao.update(contact)

    suspend fun deleteContact(contact: Contact) = contactDao.delete(contact)
}