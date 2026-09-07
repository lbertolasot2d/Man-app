package com.example.man_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.man_app.App
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate started")
        
        val app = application as ManApp
        
        enableEdgeToEdge()
        setContent {
            if (app.isDatabaseInitialized) {
                // Launch the real application
                App(database = app.database, syncTrigger = app)
            } else {
                // Show loading or error during background initialization
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // Displaying the logo in the loading screen too
                        Image(
                            painter = painterResource(R.drawable.logo_lavorare_meglio),
                            contentDescription = null,
                            modifier = Modifier.size(120.dp).padding(bottom = 24.dp)
                        )
                        
                        val error = app.initializationError
                        if (error != null) {
                            Text("Errore Critico Database:", color = androidx.compose.ui.graphics.Color.Red, fontWeight = FontWeight.Bold)
                            Text(error, color = androidx.compose.ui.graphics.Color.Red)
                        } else {
                            CircularProgressIndicator()
                            Spacer(Modifier.height(16.dp))
                            Text("Inizializzazione database...")
                        }
                    }
                }
            }
        }
    }
}
