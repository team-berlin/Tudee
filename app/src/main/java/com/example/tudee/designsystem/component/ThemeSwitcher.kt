package com.example.tudee.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.designsystem.utils.dropShadow
import com.example.tudee.designsystem.utils.innerShadow
import kotlinx.coroutines.launch


@Composable
fun ThemeSwitcher(
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChanged: (Boolean) -> Unit
) {

    val animationDuration = 700

    val valueToOffset = if (isChecked) 0f else 1f
    val offset = remember { Animatable(valueToOffset) }
    val scope = rememberCoroutineScope()

    DisposableEffect(isChecked) {
        if (offset.targetValue != valueToOffset) {
            scope.launch {
                offset.animateTo(valueToOffset, animationSpec = tween(animationDuration))
            }
        }
        onDispose { }
    }

    val blueSky = Color(0xFF151535)
    val morningSky = TudeeTheme.color.primary

    Box(
        modifier = modifier
            .size(width = 64.dp, height = 36.dp)
            .clip(RoundedCornerShape(100.dp))
            .background(lerp(blueSky, morningSky, offset.value))
            .border(1.dp, Color(0x1F1F1F1F), RoundedCornerShape(100.dp))
            .padding(horizontal = 2.dp)
            .toggleable(
                value = isChecked,
                onValueChange = onCheckedChanged,
                role = Role.Switch,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
    ) {

        DarkComponents(isChecked, animationDuration)

        LightComponents(isChecked, animationDuration)

    }
}

@Composable
private fun BoxScope.LightComponents(
    isChecked: Boolean,
    animationDuration: Int
) {
    AnimatedSun(isChecked, animationDuration)

    TopGrayCloud(isChecked, animationDuration)

    BottomGrayCloud(isChecked, animationDuration)

    TopWhiteCloud(isChecked, animationDuration)

    MiddleWhiteCloud(isChecked, animationDuration)

    BottomWhiteCloud(isChecked, animationDuration)
}


@Composable
private fun AnimatedSun(
    isDarkMode: Boolean,
    animationDuration: Int
) {
    Box(Modifier.fillMaxSize()) {
        AnimatedVisibility(
            !isDarkMode,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = animationDuration,
                    easing = EaseOut
                )
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(
                    durationMillis = animationDuration,
                    easing = EaseOut
                )
            ) + fadeOut(
                animationSpec = tween(
                    durationMillis = animationDuration,
                    easing = EaseOut
                )
            ),
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            SunIcon()
        }
    }
}

@Composable
private fun SunIcon() {
    Box(
        modifier = Modifier
            .dropShadow(
                color = Color(0xFF79A4FD),
                blurRadius = 3.dp,
                offsetX = 3.dp,
                offsetY = (-1).dp,
            )
    ) {
        Image(
            painter = painterResource(R.drawable.ic_sun),
            contentDescription = stringResource(R.string.switch_sun_icon),
        )
    }
}

@Composable
private fun DarkComponents(
    isDarkMode: Boolean,
    animationDuration: Int
) {
    Box {
        AnimatedVisibility(
            isDarkMode,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = animationDuration,
                    easing = EaseOut
                )
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(
                    durationMillis = animationDuration,
                    easing = EaseOut
                )
            ) + fadeOut(
                animationSpec = tween(
                    durationMillis = animationDuration,
                    easing = EaseOut
                )
            ),
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            MoonIcon()
        }
        AnimatedVisibility(
            isDarkMode,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = animationDuration,
                    easing = EaseOut
                )
            ),
            exit = fadeOut(
                animationSpec = tween(
                    durationMillis = animationDuration,
                    easing = EaseOut
                )
            ),
        ) {
            StarsAndMoonCircles()
        }
    }
}

