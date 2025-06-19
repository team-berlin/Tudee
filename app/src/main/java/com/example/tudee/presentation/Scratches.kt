package com.example.tudee.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.designsystem.theme.TudeeTheme



@Preview(showBackground = true)
@Composable
fun TapBarDivider(

) {

    Box {
        HorizontalDivider(Modifier
            .fillMaxWidth()
            .align(Alignment.BottomStart),
            thickness = 1.dp,
            color = TudeeTheme.color.stroke)
        Row(
            Modifier
                .fillMaxWidth()
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically,

        ) {
            var boxWodth by remember { mutableFloatStateOf(0f) }
            Box(
                Modifier.fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.onSizeChanged{
                       boxWodth=it.width.toFloat()},
                    text = "In progress")

                TabBarIndicator(
                    Modifier
                        .align(Alignment.BottomCenter)
                        .width(boxWodth.dp)

                )
            }

            Box (
                Modifier.fillMaxHeight()
                    .wrapContentWidth(),
                contentAlignment = Alignment.Center

            ){
                Text("In progress")
                TabBarIndicator(Modifier.align(Alignment.BottomCenter))

            }
            Box {
                Text("In progress")
                TabBarIndicator(Modifier.align(Alignment.BottomCenter))

            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun TabBarIndicator(modifier: Modifier= Modifier) {
    Box(
        modifier
            .height(4.dp)
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .background(TudeeTheme.color.secondary)
    )
}
//@Preview(showBackground = true)
//@Composable
//fun TabBar(selectedIndex: Int=0) {
//    val tabs = listOf("All", "In Progress", "Completed")
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        horizontalArrangement = Arrangement.SpaceEvenly,
//        verticalAlignment = Alignment.Bottom
//    ) {
//        tabs.forEachIndexed { index, label ->
//            Column(
//                modifier = Modifier.wrapContentWidth(),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(
//                    text = label,
//                    color = if (index == selectedIndex) Color.Black else Color.Gray
//                )
//
//                if (index == selectedIndex) {
//                    Box(
//                        modifier = Modifier
//                            .padding(top = 4.dp)
//                            .height(3.dp)
//                            .fillMaxWidth()
//                            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
//                            .background(Color(0xFFFF9800)) // Orange indicator
//                    )
//                }
//            }
//        }
//    }
//}
