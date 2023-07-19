package com.rustam.spisok.bottom_navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rustam.spisok.ui.theme.BackMenu
import com.rustam.spisok.ui.theme.Pink40
import com.rustam.spisok.ui.theme.Purple150

@Composable
fun KnopkiNavigation(
    navController: NavController
) {
    val listItems = listOf(
        BottomItem.Spisok,
        BottomItem.Tovari,
        BottomItem.Zametki,
        BottomItem.Work
    )
    BottomNavigation(
        backgroundColor = BackMenu
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        listItems.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(painter = painterResource(id = item.iconId), contentDescription = item.title)
                },
                label = {
                    Text(text = item.title, fontSize = 9.sp)
                },
                selectedContentColor = Pink40,
                unselectedContentColor = Color.Gray
            )
        }
    }
}