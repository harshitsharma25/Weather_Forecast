package com.example.jetweatherforecast.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetweatherforecast.model.WeatherItem
import com.example.jetweatherforecast.screens.main.WeatherStateImage

import com.example.jetweatherforecast.utils.fetchDay


@Composable
fun DayData(weather: WeatherItem , isImperial : Boolean) {

    val imageURL = "https://openweathermap.org/img/wn/${weather.weather[0].icon}.png"

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp,
                start = 10.dp,
                end = 10.dp
            ), // Added padding around each DayData Surface
        shape = RoundedCornerShape(12.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 2.dp, vertical = 5.dp)
                .background(Color.White)
                .fillMaxWidth(),

            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Text(
                text = fetchDay(weather.dt),
                modifier = Modifier.padding(start = 8.dp)
            )

            WeatherStateImage(imageURL = imageURL, size = 60.dp)

            Surface(
                color = Color(0xFFFFEDB4),
                shape = RoundedCornerShape(12.dp),

                ) {
                Text(
                    text = weather.weather[0].description,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Row(modifier = Modifier.padding(end = 8.dp)) {
                Text(
                    text = "${weather.temp.max}" + if(isImperial) "ºF" else "ºC",
                    style = TextStyle(color = MaterialTheme.colorScheme.tertiary, fontSize = 20.sp),
                    )
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "${weather.temp.min}" + if(isImperial) "ºF" else "ºC")
            }


        }
    }

}