package com.example.haengsha.ui.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.haengsha.ui.theme.LikePink
import com.example.haengsha.ui.theme.md_theme_light_onSurfaceVariant
import com.example.haengsha.ui.theme.poppins
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun formatDateToMMDD(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("MM-dd")
    return date.format(formatter)
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventCard(
    organizer: String,
    eventTitle: String,
    startDate: LocalDate,
    endDate: LocalDate,
    likes: Int,
    favorites: Int = 0,
    image: String = "",
) {
    val formattedStartDate = formatDateToMMDD(startDate)
    val formattedEndDate = formatDateToMMDD(endDate)

    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, md_theme_light_onSurfaceVariant),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(all = 16.dp),

        ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
            ) {
                Text(
                    text = organizer + "\n" + eventTitle, // 기관 명 + 행사 명 -> Text로 변경
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterStart),
                    textAlign = TextAlign.Left,
                    fontSize = 16.sp,
                    fontFamily = poppins
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "$formattedStartDate - $formattedEndDate",
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.End),
                        textAlign = TextAlign.Right,
                        fontFamily = poppins,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "♥ $likes", // 좋아요 수 -> Text로 변경
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.End),
                        textAlign = TextAlign.Right,
                        color = LikePink,
                        fontFamily = poppins
                    )
                }
            }
        }
    }
}

