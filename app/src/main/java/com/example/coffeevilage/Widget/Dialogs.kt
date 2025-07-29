package com.example.coffeevilage.Widget

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import com.example.coffeevilage.R

@Composable
fun CallDialog(
    onClickYes: () -> Unit,
    onClickNo: () -> Unit
) {
    val backgroundColor = colorResource(R.color.brown_white)
    val textColor = colorResource(R.color.dark_brown)

    Dialog(
        onDismissRequest = { onClickNo() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
        )
        {
            Column(
                modifier = Modifier
                    .width(300.dp)
                    .wrapContentHeight()
                    .background(
                        color = backgroundColor,
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "전화 주문",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        color = textColor,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "커피빌리지 매장으로 전화하시겠습니까?",
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        color = textColor,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Spacer(modifier = Modifier.height(40.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = colorResource(R.color.dark_brown))
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min) // Row의 높이를 내부 컴포넌트에 맞춤
                ) {
                    Button(
                        onClick = { onClickYes() },
                        shape = RectangleShape,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = backgroundColor, // 버튼 배경색상
                            contentColor = textColor, // 버튼 텍스트 색상
                        ),

                        ) {
                        Text(
                            text = "네",
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                            .background(colorResource(R.color.dark_brown))
                    )

                    Button(
                        onClick = { onClickNo() },
                        shape = RectangleShape,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = backgroundColor, // 버튼 배경색상
                            contentColor = textColor, // 버튼 텍스트 색상
                        ),
                    ) {
                        Text(
                            text = "아니요",
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }
        }
    }
}


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun OrderDialog(
    onDismiss: () -> Unit,
    onTakeOutClick: () -> Unit,
    onDrinkHereClick: () -> Unit
) {

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val bottomNavHeight = 56.dp

    Popup(
        alignment = Alignment.BottomCenter,
        onDismissRequest = onDismiss
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(screenHeight - bottomNavHeight)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }//컴파일 에러
                ) { onDismiss() }
                .background(Color.Black.copy(alpha = 0.3f)),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {

                OrderMethodCard(
                    text = "가지고 갈래요",
                    imgRes = R.drawable.walk,
                    onclick = onTakeOutClick
                )
                OrderMethodCard(
                    text = "매장에서 마실래요",
                    imgRes = R.drawable.inside,
                    onclick = onDrinkHereClick
                )

            }
        }
    }
}


@Composable
fun CommonAlertDialog(
    title: String,
    content: String,
    textColor: Color,
    backgroundColor: Color,
    btnTextColor: Color,
    onDismiss: () -> Unit,
    onClickEvent: (() -> Unit)? = null

) {
    AlertDialog(
        title = {
            Text(text = title, color = textColor)
        },
        text = {
            Text(text = content, color = textColor)
        },
        onDismissRequest = {
            if (onClickEvent != null) {
                onClickEvent()
            }
            onDismiss()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (onClickEvent != null) {
                        onClickEvent()
                    }
                    onDismiss()
                }
            ) {
                Text("확인", color = btnTextColor, fontWeight = FontWeight.SemiBold)
            }
        },
        shape = RoundedCornerShape(20.dp),

        modifier = Modifier.background(backgroundColor)
    )
}


@Preview
@Composable
fun showPreview() {
    CommonAlertDialog(
        "완료",
        "완료되었습니다.",
        colorResource(R.color.dark_brown),
        Color.White,
        Color.Red,
        {})
}