@Composable
private fun StarsAndMoonCircles() {
    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 2.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_stars),
                contentDescription = stringResource(R.string.switch_stars_icon),
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(width = 28.dp, height = 27.dp)
            )
        }

        Box(
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.CenterEnd)
        ) {
            MoonCircle(
                size = 4.dp,
                alignment = Alignment.BottomEnd,
                offsetX = (-9).dp,
                offsetY = (-4).dp,
                blurRadius = 2.dp
            )

            MoonCircle(
                size = 8.dp,
                alignment = Alignment.TopStart,
                offsetX = 10.dp,
                offsetY = 2.dp,
                blurRadius = 3.dp
            )
            MoonCircle(
                size = 14.dp,
                alignment = Alignment.BottomStart,
                offsetX = 4.dp,
                offsetY = (-6).dp,
                blurRadius = 4.dp
            )
        }
    }
}

@Composable
private fun MoonIcon() {
    Box(
        modifier = Modifier
            .dropShadow(
                offsetX = (-1).dp,
                offsetY = 1.dp,
                blurRadius = 4.dp,
                color = Color(0xFF323297),
            )
    ) {
        Image(
            painter = painterResource(R.drawable.ic_moon),
            contentDescription = stringResource(R.string.switch_moon_icon),
        )
    }
}

@Composable
private fun BoxScope.MoonCircle(
    size: Dp,
    alignment: Alignment,
    offsetX: Dp,
    offsetY: Dp,
    blurRadius: Dp
) {
    Box(
        modifier = Modifier
            .size(size)
            .align(alignment)
            .offset(x = offsetX, y = offsetY)
            .innerShadow(
                shape = CircleShape,
                color = Color(0xFFBFD2FF),
                blurRadius = blurRadius,
                offsetX = 1.dp,
                offsetY = 1.dp
            )
    )
}


@Composable
private fun BoxScope.TopWhiteCloud(isDarkMode: Boolean, animationDuration: Int) {
    val animationSpec = tween<IntOffset>(
        durationMillis = animationDuration,
        easing = EaseOut
    )

    val scaleSpec = tween<Float>(
        durationMillis = animationDuration,
        easing = EaseOut
    )

    val offset = IntOffset(
        x = -(54),
        y = -(18)
    )

    AnimatedVisibility(
        visible = !isDarkMode,
        enter = slideIn(
            initialOffset = { offset },
            animationSpec = animationSpec
        ) + scaleIn(
            initialScale = 0.15f,
            animationSpec = scaleSpec
        ) + fadeIn(animationSpec = tween(animationDuration, easing = EaseOut)),

        exit = slideOut(
            targetOffset = { offset },
            animationSpec = animationSpec
        ) + scaleOut(
            targetScale = 0.15f,
            animationSpec = scaleSpec
        ) + fadeOut(animationSpec = tween(animationDuration, easing = EaseOut)),

        modifier = Modifier
            .align(Alignment.BottomEnd)
            .offset(x = 14.dp, y = (-7).dp)
    ) {
        Box(
            modifier = Modifier
                .size(29.dp)
                .background(Color.White, RoundedCornerShape(100.dp))
        )
    }
}

@Composable
private fun MiddleWhiteCloud(isDarkMode: Boolean, animationDuration: Int) {
    Box(Modifier.fillMaxSize()) {
        AnimatedCloud(
            size = 16.dp,
            modifier = Modifier.align(Alignment.BottomEnd),
            isDarkMode = isDarkMode,
            animationDuration = animationDuration,
            startOffsetX = 1.dp,
            targetOffsetX = 50.dp,
            startOffsetY = 1.dp,
            targetOffsetY = 50.dp
        )
    }
}

