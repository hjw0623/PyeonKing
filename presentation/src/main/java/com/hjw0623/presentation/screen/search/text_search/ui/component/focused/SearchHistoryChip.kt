package com.hjw0623.presentation.screen.search.text_search.ui.component.focused

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchHistoryChip(
    text: String,
    onClick: () -> Unit,
    onRemove: () -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        tonalElevation = 1.dp,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        color = MaterialTheme.colorScheme.surfaceVariant,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .clickable { onRemove() }
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = text, style = MaterialTheme.typography.bodySmall)
        }
    }
}