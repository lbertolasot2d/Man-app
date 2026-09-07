package com.example.man_app.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.Color
import android.util.Log

@Composable
actual fun getLogoPainter(): Painter {
    val context = LocalContext.current
    val packageName = "com.example.man_app"
    val resId = context.resources.getIdentifier("logo_lavorare_meglio", "drawable", packageName)
    
    return if (resId != 0) {
        painterResource(resId)
    } else {
        Log.e("PlatformUtils", "Resource logo_lavorare_meglio not found in $packageName")
        ColorPainter(Color.LightGray)
    }
}
