package com.example.shelfy.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shelfy.R
import com.example.shelfy.ui.theme.BlackBar
import com.example.shelfy.ui.theme.BlueText
import com.example.shelfy.ui.theme.fonts

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    fontSize : TextUnit = 24.sp,
    padding : Dp = 8.dp)
{
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Text(
            text = stringResource(R.string.shelfy),
            color = BlueText,
            fontFamily = fonts,
            fontSize = fontSize,
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding),
            textAlign = TextAlign.Center
        )
    }
}