package me.dio.android.copa2022app.features

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.dio.android.copa2022app.R
import me.dio.android.copa2022app.domain.extensions.getDate
import me.dio.android.copa2022app.domain.models.MatchDomain
import me.dio.android.copa2022app.domain.models.TeamDomain

@Composable
fun MainScreen(matches: List<MatchDomain>) {
    Box(modifier = Modifier.background(Color.Black)) {
        Column(modifier = Modifier.padding(top = 16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Copa do Mundo",
                    style = MaterialTheme.typography.headlineMedium.copy(color = Color.White)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(matches) { match ->
                        MatchInfo(match)
                    }
                }
            }
        }
    }
}

@Composable
fun MatchInfo(match: MatchDomain) {
    Card(shape = RoundedCornerShape(24.dp), modifier = Modifier.fillMaxWidth()) {
        Box {
            AsyncImage(
                model = match.stadium.image,
                contentDescription = "Imagem do estádio ${match.stadium.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(160.dp)
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Notification(match)
                Title(match)
                Team(match)
            }
        }
    }
}

@Composable
fun Notification(match: MatchDomain) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        val drawable =
            if (match.notificationEnabled) R.drawable.ic_notifications else R.drawable.ic_notifications_active
        val notificationDescription =
            if (match.notificationEnabled) "Desativar notificação" else "Ativar notificação"
        Image(
            painter = painterResource(id = drawable),
            contentDescription = notificationDescription
        )
    }
}

@Composable
fun Title(match: MatchDomain) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(
            text = "${match.date.getDate()} - ${match.name}",
            style = MaterialTheme.typography.titleLarge.copy(color = Color.White)
        )
    }
}

@Composable
fun Team(match: MatchDomain) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TeamItem(team = match.team1)

        Text(
            text = "X",
            modifier = Modifier.padding(end = 16.dp, start = 16.dp),
            style = MaterialTheme.typography.titleLarge.copy(color = Color.White)
        )

        TeamItem(team = match.team2)
    }
}

@Composable
fun TeamItem(team: TeamDomain) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = team.flag,
            modifier = Modifier.align(Alignment.CenterVertically),
            style = MaterialTheme.typography.displaySmall.copy(color = Color.White)
        )

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = team.displayName,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(color = Color.White)
        )
    }
}
