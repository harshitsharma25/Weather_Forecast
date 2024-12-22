package com.example.jetweatherforecast.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.navigation.NavController
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.jetweatherforecast.R
import com.example.jetweatherforecast.navigation.WeatherScreens


@Composable
fun WeatherSplashScreen(navController: NavController) {


    val scale = remember {
        Animatable(0f)
    }

    val defaultCity = "Delhi"

    LaunchedEffect(key1 = true, block = {
        scale.animateTo(targetValue = 0.55f,
            animationSpec = tween(
                durationMillis = 1800,
                easing = {
                    OvershootInterpolator(8f)
                        .getInterpolation(it)
                }
            )
        )
       //delay(2000)
        navController.navigate(WeatherScreens.MainScreen.name + "/$defaultCity")  // added the default city to navigate to the mainScreen
    })

    Surface(
        modifier = Modifier
            .padding(15.dp)
            .size(330.dp)
            .scale(scale.value),   // animation added
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(
            width = 2.dp,
            color = Color.LightGray)
    ){
        Column(modifier = Modifier.padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){
            Image(
                painter = painterResource(id = R.drawable.sun),
                contentDescription = "sun",
                modifier = Modifier.size(100.dp))
            Text(text = "Find the Sun?",
                style = TextStyle(color = Color.LightGray, fontSize = 30.sp
                ))
        }
    }
}


@Composable
@Preview
fun WeatherPreview(){
    val navController = rememberNavController()
    WeatherSplashScreen(navController)
}