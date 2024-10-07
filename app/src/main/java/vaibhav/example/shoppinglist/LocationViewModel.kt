package vaibhav.example.shoppinglist

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LocationViewModel: ViewModel() {
    private  val _location= mutableStateOf<LocationData?>(null)
    val location : State<LocationData?> = _location

    private val _address = mutableStateOf(listOf<GeocodingResult>())
    val address: State<List<GeocodingResult>> = _address


    fun updateLocation(newLocation: LocationData){
        _location.value = newLocation
    }

    fun fetchAddress(latlng: String) {
        try {
            viewModelScope.launch {
                val result = RetrofitClient.create().getAddressFromCoordinates(
                    latlng,
                    "AIzaSyBZbm53yrsueed4OWNR4hv_ZCg6aUrzoP0"
                )

                Log.d("GeocodingResponse", "Response: $result")

                if (result.status == "OK") {
                    _address.value = result.results
                } else {
                    Log.e("GeocodingError", "Error: ${result.status}")
                }
            }
        } catch (e: Exception) {
            Log.e("GeocodingAPIError", "Error: ${e.localizedMessage}", e)
        }
    }


}
