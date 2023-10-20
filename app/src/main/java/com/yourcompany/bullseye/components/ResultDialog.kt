package com.yourcompany.bullseye.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.yourcompany.bullseye.R

@Composable
fun ResultDialog(
    hideDialog: () -> Unit,
    dialogTitle: Int,
    onRoundIncrement: () -> Unit,
    sliderValue: Int,
    points: Int,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {
            hideDialog()
            onRoundIncrement()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    hideDialog()
                    onRoundIncrement()
                }
            ) {
                Text(stringResource(id = R.string.result_dialog_button_text))
            }
        },
        title = { Text(stringResource(id = dialogTitle)) },
        text = { Text(stringResource(id = R.string.result_dialog_message, sliderValue, points)) }
    )
}