package com.vincicent.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.vincicent.core.presentation.designsystem.RunAppTheme

@Composable
fun RunAppDialog(
    title: String,
    onDismiss: () -> Unit,
    description: String,
    primaryButton: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    secondaryButton: @Composable RowScope.() -> Unit = {}
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = description,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                secondaryButton()
                primaryButton()
            }
        }
    }
}

@Preview
@Composable
private fun RunAppDialogPreview() {
    RunAppTheme {
        RunAppDialog(
            title = "test",
            onDismiss = {},
            description = "This is a test",
            primaryButton = {
                RunAppActionButton(
                    text = "primaryButton",
                    isLoading = false,
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                )
            },
            secondaryButton = {
                RunAppActionButton(
                    text = "secondaryButton",
                    isLoading = false,
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                )
            }
        )
    }
}