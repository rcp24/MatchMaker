package com.shadi.matchmate.presentation.components



import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shadi.matchmate.R


@Composable
fun TitleHeading(text: String, fontSize: Int =32) {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color(0xFFFF69B4), Color(0xFFFFB6C1))
    )

    Row(modifier = Modifier.padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {

        Text(
            modifier = Modifier.padding(16.dp),
            text = text,
            textAlign = TextAlign.Center,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                brush = gradientBrush,
                shadow = Shadow(
                    color = Color(0xFFF6ECAB), offset = Offset(2f, 2f), blurRadius = 4f
                )
            )
        )
    }

}