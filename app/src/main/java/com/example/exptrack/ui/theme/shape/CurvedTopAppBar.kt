package com.example.exptrack.ui.theme.shape

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class CurvedTopAppBar(private val waveHeightRatio: Float = 0.2f) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path  = androidx.compose.ui.graphics.Path()

        val width = size.width
        val height = size.height

        val waveHeight = height * waveHeightRatio
        val standardHeight = height - waveHeight

        path.moveTo(0f,0f)
        path.lineTo(width,0f)
        path.lineTo(width, standardHeight)
        path.quadraticTo(
            width/2f, height,
            0f, standardHeight
        )
        path.close()

        return Outline.Generic(path)
    }
}