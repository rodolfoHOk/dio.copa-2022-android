package me.dio.android.copa2022app.data.remote.models

import java.util.Date

internal typealias MatchRemote = Match

data class Match(
    val name: String,
    val stadium: Stadium,
    val team1: String,
    val team2: String,
    val date: Date
)
