package com.example.jetweatherforecast.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jetweatherforecast.model.Unit
import com.example.jetweatherforecast.widgets.WeatherAppBar

@Composable
fun SettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
){
    var unitToggleState by remember { mutableStateOf(false) }
    val measurementUnits = listOf("Imperial (F)" ,"Metric (C)")
    val choiceFromDb = settingsViewModel.unitList.collectAsState().value

    val defaultChoice = if(choiceFromDb.isNullOrEmpty()) measurementUnits[0]
    else choiceFromDb[0].unit

    var choiceState by remember {
        mutableStateOf(defaultChoice)
    }


   Scaffold(
       topBar = {
           WeatherAppBar(
               navController = navController,
               isMainScreen = false,
               icon = Icons.AutoMirrored.Filled.ArrowBack,
               title = "Settings",
               onButtonClicked = {
                   navController.popBackStack()
               }

           )
       }
   ){
       innerPadding ->

       Surface(
           modifier = Modifier
               .fillMaxWidth()
               .fillMaxHeight()
               .padding(innerPadding)
       ){
           Column(
               horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.Center,
           )
           {
               Text(
                   text = "Change Units of Measurement",
                   modifier = Modifier.padding(bottom = 15.dp)
               )


               IconToggleButton(
                   checked = !unitToggleState,
                   onCheckedChange = {
                       unitToggleState = !it
                       choiceState = if(unitToggleState){
                           "Imperial (F)"
                       } else {
                           "Metric (C)"
                       }
                   },
                   modifier = Modifier
                       .fillMaxWidth(0.5f)
                       .clip(shape = RectangleShape)
                       .padding(5.dp)
                       .background(Color(0xADE7E6E6))
                   ) {
                   Text(text = if(unitToggleState) "Fahrenheit ºF" else "Celsius ºC")
               }

               Button(onClick = {
                   settingsViewModel.deleteAllUnits()
                   settingsViewModel.insertUnit(Unit(unit = choiceState))
               },
                   shape = RoundedCornerShape(corner = CornerSize(12.dp)),
                   modifier = Modifier.padding(top = 15.dp),
                   colors = ButtonDefaults.buttonColors(Color(0xFFFFC107))
               ) {
                   Text(text = "Save",
                       style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
                       )
               }
           }
       }

   }
}


//@Preview
//@Composable
//fun SettingsPreview(){
//    SettingsScreen(rememberNavController())
//}
