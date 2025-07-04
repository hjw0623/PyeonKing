package com.hjw0623.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme

@Composable
fun PyeonKingTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    startIcon: ImageVector? = null,
    endIcon: ImageVector? = null,
    onEndIconClick: (() -> Unit)? = null,
    hint: String = "",
    title: String? = null,
    error: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    additionalInfo: String? = null
) {
    var isFocused by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (title != null) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (error != null) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            } else if (additionalInfo != null) {
                Text(
                    text = additionalInfo,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(
                    if (isFocused) {
                        MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.05f
                        )
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                )
                .border(
                    width = 1.dp,
                    color = if (isFocused) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.Transparent
                    },
                    shape = RoundedCornerShape(16.dp)
                )
                .onFocusChanged {
                    isFocused = it.isFocused
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (startIcon != null) {
                    Icon(
                        imageVector = startIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    if (value.isEmpty() && !isFocused) {
                        Text(
                            text = hint,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                alpha = 0.4f
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        textStyle = LocalTextStyle.current.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = keyboardType
                        ),
                        singleLine = true,
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                        visualTransformation = visualTransformation
                    )
                }
                if (endIcon != null) {
                    Spacer(modifier = Modifier.width(16.dp))
                    val iconModifier = if (onEndIconClick != null) {
                        Modifier.clickable(onClick = onEndIconClick)
                    } else {
                        Modifier
                    }
                    Icon(
                        imageVector = endIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = iconModifier
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PyeonKingTextFieldPreview() {
    PyeonKingTheme {
        PyeonKingTextField(
            value = "",
            onValueChange = {},
            startIcon = Icons.Default.Email,
            endIcon = Icons.Default.Check,
            hint = "example@test.com",
            title = "이메일",
            additionalInfo = "이메일을 입력해주세요",
            modifier = Modifier.fillMaxWidth()
        )
    }
}