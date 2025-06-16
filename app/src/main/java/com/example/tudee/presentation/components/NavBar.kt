package com.example.tudee.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme

data class BottomNavItem(
    val icon: Painter,
    val selectedIcon: Painter,
    val route: String
)

@Composable
fun NavBar(
    navDestinations: List<BottomNavItem>,
    currentRoute: String,
    onNavDestinationClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = TudeeTheme.color.surfaceHigh,
    selectedItemBackgroundColor: Color = TudeeTheme.color.primaryVariant,
    activeIconColor: Color = TudeeTheme.color.primary,
    inactiveIconColor: Color = Color.Unspecified,
    shadowColor: Color = Color(0x14000000),
    rippleColor: Color = TudeeTheme.color.surfaceHigh,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                clip = false,
                ambientColor = shadowColor,
                spotColor = shadowColor
            )
            .background(color = backgroundColor)
            .padding(horizontal = 32.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        navDestinations.forEach { item ->
            val isSelected = currentRoute == item.route
            val backgroundColorAnimated by animateColorAsState(
                targetValue = if (isSelected) selectedItemBackgroundColor else backgroundColor,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(
                        color = backgroundColorAnimated,
                        shape = if (isSelected) RoundedCornerShape(16.dp) else RoundedCornerShape(
                            100.dp
                        )
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(
                            bounded = true,
                            radius = (42 / 2).dp,
                            color = rippleColor
                        )
                    ) { onNavDestinationClicked(item.route) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = if (isSelected) item.selectedIcon else item.icon,
                    contentDescription = item.route,
                    modifier = Modifier.size(24.dp),
                    tint = if (isSelected) activeIconColor else inactiveIconColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NavBarPreview() {
    TudeeTheme {
        NavBar(
            navDestinations = listOf(
                BottomNavItem(
                    icon = painterResource(id = R.drawable.home),
                    selectedIcon = painterResource(id = R.drawable.home_select),
                    route = "home"
                ),
                BottomNavItem(
                    icon = painterResource(id = R.drawable.task),
                    selectedIcon = painterResource(id = R.drawable.task_select),
                    route = "search"
                ),
                BottomNavItem(
                    icon = painterResource(id = R.drawable.category),
                    selectedIcon = painterResource(id = R.drawable.category_select),
                    route = "profile"
                )
            ),
            currentRoute = "home",
            onNavDestinationClicked = {}
        )
    }
}