@Composable
private fun BottomWhiteCloud(
    isDarkMode: Boolean,
    animationDuration: Int
) {
    Box(Modifier.fillMaxSize()) {

        val animationSpec = tween<IntOffset>(
            durationMillis = animationDuration,
            easing = EaseOut
        )

        val scaleSpec = tween<Float>(
            durationMillis = animationDuration,
            easing = EaseOut
        )


        AnimatedVisibility(
            visible = !isDarkMode,
            enter = slideIn(
                initialOffset = { fullSize ->
                    IntOffset(
                        x = (-1.5f * fullSize.width).toInt(),
                        y = (fullSize.height / 2f).toInt()
                    )
                },
                animationSpec = animationSpec
            ) + scaleIn(
                initialScale = 0.5f,
                animationSpec = scaleSpec
            ) + fadeIn(animationSpec = tween(animationDuration, easing = EaseOut)),

            exit = slideOut(
                targetOffset = { fullSize ->
                    IntOffset(
                        x = (-1.5f * fullSize.width).toInt(),
                        y = (fullSize.height / 2f).toInt()
                    )
                },
                animationSpec = animationSpec
            ) + scaleOut(
                targetScale = 0.5f,
                animationSpec = scaleSpec
            ) + fadeOut(animationSpec = tween(animationDuration, easing = EaseOut)),

            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-12).dp, y = 3.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(14.dp, 16.dp)
                    .background(Color.White, RoundedCornerShape(100.dp))
            )
        }
    }
}

@Composable
private fun BottomGrayCloud(isDarkMode: Boolean, animationDuration: Int) {
    Box(Modifier.fillMaxSize()) {
        AnimatedCloud(
            isDarkMode = isDarkMode,
            modifier = Modifier.align(Alignment.BottomEnd),
            size = 24.dp,
            animationDuration = animationDuration,
            startOffsetX = (-5).dp,
            targetOffsetX = 50.dp,
            startOffsetY = 6.dp,
            targetOffsetY = 50.dp,
            color = Color(0xFFF0F0F0),
        )
    }
}

@Composable
private fun TopGrayCloud(isDarkMode: Boolean, animationDuration: Int) {
    Box(Modifier.fillMaxSize()) {
        AnimatedCloud(
            isDarkMode = isDarkMode,
            modifier = Modifier.align(Alignment.TopEnd),
            animationDuration = animationDuration,
            size = 32.dp,
            startOffsetX = 14.dp,
            targetOffsetX = 50.dp,
            startOffsetY = (-4).dp,
            targetOffsetY = 50.dp,
            color = Color(0xFFF0F0F0),
        )
    }
}

@Composable
private fun AnimatedCloud(
    size: Dp,
    modifier: Modifier = Modifier,
    isDarkMode: Boolean = false,
    color: Color = Color.White,
    startOffsetX: Dp,
    targetOffsetX: Dp,
    startOffsetY: Dp,
    targetOffsetY: Dp,
    animationDuration: Int,
    hasInnerShadow: Boolean = false
) {

    val animationDpSpec: AnimationSpec<Dp> =
        tween(durationMillis = animationDuration, easing = EaseOut)

    val offsetX by animateDpAsState(
        targetValue = if (isDarkMode) targetOffsetX else startOffsetX,
        animationSpec = animationDpSpec
    )

    val offsetY by animateDpAsState(
        targetValue = if (isDarkMode) targetOffsetY else startOffsetY,
        animationSpec = animationDpSpec
    )

    val innerShadowColor by animateColorAsState(
        targetValue = if (isDarkMode) Color(0xFFBFD2FF) else Color.Transparent,
        animationSpec = tween(durationMillis = animationDuration, easing = EaseOut)
    )

    Box(
        modifier = modifier
            .size(size)
            .offset(x = offsetX, y = offsetY)
            .background(color, CircleShape)
            .then(
                if (hasInnerShadow) Modifier.innerShadow(
                    shape = CircleShape,
                    color = innerShadowColor,
                    blurRadius = 4.dp,
                    offsetX = 1.dp,
                    offsetY = 1.dp
                ) else Modifier
            )
    )
}

@Preview(showBackground = true)
@Composable
private fun ThemeSwitcherPreview() {
    TudeeTheme {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {

            var checkedState by remember { mutableStateOf(false) }

            ThemeSwitcher(
                checkedState,
            ) { checkedState = !checkedState }

            ThemeSwitcher(
                !checkedState,
            ) { checkedState = !checkedState }
        }
    }
}
