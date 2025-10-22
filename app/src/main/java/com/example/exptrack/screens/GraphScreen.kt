package com.example.exptrack.screens

import android.graphics.Paint
import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.exptrack.viewModels.GraphScreenViewModel
import com.example.exptrack.viewModels.GraphScreenViewModelFactory
import com.example.exptrack.viewModels.GraphUiState


@Composable
fun GraphScreen(navigator: NavHostController) {

    // Instantiate ViewModel using its factory
    val viewModel: GraphScreenViewModel = viewModel(factory = GraphScreenViewModelFactory(
        LocalContext.current
    )
    )
    // Collect state from the ViewModel
    val uiState by viewModel.uiState.collectAsState()

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }
                uiState.hasData -> {
                    // Pass the state to the graph composable
                    UserMonthGraph(state = uiState)
                }
                else -> {
                    Text("No expense data to display.", fontSize = 18.sp)
                }
            }
        }

    }
}


@Composable
fun UserMonthGraph(modifier: Modifier = Modifier, state: GraphUiState) {
    val xValues = state.xValues
    val yValues = state.yValues
    val points = state.points
    val interval = state.interval

    Box(
        modifier = modifier.size(350.dp), // Increased size for better visibility
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {

            val textPaint = Paint().apply {
                textSize = 30f // Adjusted for clarity
                color = Color.Black.toArgb()
                textAlign = Paint.Align.CENTER
            }

            // Corrected max value logic
            val maxX = xValues.maxOrNull() ?: 0
            val xSpacing = if (maxX > 0) size.width / (maxX + 1) else 0f // Avoid division by zero

            val maxY = yValues.maxOrNull() ?: 0
            val ySpacing = if (maxY > 0) size.height / (maxY + 50) else 0f // Added buffer

            // Draw X-axis labels (days)
            xValues.forEach { index ->
                drawContext.canvas.nativeCanvas.drawText(
                    index.toString(),
                    xSpacing * index,
                    size.height,
                    textPaint
                )
            }
            // Draw Y-axis labels (amounts)
            yValues.forEach { value ->
                drawContext.canvas.nativeCanvas.drawText(
                    value.toString(),
                    0f,
                    size.height - (ySpacing * value),
                    textPaint
                )
            }

            val coordinates = mutableListOf<PointF>()

            // Populate coordinates list first
            points.forEachIndexed { index, value ->
                val x = xSpacing * xValues[index]
                val y = size.height - (ySpacing * value)
                coordinates.add(PointF(x, y))
            }

            // Draw circles on data points
            coordinates.forEach { point ->
                drawCircle(Color(0xFF3F51B5), radius = 10f, center = Offset(point.x, point.y))
            }

            // Ensure there are points to draw a path
            if (coordinates.size < 2) return@Canvas

            // Calculate control points for the curve
            val controlPoints = calculateControlPoints(coordinates)

            val path = Path().apply {
                reset()
                moveTo(
                    coordinates.first().x,
                    coordinates.first().y
                )
                for (i in 1 until coordinates.size) {
                    val p2 = controlPoints[i - 1] // Corrected index
                    cubicTo(
                        p2.first.x, p2.first.y,
                        p2.second.x, p2.second.y,
                        coordinates[i].x, coordinates[i].y
                    )
                }
            }
            // Draw the curved line
            drawPath(path, color = Color(0xFFFF5252), style = Stroke(width = 8f))
        }
    }
}

fun calculateControlPoints(points: List<PointF>): List<Pair<PointF, PointF>> {
    val controlPoints = mutableListOf<Pair<PointF, PointF>>()
    for (i in 0 until points.size - 1) {
        val p0 = points.getOrNull(i - 1) ?: points[i]
        val p1 = points[i]
        val p2 = points[i + 1]
        val p3 = points.getOrNull(i + 2) ?: p2

        val tension = 0.4f
        val cp1x = p1.x + (p2.x - p0.x) * tension / 2
        val cp1y = p1.y + (p2.y - p0.y) * tension / 2
        val cp2x = p2.x - (p3.x - p1.x) * tension / 2
        val cp2y = p2.y - (p3.y - p1.y) * tension / 2

        controlPoints.add(Pair(PointF(cp1x, cp1y), PointF(cp2x, cp2y)))
    }
    return controlPoints
}