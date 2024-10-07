package com.tech.mymedicalshopuser

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.tech.mymedicalshopuser.data.ApiProvider
import com.tech.mymedicalshopuser.ui_layer.navigation.AppNavigation
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            MyAppTheme {
                val navController = rememberNavController()

                AppNavigation(navController)

//                GlobalScope.launch(Dispatchers.IO) {
//
//                    val response = ApiProvider.api().getAllUsers()
//                    if(response.isSuccessful){
//                        val data = response.body()
//                        Log.d("@response", "onCreate: $data")
//
//                    }else{
//                        Log.d("@response", "onCreate: Something went wrong")
//                    }
//                }

            }
        }
    }
}
