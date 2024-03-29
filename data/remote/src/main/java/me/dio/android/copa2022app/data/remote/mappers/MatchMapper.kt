package me.dio.android.copa2022app.data.remote.mappers

import me.dio.android.copa2022app.data.remote.models.MatchRemote
import me.dio.android.copa2022app.data.remote.models.StadiumRemote
import me.dio.android.copa2022app.domain.models.MatchDomain
import me.dio.android.copa2022app.domain.models.StadiumDomain
import me.dio.android.copa2022app.domain.models.TeamDomain
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.Locale

internal fun List<MatchRemote>.toDomain() = map { matchRemote ->
    matchRemote.toDomain()
}

fun MatchRemote.toDomain(): MatchDomain {
    return MatchDomain(
        id = "$team1-$team2",
        name = name,
        team1 = team1.toTeam(),
        team2 = team2.toTeam(),
        stadium = stadium.toDomain(),
        date = date.toLocalDateTime(),
//        date = LocalDateTime.now().plusMinutes(6), // for test
    )
}

private fun String.toTeam(): TeamDomain {
    return TeamDomain(
        flag = getTeamFlag(this),
        displayName = Locale("", this).isO3Country
    )
}

private fun getTeamFlag(team: String): String {
    return team.map {
        String(Character.toChars(it.code + 127397))
    }.joinToString("")
}

fun StadiumRemote.toDomain(): StadiumDomain {
    return StadiumDomain(
        name = name,
        image = image
    )
}

private fun Date.toLocalDateTime(): LocalDateTime {
    return toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
}
