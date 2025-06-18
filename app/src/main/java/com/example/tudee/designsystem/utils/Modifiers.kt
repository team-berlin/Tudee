package com.example.tudee.designsystem.utils

import android.graphics.BlurMaskFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Adds a custom shadow behind a circular shape using blur and offset.
 *
 * @param color The color of the shadow.
 * @param blurRadius The amount of blur to apply to the shadow.
 * @param shape The shape to use for the shadow. Defaults to [CircleShape].
 * @param offsetX Horizontal offset of the shadow.
 * @param offsetY Vertical offset of the shadow.
 * @param scaleX Horizontal scaling factor for the shadow.
 * @param scaleY Vertical scaling factor for the shadow.
 */
fun Modifier.dropShadow(
    color: Color,
    blurRadius: Dp,
    shape: Shape = CircleShape,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 4.dp,
    scaleX: Float = 1f,
    scaleY: Float = 1f,
): Modifier = drawBehind {
    val spreadPx = 0.dp.toPx()
    val blurPx = blurRadius.toPx()
    val offsetXPx = offsetX.toPx()
    val offsetYPx = offsetY.toPx()

    val extraWidth = (size.width + spreadPx) * (scaleX - 1f)
    val extraHeight = (size.height + spreadPx) * (scaleY - 1f)
    val shadowWidth = size.width + spreadPx + extraWidth
    val shadowHeight = size.height + spreadPx + extraHeight

    val shadowSize = Size(shadowWidth, shadowHeight)
    val outline = shape.createOutline(shadowSize, layoutDirection, this)

    val paint = Paint().apply {
        if (blurPx > 0f) {
            asFrameworkPaint().maskFilter = BlurMaskFilter(blurPx, BlurMaskFilter.Blur.NORMAL)
        }
        this.color = color
    }

    drawIntoCanvas { canvas ->
        canvas.save()
        canvas.translate(offsetXPx - extraWidth / 2f, offsetYPx - extraHeight / 2f)
        canvas.drawOutline(outline, paint)
        canvas.restore()
    }
}

/**
 * Applies an inner shadow to the composable using the given shape.
 *
 * @param shape The shape inside which the inner shadow is applied.
 * @param color The shadow color.
 * @param blurRadius The blur radius of the shadow.
 * @param offsetX Horizontal offset for the shadow.
 * @param offsetY Vertical offset for the shadow.
 * @param spread Extra size to increase the shadow area.
 */
fun Modifier.innerShadow(
    shape: Shape,
    color: Color = Color.Black,
    blurRadius: Dp,
    offsetX: Dp = 2.dp,
    offsetY: Dp = 2.dp,
    spread: Dp = 0.dp,
): Modifier = drawWithContent {
    // Draw the actual content first
    drawContent()

    val blurPx = blurRadius.toPx()
    val offsetXPx = offsetX.toPx()
    val offsetYPx = offsetY.toPx()
    val spreadPx = spread.toPx()

    val shadowSize = Size(
        width = size.width + spreadPx,
        height = size.height + spreadPx
    )

    val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)
    val paint = Paint()

    drawIntoCanvas { canvas ->
        paint.color = color

        // Create an off-screen layer to isolate the blending effect
        canvas.saveLayer(size.toRect(), paint)

        // Step 1: Draw the full shape as a base layer
        canvas.drawOutline(shadowOutline, paint)

        // Step 2: Configure paint for shadow mask
        val frameworkPaint = paint.asFrameworkPaint().apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
            if (blurPx > 0f) {
                maskFilter = BlurMaskFilter(blurPx, BlurMaskFilter.Blur.NORMAL)
            }
        }

        // Step 3: Draw the same outline offset and blurred (to punch out the shadow)
        paint.color = Color(0x00000000)
        canvas.translate(offsetXPx, offsetYPx)
        canvas.drawOutline(shadowOutline, paint)

        canvas.restore()
    }
}