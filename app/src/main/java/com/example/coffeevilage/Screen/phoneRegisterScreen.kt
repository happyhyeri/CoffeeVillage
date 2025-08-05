package com.example.coffeevilage.Screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.coffeevilage.R
import androidx.compose.ui.unit.sp
import com.example.coffeevilage.ViewModel.StateViewModel
import com.example.coffeevilage.ViewModel.UserViewModel
import com.example.coffeevilage.Widget.BackTopBar
import com.example.coffeevilage.Widget.CommonAlertDialog

@Composable
fun phoneRegisterScreen(
    stateViewModel: StateViewModel,
    userViewModel: UserViewModel,
    navController: NavHostController
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.brown_3))
            .padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        BackTopBar("휴대폰 번호 등록", navController = navController)
        phoneNumberSection(stateViewModel, userViewModel)

    }
    if (stateViewModel.isValidPhoneNumber) {
        CommonAlertDialog(
            title = "등록 완료",
            content = "휴대폰 번호가 등록되었습니다.",
            textColor = colorResource(R.color.dark_brown),
            backgroundColor = colorResource(R.color.brown_white),
            btnTextColor = colorResource(R.color.dark_brown),
            onDismiss = { stateViewModel.isValidPhoneNumber = false },
            onClickEvent = { navController.popBackStack() }
        )
    }
    if (stateViewModel.isNotValidPhoneNumber) {
        CommonAlertDialog(
            title = "확인",
            content = "휴대폰 번호 형식이 올바르지 않습니다.",
            textColor = colorResource(R.color.dark_brown),
            backgroundColor = colorResource(R.color.brown_white),
            btnTextColor = colorResource(R.color.dark_brown),
            onDismiss = { stateViewModel.isNotValidPhoneNumber = false },
        )
    }

}

@Composable
fun phoneNumberSection(stateViewModel: StateViewModel, userViewModel: UserViewModel) {
    val focusManager = LocalFocusManager.current
    val roundValue = 10.dp
    var inputNumber by remember { mutableStateOf(userViewModel.registeredPhoneNumber) }
    var isFocused by remember { mutableStateOf(false) }
    val context = LocalContext.current



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.brown_3))
            .padding(15.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        Text(
            text = "커피빌리지는 휴대폰 번호로 포인트 적립을 해요. \n번호는 안전하게 보관되며 어디에도 공개되지 않아요.",
            color = colorResource(R.color.brown_white),
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(20.dp))

        BasicTextField(
            value = inputNumber,
            onValueChange = { inputNumber = it },
            modifier = Modifier.onFocusChanged { focusState ->
                isFocused = focusState.isFocused
                Log.d("isfocus", "${isFocused}")

            },
            singleLine = true,
            textStyle = TextStyle(
                color = colorResource(R.color.brown_1),
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            cursorBrush = SolidColor(colorResource(R.color.brown_1)),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .background(color = Color.White, RoundedCornerShape(roundValue))
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start

                ) {
                  if (inputNumber.isBlank() && !isFocused) {
                        Text(
                            "휴대폰 번호 (- 없이 숫자만 입력) ",
                            color = colorResource(R.color.brown_3), fontSize = 13.sp
                        )
                    }
                    innerTextField()
                }

            },
        )
        Spacer(modifier = Modifier.height(7.dp))

        Button(
            onClick = {
                if (inputNumber.isEmpty()) {
                   // Toast.makeText(context, "휴대폰 번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                    userViewModel.savePhoneNumber(inputNumber)

                } else {
                    ValidationAndRegisterPhoneNumber(inputNumber, stateViewModel, userViewModel)
                }
                focusManager.clearFocus()

            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.brown_1),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(roundValue),
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
        ) {
            Text(
                "완 료",
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.brown_white)
            )
        }
    }
}


fun ValidationAndRegisterPhoneNumber(
    phoneNumber: String,
    stateViewModel: StateViewModel,
    userViewModel: UserViewModel
) {

    val phoneRegex = "^01[0|1|6|7|8|9][0-9]{7,8}$".toRegex()
    if (phoneRegex.matches(phoneNumber)) {
        stateViewModel.isValidPhoneNumber = true
        userViewModel.savePhoneNumber(phoneNumber)
    } else {
        stateViewModel.isNotValidPhoneNumber = true
    }
}


