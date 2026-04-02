package com.example.wiisignageevento.screens

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.runtime.getValue
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import com.example.wiisignageevento.DeviceButtonWithArrow
import com.example.wiisignageevento.DigitalClock
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TelaSemMidias(
    nomeEvento: String,
    nomeEmpresa: String,
    logoUrl: String?,
    deviceName: String
) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val infiniteTransition = rememberInfiniteTransition(label = "scroll")
    val gradientOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "gradientOffset"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()

            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFC54FF7), Color(0xFF424242)),
                    startY = gradientOffset,
                    endY = gradientOffset + 400f
                )
            )
            .clipToBounds(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .background(Color.Yellow)
                .align(Alignment.TopCenter)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .background(Color.Yellow)
                .align(Alignment.BottomCenter)
        )
        if (isPortrait) {
            ColumnContent(
                nomeEvento = nomeEvento,
                nomeEmpresa = nomeEmpresa,
                logoUrl = logoUrl,
                deviceName = deviceName
            )
        } else {
            RowContent(
                nomeEvento = nomeEvento,
                nomeEmpresa = nomeEmpresa,
                logoUrl = logoUrl,
                deviceName = deviceName
            )
        }

        // Relógio digital no canto inferior direito
        /*DigitalClock(
            modifier = Modifier
                .align(Alignment.BottomCenter) // Alinha no canto inferior direito
                .padding(16.dp) // Ajuste da distância da borda
                .wrapContentSize() // Ajusta o tamanho do relógio para que ele não ocupe a tela inteira
        )*/
    }
}

@Composable
fun ColumnContent(
    nomeEvento: String,
    nomeEmpresa: String,
    logoUrl: String?,
    deviceName: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Imagem de fundo como marca d'água
        logoUrl?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = "Marca d'água da empresa",
                contentScale = ContentScale.Fit,
                alpha = 0.1f,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Topo
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Bem Vindo", color = Color.White, fontSize = 30.sp)

                Text(
                    modifier = Modifier.basicMarquee(
                        iterations = Int.MAX_VALUE,
                        initialDelayMillis = 0,
                        velocity = 50.dp,
                        spacing = MarqueeSpacing(32.dp)
                    ),
                    text = nomeEvento,
                    color = Color.White,
                    fontSize = 100.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(nomeEmpresa, color = Color.White, fontSize = 70.sp)
            }

            // Meio: botão com seta
            DeviceButtonWithArrow(deviceName = deviceName)

            // Base: botão de login e rodapé
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { /* ação de login */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(deviceName, color = Color(0xFF393A3B), fontSize = 50.sp)
                }

                TextButton(onClick = { /* ação de signup */ }) {
                    Text("H Niterói Hotel, o melhor hotel de niterói!", color = Color.White, fontSize = 15.sp)
                }
            }
        }
    }
}






/*@Composable
fun ColumnContent(nomeEvento: String, nomeEmpresa: String, logoUrl: String?, deviceName: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(24.dp)
    ) {
        Text("Bem Vindo", color = Color.White, fontSize = 30.sp)

        Text(
            modifier = Modifier.basicMarquee(
                iterations = Int.MAX_VALUE,
                initialDelayMillis = 0,
                velocity = 50.dp,
                spacing = MarqueeSpacing(32.dp)
            ),
            text = nomeEvento,
            color = Color.White,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold
        )

        Text(nomeEmpresa, color = Color.White, fontSize = 25.sp)

        logoUrl?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = "Logo da empresa",
                contentScale = ContentScale.Crop, // ajusta a imagem ao formato circular
                modifier = Modifier
                    .size(300.dp) // define altura e largura iguais
                    .clip(CircleShape) // recorta a imagem em forma de círculo

            )
        }
        // Exibe o botão com o nome do dispositivo e a seta
        DeviceButtonWithArrow (deviceName = deviceName)
       Button(
            onClick = { *//* ação de login *//* },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(deviceName, color = Color(0xFF393A3B), fontSize = 25.sp)
        }

        TextButton(onClick = { *//* ação de signup *//* }) {
            Text("H Niterói Hotel, o melhor hotel de niterói!", color = Color.White,fontSize = 15.sp)
        }
    }
}*/

@Composable
fun RowContent(
    nomeEvento: String,
    nomeEmpresa: String,
    logoUrl: String?,
    deviceName: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
        // fundo base escuro
    ) {
        // Marca d'água no fundo
        logoUrl?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = "Marca d'água",
                contentScale = ContentScale.Fit,
                alpha = 0.08f, // transparência sutil
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            )
        }

        // Conteúdo sobreposto
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Bem Vindo", color = Color.White, fontSize = 40.sp)

                Text(
                    modifier = Modifier.basicMarquee(
                        iterations = Int.MAX_VALUE,
                        initialDelayMillis = 0,
                        velocity = 50.dp,
                        spacing = MarqueeSpacing(32.dp)
                    ),
                    text = nomeEvento,
                    color = Color.White,
                    fontSize = 100.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(nomeEmpresa, color = Color.White, fontSize = 70.sp)

                DeviceButtonWithArrow(deviceName = deviceName)

                Button(
                    onClick = { /* ação de login */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(deviceName, color = Color(0xFF393A3B), fontSize = 50.sp)
                }

                TextButton(onClick = { /* ação de signup */ }) {
                    Text(
                        "H Niterói Hotel, o melhor hotel de Niterói!",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
            }


        }
    }
}


/*@Composable
fun RowContent(nomeEvento: String, nomeEmpresa: String, logoUrl: String?, deviceName: String) {
    Row (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Bem Vindo", color = Color.White, fontSize = 40.sp)

            Text(
                modifier = Modifier.basicMarquee(
                    iterations = Int.MAX_VALUE,
                    initialDelayMillis = 0,
                    velocity = 50.dp,
                    spacing = MarqueeSpacing(32.dp)
                ),
                text = nomeEvento,
                color = Color.White,
                fontSize = 70.sp,
                fontWeight = FontWeight.Bold
            )

            Text(nomeEmpresa, color = Color.White, fontSize = 35.sp)

            // Exibe o botão com o nome do dispositivo e a seta
            DeviceButtonWithArrow (deviceName = deviceName)
            Button(
                onClick = { *//* ação de login *//* },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(deviceName, color = Color(0xFF393A3B), fontSize = 30.sp)
            }


            TextButton(onClick = { *//* ação de signup *//* }) {
                Text("H Niterói Hotel, o melhor hotel de niterói!", color = Color.White,fontSize = 20.sp)
            }
        }

        logoUrl?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = "Logo da empresa",
                contentScale = ContentScale.Crop, // ajusta a imagem ao formato circular
                modifier = Modifier
                    .size(300.dp) // define altura e largura iguais
                    .clip(CircleShape) // recorta a imagem em forma de círculo

            )
        }
    }
}*/



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TelaSemMidias("BRAM SHOUWEST","BRAM","http://10.20.42.60/storage/eventos/img/2238/agKx2UcwnxbIA2tvlK43PEIyZrI2gxf9gHAZsxJm.png","BAIA")
}
