package com.example.jetweatherforecast.screens.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jetweatherforecast.R
import com.example.jetweatherforecast.widgets.WeatherAppBar


@Composable
fun AboutScreen(
    navController: NavController
){

    Scaffold(
        topBar = {

            WeatherAppBar(
                title = "About",
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                isMainScreen = false,
                navController = navController,
                onButtonClicked = {navController.popBackStack()},

            )
        }
    ){ innerPadding ->
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(text = stringResource(id = R.string.about_app),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold)

                Text(text = stringResource(id = R.string.developer_name),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold)
            }
        }

    }

}


@Preview
@Composable
fun aboutScreenPreview(){
    AboutScreen(navController  = rememberNavController())
}