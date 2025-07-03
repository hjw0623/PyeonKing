package com.hjw0623.presentation.screen.mypage.mypage_main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme

@Composable
fun MyPageMenuListItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    menuName: String,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxWidth(),

        ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(8.dp),

            ) {
            Icon(
                imageVector = icon,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = menuName,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowRight,
                contentDescription = null
            )

        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.LightGray))
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPageMenuListItemPreview() {
    PyeonKingTheme {
        MyPageMenuListItem(
            icon = Icons.Default.AccountCircle,
            menuName = "메뉴1"
        )
    }
}