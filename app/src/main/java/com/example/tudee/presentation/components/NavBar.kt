package com.example.tudee.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import com.example.tudee.designsystem.theme.TudeeTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R

data class BottomNavItem(
    val icon: Int,
    val selectedIcon: Int,
    val route: String
)


@Composable
fun NavBar(
    items: List<BottomNavItem>,
    currentRoute: String,
    onItemClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = TudeeTheme.color.surfaceHigh,
    selectedItemBackgroundColor: Color = TudeeTheme.color.primaryVariant,
    activeIconColor: Color = TudeeTheme.color.primary,
    inactiveIconColor: Color = Color.Unspecified,
    shadowColor: Color = TudeeTheme.color.shadow,
    shadowElevation: Int = 2,
    shapeWhenSelected: RoundedCornerShape = RoundedCornerShape(16.dp),
    shapeWhenNotSelected: RoundedCornerShape = RoundedCornerShape(100.dp),
    navItemBoxSize: Int = 42,
    iconSize: Int = 24,
    rippleColor: Color = TudeeTheme.color.surfaceHigh,
    paddingValues: PaddingValues =PaddingValues(horizontal = 32.dp, vertical = 16.dp),
    iconAlignment: Alignment = Alignment.Center
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = shadowElevation.dp,
                clip = false,
                ambientColor = shadowColor,
                spotColor = shadowColor
            )
            .background(color = backgroundColor)
            .padding(paddingValues),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route

            Box(
                modifier = Modifier
                    .size(navItemBoxSize.dp)
                    .background(
                        color = if (isSelected) selectedItemBackgroundColor else backgroundColor,
                        shape = if (isSelected) shapeWhenSelected else shapeWhenNotSelected
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(
                            bounded = true,
                            radius = (navItemBoxSize / 2).dp,
                            color = rippleColor
                        )
                    ) { onItemClicked(item.route) },
                contentAlignment = iconAlignment
            ) {
                Icon(
                    painter = painterResource(id = if (isSelected) item.selectedIcon else item.icon),
                    contentDescription = item.route,
                    modifier = Modifier.size(iconSize.dp),
                    tint = if (isSelected) activeIconColor else inactiveIconColor
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NavBarPreview() {
    NavBar(
        items = listOf(
            BottomNavItem(R.drawable.home, R.drawable.home_select, "home"),
            BottomNavItem(R.drawable.task, R.drawable.task_select, "task"),
            BottomNavItem(R.drawable.category, R.drawable.category_select, "category")
        ),
        currentRoute = "category",
        onItemClicked = {}
    )
}
