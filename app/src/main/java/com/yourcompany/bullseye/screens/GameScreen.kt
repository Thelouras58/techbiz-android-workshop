package com.yourcompany.bullseye.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yourcompany.bullseye.R
import com.yourcompany.bullseye.components.GameDetail
import com.yourcompany.bullseye.components.GamePrompt
import com.yourcompany.bullseye.components.ResultDialog
import com.yourcompany.bullseye.components.TargetSlider
import com.yourcompany.bullseye.ui.theme.BullseyeTheme
import com.yourcompany.bullseye.viewmodels.GameScreenViewModel


@Composable
fun GameScreen(
    viewModel: GameScreenViewModel,
    onNavigateToAbout: () -> Unit
) {
    Box {
        Background()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(
                modifier = Modifier.weight(.5f)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.weight(9f)
            ) {
                GamePrompt(
                    targetValue = viewModel.targetValue.collectAsState().value
                )

                TargetSlider(
                    value = viewModel.sliderValue.collectAsState().value,
                    valueChanged = viewModel::updateSliderValue
                )

                Button(
                    onClick = viewModel::onHit,
                    shape = MaterialTheme.shapes.medium,
                    contentPadding = PaddingValues(16.dp)
                ) {
                    Text(text = stringResource(id = R.string.hit_me_button_text))
                }

                GameDetail(
                    totalScore = viewModel.totalScore.collectAsState().value,
                    round = viewModel.currentRound.collectAsState().value,
                    onStartOver = viewModel::startNewGame,
                    onNavigateToAbout = onNavigateToAbout,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.weight(.5f))

            if (viewModel.alertIsVisible.collectAsState().value) {
                ResultDialog(
                    dialogTitle = viewModel.alertTitle,
                    hideDialog = viewModel::hideDialog,
                    sliderValue = viewModel.sliderToInt,
                    points = viewModel.pointsForCurrentRound,
                    onRoundIncrement = viewModel::onRoundIncrement
                )
            }
        }
    }
}

@Composable
private fun Background(){
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.background),
        contentScale = ContentScale.Crop,
        contentDescription = stringResource(R.string.background_image)
    )
}

@Preview(showBackground = true, device = Devices.AUTOMOTIVE_1024p, widthDp = 864, heightDp = 432)
@Composable
fun GameScreenPreview() {
    BullseyeTheme {
        GameScreen(
            viewModel = viewModel(),
            onNavigateToAbout = {}
        )
    }
}