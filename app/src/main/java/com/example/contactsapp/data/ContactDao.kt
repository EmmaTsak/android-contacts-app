package com.example.contactsapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: Contact): Long

    @Update
    suspend fun update(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Query("SELECT * FROM contacts ORDER BY first_name ASC, last_name ASC")
    fun getAllContacts(): Flow<List<Contact>>

    @Query("SELECT * FROM contacts WHERE id = :contactId")
    fun getContactById(contactId: Long): Flow<Contact?>

    @Query("""
        SELECT * FROM contacts 
        WHERE first_name LIKE '%' || :query || '%' 
        OR last_name LIKE '%' || :query || '%' 
        OR phone_number LIKE '%' || :query || '%'
        ORDER BY first_name ASC, last_name ASC
    """)
    fun searchContacts(query: String): Flow<List<Contact>>
}