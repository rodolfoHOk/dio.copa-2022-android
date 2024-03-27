package me.dio.android.copa2022app.data.remote.services

import me.dio.android.copa2022app.data.remote.models.MatchRemote
import retrofit2.http.GET

interface MatchesServices {

    @GET("api.json")
    suspend fun getMatches(): List<MatchRemote>
}
