package io.pc7.ninu.presentation.theme


import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import io.pc7.ninu.R


val montserratFontFamily = FontFamily(
    Font(R.font.montserrat_light, FontWeight.Light),        //300
    Font(R.font.montserrat_regular, FontWeight.Normal),     //400
    Font(R.font.montserrat_medium, FontWeight.Medium),      //500
    Font(R.font.montserrat_semibold, FontWeight.SemiBold),  //600
    Font(R.font.montserrat_bold, FontWeight.Bold),          //700

)

val NINUTypography =
    Typography(
        headlineLarge = TextStyle(
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            lineHeight = 33.sp
        ),
        headlineMedium = TextStyle(
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        ),
        headlineSmall = TextStyle(
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        ),


        titleLarge = TextStyle(
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
        ),
        titleMedium = TextStyle(
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 17.5.sp,
        ),
        titleSmall = TextStyle(
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        ),



        bodyLarge =     TextStyle(
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
        ),
        bodyMedium =    TextStyle(
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
        ),
        bodySmall = TextStyle(
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
        ),

        //Button text
        displayLarge =  TextStyle(
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
        ),


        //Caption
        labelLarge = TextStyle(
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 16.sp,
        ),
        //Note
        labelMedium = TextStyle(
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
        ),
        //Tooltip
        labelSmall =   TextStyle(
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
        ),



        displaySmall = TextStyle(
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,


        )

   )


