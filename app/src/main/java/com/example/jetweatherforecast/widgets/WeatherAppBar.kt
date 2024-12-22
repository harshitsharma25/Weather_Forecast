package com.example.jetweatherforecast.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jetweatherforecast.model.Favorite
import com.example.jetweatherforecast.navigation.WeatherScreens
import com.example.jetweatherforecast.screens.favorite.FavoriteViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun WeatherAppBar(

    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},  // for search button (to perform specific action)
    onButtonClicked: () -> Unit = {},   // for navigation (back button etc..)
    shadowColor: Color = Color.Black,

    ) {

    val showDialog = remember {
        mutableStateOf(false)
    }

    val userInteracted = remember {
        mutableStateOf(false)
    }

    val showIt = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
//    var buttonPressed by remember {
//        mutableStateOf(false)
//    }

    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)
    }

    Surface(
        shadowElevation = elevation, modifier = Modifier.systemBarsPadding()
    ) {

        TopAppBar(
            modifier = Modifier.shadow(
                elevation, ambientColor = shadowColor, spotColor = shadowColor
            ),
            title = {
                Text(
                    title, style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold, fontSize = 24.sp
                    ),
                    //color = MaterialTheme.colorScheme.onSecondarycolor
                    color = Color.White, modifier = Modifier.padding(start = 10.dp)
                )
            },
            //        0xFFE9B928
            colors = TopAppBarDefaults.topAppBarColors(Color(0xFFE9B928)),
            actions = {
                if (isMainScreen) {

                    Row {
                        IconButton(onClick = {
                            onAddActionClicked.invoke()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "search icon",
                                modifier = Modifier.size(25.dp)

                            )
                        }

                        IconButton(onClick = {
                            showDialog.value = true
                        }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Morevert icon",
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    }

                }

            },
            navigationIcon = {

                if (icon != null) {
                    Icon(
                        imageVector = icon, contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.clickable {
                            onButtonClicked.invoke()
                        },
                    )
                }

                if (isMainScreen) {

                    val isAlreadyFavList =
                        favoriteViewModel.favList.collectAsState().value.any { item ->
                                item.city == title.split(",")[0]
                            }

                    // if (isAlreadyFavList.isNullOrEmpty()) {

                    Icon(
//                                if (buttonPressed) Icons.Default.Favorite else
                        imageVector = if (isAlreadyFavList) Icons.Default.Favorite
                        else Icons.Default.FavoriteBorder,

                        contentDescription = "Favorite Icon",
                        modifier = Modifier
                            .scale(0.9f)
                            .clickable {
                                val dataList = title.split(",")

                                // removing the favorite city to favorite list
                                if (isAlreadyFavList) {
                                    favoriteViewModel.deleteFavorite(
                                        Favorite(
                                            city = dataList[0],   // city name
                                            country = dataList[1] // country code
                                        )
                                    )
                                    showIt.value = false

                                    // adding the favorite city from the favorite list
                                } else {
                                    favoriteViewModel.insertFavorite(
                                        Favorite(
                                            city = dataList[0], country = dataList[1]
                                        )
                                    )
                                    showIt.value = true

                                }
                                userInteracted.value = true
                            },
                        tint = Color.Red.copy(alpha = 0.6f)
                    )

                    LaunchedEffect(userInteracted.value) {
                        if (userInteracted.value) {
                            ShowToast(context = context, showIt.value)
                            userInteracted.value = false
                        }
                    }
                }
            },

            )
    }
}


fun ShowToast(context: Context, isAdded: Boolean) {
    val message = if (isAdded) "Added to Favorites" else "Deleted from Favorites"
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {

    var expanded by remember {
        mutableStateOf(true)
    }

    val items = listOf("About", "Favorites", "Settings")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)
        ) {
            items.forEachIndexed { index, text ->
                DropdownMenuItem(
                    text = {
                        Row {
                            Icon(
                                imageVector = when (text) {
                                    "About" -> Icons.Default.Info
                                    "Favorites" -> Icons.Default.FavoriteBorder
                                    else -> Icons.Default.Settings

                                }, contentDescription = null, tint = Color.LightGray
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = text, modifier = Modifier.clickable {
                                    navController.navigate(
                                        when (text) {
                                            "About" -> WeatherScreens.AboutScreen.name
                                            "Favorites" -> WeatherScreens.FavoriteScreen.name
                                            else -> WeatherScreens.SettingsScreen.name
                                        }
                                    )
                                }, fontWeight = FontWeight.W300
                            )
                        }
                    },

                    onClick = {
                        expanded = expanded
                        showDialog.value = false
                    },
                )
            }
        }
    }

}


@Preview
@Composable
fun appBarPreview() {
    WeatherAppBar(navController = rememberNavController())
}