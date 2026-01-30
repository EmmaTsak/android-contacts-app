// ui/navigation/NavGraph.kt
package com.example.contactsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.contactsapp.ui.contactform.ContactFormScreen
import com.example.contactsapp.ui.contactlist.ContactListScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "contact_list"
    ) {
        composable("contact_list") {
            ContactListScreen(
                onAddContact = { navController.navigate("contact_form") },
                onEditContact = { contactId ->
                    navController.navigate("contact_form/$contactId")
                }
            )
        }

        composable(
            route = "contact_form/{contactId}",
            arguments = listOf(navArgument("contactId") { type = NavType.LongType })
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getLong("contactId")
            ContactFormScreen(navController = navController, contactId = contactId)
        }

        composable("contact_form") {
            ContactFormScreen(navController = navController, contactId = null)
        }
    }
}