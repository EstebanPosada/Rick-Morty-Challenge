package com.estebanposada.rickmorty_app.ui.screens.common

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StatusBadge(modifier: Modifier = Modifier, text: String, color: Color) {
    Surface(
        modifier = modifier,
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(50)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 4.dp),
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = color,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun StatusBadgePreview() {
    StatusBadge(text = "text", color = Color.Blue)
}
