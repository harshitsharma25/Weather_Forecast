package com.example.jetweatherforecast.screens.main


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.jetweatherforecast.R
import com.example.jetweatherforecast.data.DataOrException
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.model.WeatherItem
import com.example.jetweatherforecast.navigation.WeatherScreens
import com.example.jetweatherforecast.screens.settings.SettingsViewModel
import com.example.jetweatherforecast.utils.fetchDay
import com.example.jetweatherforecast.utils.formatDate
import com.example.jetweatherforecast.utils.formatDateTime

import com.example.jetweatherforecast.utils.formatPressure
import com.example.jetweatherforecast.widgets.DayData
import com.example.jetweatherforecast.widgets.WeatherAppBar


@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    city: String?,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
){
    Log.d("city","name: $city")


    val curCity :String = if(city!!.isBlank()) "Seattle" else city

    val unitFromDb by settingsViewModel.unitList.collectAsState()

    Log.d("unitFromDB","is:$unitFromDb")

    var unit by remember {
        mutableStateOf("imperial")
    }

    var isImperial by remember {
        mutableStateOf(false)
    }

    if(unitFromDb.isNotEmpty()){
        unit = unitFromDb[0].unit.split(" ")[0].lowercase()
        isImperial = unit == "imperial"

        val weatherData = produceState<DataOrException<Weather,Boolean,Exception>>(
            initialValue = DataOrException(loading = true)) {
            value = mainViewModel.getWeatherData(city = curCity,
                units = unit)
        }.value

        if(weatherData.loading == true){
            CircularProgressIndicator()
            Log.d("where","${weatherData.data}")
        }else if(weatherData.data != null){
            Log.d("where","${weatherData.data}")
            MainScaffold(weather = weatherData.data,navController,isImperial = isImperial)
        }
    }

    else {
        Log.d("empty" , "data is empty")
    }
}


@Composable
fun MainScaffold(weather: Weather, navController: NavController,isImperial : Boolean) {

    Scaffold(topBar = {
        WeatherAppBar(
            title = weather.city.name + ", " + weather.city.country,
            elevation = 15.dp,
            shadowColor = Color.Black,
            onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            },
            navController = navController,

        )
    }){ innerPadding ->
        MainContent(data = weather , padding =  innerPadding , isImperial = isImperial)
    }

}


@Composable
fun MainContent(data: Weather, padding: PaddingValues, isImperial: Boolean){
    Log.d("data","name: $data")
    val weatherItem = data.list[0]
    val imageURL = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
        ){
//        Text(text = data.city.name)

        Text(text = formatDate(weatherItem.dt),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 25.dp, bottom = 10.dp),
            fontSize = 20.sp
            )

        Surface(
            modifier = Modifier
                .size(180.dp)
                .padding(top = 4.dp),
            shape = CircleShape,
            color = Color(0xFFFFF0C6)
        ){

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 25.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally){

                WeatherStateImage(imageURL = imageURL, size = 80.dp)


                Text(text = "${weatherItem.temp.day}" + if(isImperial) "ºF" else "ºC",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 38.sp)
                )


                Text(
                    weatherItem.weather[0].main,
                    style = TextStyle(fontWeight = FontWeight.Normal,
                        fontSize = 18.sp))
            }
        }

        HumidityWindPressureRow(weather = data.list[0] , isImperial = isImperial)

        HorizontalDivider()

        SunRiseSunSetRow(weather = data.list[0])

        Text(
            text = "This Week",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier.padding(top = 22.dp, bottom = 8.dp),
            //color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7f)
            color = Color.Black.copy(alpha = 0.6f),
            fontSize = 20.sp
        )

        Surface(
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(corner = CornerSize(12.dp))
        ){
            LazyColumn {
                items(data.list) { item ->
                    DayData(item , isImperial)
                    Log.d("temp","value: ${imageURL},day: ${fetchDay(item.dt)}, data: ${data.list[0].weather[0].description}" )
                }
            }
        }
    }

}



@Composable
fun SunRiseSunSetRow(weather: WeatherItem) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 20.dp, start = 12.dp, end = 12.dp)
            .fillMaxWidth()
    ){
        Row{
            Icon(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "Sun Rise",
                modifier = Modifier.size(25.dp),

            )
            Text(text = formatDateTime(weather.sunrise),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 5.dp, start = 5.dp),

            )
        }

        Row{
            Icon(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "Sun Set",
                modifier = Modifier.size(25.dp)
            )
            Text(text = formatDateTime(weather.sunset),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 5.dp, start = 5.dp)
            )
        }
    }
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem, isImperial: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row(modifier = Modifier.padding(4.dp)){
            Icon(painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity icon",
                modifier = Modifier.size(20.dp))
            Text(text = "${weather.humidity}%")
        }

        Row{
            Icon(painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure icon",
                modifier = Modifier.size(20.dp))
            Text(text = "${formatPressure(weather.pressure)} psi")
        }

        Row{
            Icon(painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind icon",
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 3.dp))
            Text(text = "${weather.speed}" + if(isImperial) "mph" else "m/s")
        }

    }
}


@Composable
fun WeatherStateImage(imageURL: String,size : Dp) {
    Image(painter = rememberAsyncImagePainter(imageURL),     // rememberAsyncImagePainter() for when image comes from the internet
        contentDescription = "icon image",
        modifier = Modifier.size(size))

}
