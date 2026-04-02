package com.example.wiisignageevento

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.wiisignageevento.viewmodel.WeatherResponse
import kotlinx.coroutines.delay


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherFooter() {

    var weather by remember { mutableStateOf<WeatherResponse?>(null) }

    val cities =  listOf("Rio de Janeiro", "Niterói")
    var cityIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            try {
                val city = cities[cityIndex % cities.size]
                weather = WeatherService.getWeather(city)

                cityIndex++
            } catch (e: Exception) {
                e.printStackTrace()
            }
            delay(10 * 60 * 1000L) // 10 minutos
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 2.dp,
        shadowElevation = 4.dp
    ) {

        /*Row(
            Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Cotação do dólar à esquerda

            CurrencyFooter()

            // Clima à direita
            if (weather != null) {

                val iconUrl = "https://openweathermap.org/img/wn/${weather!!.weather[0].icon}.png"//"https://openweathermap.org/img/wn/${weather!!.weather[0].icon}@2x.png"
                Log.d("TEMPO", "TEMPO: $iconUrl")
                Row(verticalAlignment = Alignment.CenterVertically) {

                   Image(
                        painter = rememberAsyncImagePainter(iconUrl),
                        contentDescription = weather!!.weather[0].description,
                        modifier = Modifier.size(32.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${weather!!.main.temp.toInt()}°C, ${weather!!.weather[0].description} em ${weather!!.name}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                Text("Clima...", style = MaterialTheme.typography.bodyMedium)
            }


           DigitalClock(
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically) // Alinha no canto inferior direito
                    .padding(5.dp) // Ajuste da distância da borda
                    .wrapContentSize() // Ajusta o tamanho do relógio para que ele não ocupe a tela inteira
            )
        }*/

        Row(
            Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Cotação à esquerda
            Box(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
            ) {
                CurrencyFooter()
            }

            // Clima no centro
            Box(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                if (weather != null) {
                    val iconUrl = "https://openweathermap.org/img/wn/${weather!!.weather[0].icon}.png"
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = rememberAsyncImagePainter(iconUrl),
                            contentDescription = weather!!.weather[0].description,
                            modifier = Modifier.size(32.dp),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${weather!!.main.temp.toInt()}°C, ${weather!!.weather[0].description} em ${weather!!.name}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                } else {
                    Text("Clima...", style = MaterialTheme.typography.bodyMedium)
                }
            }

            // Relógio à direita
            Box(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.End)
            ) {
                DigitalClock(
                    fontSize = 17.sp,
                    modifier = Modifier
                        .padding(end = 8.dp)
                )
            }
        }


    }
}


/*@Composable
fun WeatherFooter() {
    val coroutineScope = rememberCoroutineScope()
    var weather by remember { mutableStateOf<WeatherResponse?>(null) }

    val cities = listOf("Rio de Janeiro", "Niterói")
    var cityIndex by remember { mutableStateOf(0) }



    LaunchedEffect(Unit) {
        while (true) {
            val currentCity = cities[cityIndex % cities.size]
            try {
                weather = WeatherService.getWeather(currentCity)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            cityIndex++
            delay(10 * 60 * 1000L) // Espera 10 minutos antes de buscar a próxima cidade
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 2.dp,
        shadowElevation = 4.dp
    ) {
        Box(
            Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (weather != null) {
                val iconUrl = "https://openweathermap.org/img/wn/${weather!!.weather[0].icon}@2x.png"
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(iconUrl),
                        contentDescription = weather!!.weather[0].description,
                        modifier = Modifier.size(32.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${weather!!.main.temp.toInt()}°C, ${weather!!.weather[0].description} em ${weather!!.name}",
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            } else {
                Text("Carregando clima...", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}*/
