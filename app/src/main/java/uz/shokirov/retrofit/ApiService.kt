package uz.shokirov.retrofit

import retrofit2.Call
import retrofit2.http.GET
import uz.shokirov.model.Valyuta
import uz.shokirov.utils.MyViewModels

interface ApiService {
    @GET("json")
    fun getUsers(): Call<List<Valyuta>>
}