package com.shadi.matchmate.presentation.allmatches

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.shadi.matchmate.R
import com.shadi.matchmate.domain.model.ProfileMatch
import com.shadi.matchmate.presentation.components.ProfileStatusButton


@Composable
fun PersonProfileItem(
    profileMatch: ProfileMatch,
    onProfileAccepted: (ProfileMatch) -> Unit,
    onProfileDeclined: (ProfileMatch) -> Unit,
) {
    val localContext = LocalContext.current

    Card(modifier = Modifier.padding(all = 16.dp).shadow(4.dp, shape = RoundedCornerShape(24.dp))
        .wrapContentHeight()
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .background(Color.Red.copy(.20f)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth()
                    .height(380.dp)
                    .padding(16.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(24.dp))
                    .clip(RoundedCornerShape(24.dp))
                        ,
                model = profileMatch.profilePicUrl,
                contentDescription = profileMatch.name,
                imageLoader =
                ImageLoader
                    .Builder(context = localContext)
                    .crossfade(enable = true)
                    .build(),
            )

            VerticalDivider(thickness = 12.dp)

            Text(
                text = profileMatch.name,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 22.sp),
                color = Color.White,
            )

            VerticalDivider(thickness = 4.dp)

            Text(
                text = profileMatch.address,
                style = TextStyle(fontSize = 16.sp),
                color = Color.White
            )



            Box(modifier = Modifier.fillMaxWidth()) {
                if (profileMatch.status == 0 || profileMatch.status == 1) {
                    Spacer(modifier = Modifier.height(18.dp))
                    Box(modifier = Modifier
                            .fillMaxWidth()
                            .padding(top=20.dp)
                            .height(height = 60.dp)
                            .clip(
                                shape =
                                RoundedCornerShape(
                                    topStart = 12.dp,
                                    topEnd = 12.dp,
                                ),
                            ).background(color = if (profileMatch.status == 1) Color(27, 161, 32, 255) else Color(
                            229,
                    16,
                    16,
                    255
                    )),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = if (profileMatch.status == 1) "Accepted" else "Declined",
                            color =Color.White,
                            style = TextStyle(fontSize = 18.sp),
                        )
                    }
                }
                else {
                    Row(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top=20.dp,bottom = 20.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                    ) {
                        ProfileStatusButton (
                            resId = R.drawable.reject_ic,
                            onClick = {
                                onProfileDeclined(profileMatch)
                            },
                        )

                        ProfileStatusButton(
                            resId = R.drawable.accept_ic,
                            onClick = {
                                onProfileAccepted(profileMatch)
                            },
                        )
                    }
                }
            }
        }
    }
}
