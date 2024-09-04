package com.akshat.weatherforecastcompose.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.akshat.weatherforecastcompose.model.Favourite
import com.akshat.weatherforecastcompose.navigation.WeatherScreens
import com.akshat.weatherforecastcompose.screens.favourites.FavouriteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 10.dp,
    navController: NavController,
    favouriteViewModel: FavouriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}

) {
    val showDialog = remember {
        mutableStateOf(false)
    }

    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog = showDialog, navController)
    }

    val showIt = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    Surface(shadowElevation = elevation) {
        TopAppBar(
            title = {
                Text(
                    text = title, color = MaterialTheme.colorScheme.primary, style = TextStyle(
                        fontWeight = FontWeight.Bold, fontSize = 15.sp
                    )
                )
            },
            actions = {
                if (isMainScreen) {
                    IconButton(onClick = {
                        onAddActionClicked.invoke()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search, contentDescription = "Search Icons"
                        )

                    }

                    IconButton(onClick = {
                        showDialog.value = true
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.MoreVert, contentDescription = "More Icons"
                        )
                    }
                } else Box { }
            },
            navigationIcon = {
                IconButton(onClick = {
                    onButtonClicked.invoke()
                }) {
                    if (!isMainScreen){
                        Icon(
                            imageVector = Icons.Default.ArrowBack, contentDescription = "Back Icons"
                        )
                    }
                }

                if (isMainScreen) {
                    val isAlreadyFavList =
                        favouriteViewModel.favList.collectAsState().value.filter { item ->
                            (item.city == title.split(",")[0])
                        }

                    if (isAlreadyFavList.isNullOrEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Fav icon",
                            modifier = Modifier
                                .scale(0.9f)
                                .clickable {
                                    val dataList = title.split(",")
                                    favouriteViewModel
                                        .insertFavourite(
                                            Favourite(
                                                city = dataList[0], country = dataList[1]
                                            )
                                        )
                                        .run { showIt.value = true }
                                },
                            tint = Color.Red.copy(alpha = 0.6f)
                        )
                    } else {
                        showIt.value = false
                        Box {}
                    }
                    ShowToast(context = context, showIt)
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
        )
    }
}

@Composable
fun ShowToast(context: Context, showIt: MutableState<Boolean>) {
    if (showIt.value) {
        Toast.makeText(context, "Added to Favs", Toast.LENGTH_SHORT).show()
    }

}

@Composable
fun ShowSettingDropDownMenu(
    showDialog: MutableState<Boolean>, navController: NavController
) {
    var expanded by remember { mutableStateOf(true) }
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
                DropdownMenuItem(text = {
                    Text(
                        text = text, modifier = Modifier.clickable {
                            navController.navigate(
                                when (text) {
                                    "About" -> WeatherScreens.AboutScreen.name
                                    "Favorites" -> WeatherScreens.FavouriteScreen.name
                                    else -> WeatherScreens.SettingsScreen.name
                                }
                            )


                        }, fontWeight = FontWeight.SemiBold
                    )
                }, onClick = {
                    expanded = false
                    showDialog.value = false
                }, leadingIcon = {
                    Icon(
                        imageVector = when (text) {
                            "About" -> Icons.Default.Info
                            "Favorites" -> Icons.Default.FavoriteBorder
                            else -> Icons.Default.Settings

                        }, contentDescription = null, tint = Color.LightGray
                    )
                })
            }


        }

    }


}

