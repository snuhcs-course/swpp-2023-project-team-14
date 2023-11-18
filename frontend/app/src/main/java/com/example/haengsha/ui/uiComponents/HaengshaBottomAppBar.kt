package com.example.haengsha.ui.uiComponents

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.haengsha.R

@Composable
fun HaengshaBottomAppBar(
    navigateFavorite: () -> Unit,
    navigateHome: () -> Unit,
    navigateBoard: () -> Unit
) {
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        actions = {
            Spacer(modifier = Modifier.width(20.dp))
            IconButton(onClick = { navigateFavorite() }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.favorite_page_icon),
                    contentDescription = "favorite screen icon"
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { navigateHome() }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.home_page_icon),
                    contentDescription = "home screen icon"
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { navigateBoard() }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.search_icon),
                    contentDescription = "board screen icon"
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
        }
    )
}