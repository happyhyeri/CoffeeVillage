package com.example.coffeevilage.Widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevilage.Data.Menu
import com.example.coffeevilage.R
import com.example.coffeevilage.ViewModel.CartViewModel

@Composable
fun OrderDetailPage_ShotOption(
    menu: Menu,
    cartViewModel: CartViewModel,
    onDismiss: () -> Unit
) {

    var selectedSize by remember { mutableStateOf("기본") }
    var selectedShot by remember { mutableStateOf("기본") }

    var isLarge by remember { mutableStateOf(false) }
    var isShotAdd by remember { mutableStateOf(false) }
    var BasePrice by remember { mutableStateOf(menu.price) } //메뉴의 기본 가격
    val price = //옵션 적용 가격
        remember { derivedStateOf { BasePrice + if (isLarge && isShotAdd) 1000 else if (isLarge || isShotAdd) 500 else 0 } }
    var count by remember { mutableStateOf(1) }


    val spacer = 10.dp
    val itemSpacer = 20.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
            .background(colorResource(R.color.brown_3))
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Text(
                text = menu.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(vertical = 8.dp),
                color = colorResource(R.color.dark_brown)
            )

            IconButton(
                onClick = { onDismiss() },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "닫기",
                    tint = colorResource(R.color.dark_brown)
                )
            }
        }

        Spacer(Modifier.height(itemSpacer))

        //1. 사이즈 선택
        Text(
            "사이즈",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = colorResource(R.color.dark_brown)
        )
        Spacer(Modifier.height(spacer))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("기본", "라지").forEach { size ->
                Button(
                    onClick = {
                        selectedSize = size
                        if (selectedSize == "라지") isLarge = true else isLarge = false

                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedSize == size) Color.White else Color(
                            73f,
                            54f,
                            40f,
                            0.42f
                        ),
                        contentColor = if (selectedSize == size) colorResource(R.color.brown_2) else Color.White
                    ),
                    shape = RoundedCornerShape(0.dp),
                    modifier = Modifier
                        .weight(1f)
                        .border(width = 1.5.dp, color = colorResource(R.color.brown_2))
                        .height(50.dp),
                    contentPadding = PaddingValues(1.dp),
                ) {
                    Text(text = size, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(Modifier.height(itemSpacer))


        //2. 샷 선택
        Text(
            "샷 선택",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = colorResource(R.color.dark_brown)
        )
        Spacer(Modifier.height(spacer))
        listOf("기본", "연하게", "샷 추가").forEach { shot ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                RadioButton(
                    selected = selectedShot == shot,
                    onClick = {
                        selectedShot = shot
                        if (selectedShot == "샷 추가") isShotAdd = true else isShotAdd = false

                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = colorResource(R.color.dark_brown),
                        unselectedColor = colorResource(R.color.brown_white)
                    )
                )
                Text(
                    shot,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = colorResource(R.color.dark_brown),
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }

        Spacer(Modifier.height(itemSpacer))


        //3. 수량 선택
        Text(
            "수량",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = colorResource(R.color.dark_brown)
        )
        Spacer(Modifier.height(itemSpacer))


        QuantitySelectorSection(
            price = price.value,
            count = count,
            buttonBackgroundColor = Color.White,
            buttonTextColor = colorResource(R.color.dark_brown),
            textColor = colorResource(R.color.dark_brown),
            quantityFontSize = 20,
            priceFontSize = 30,
            buttonSize = 30,
            buttonTextSize = 15,
            horizontalArrangeMent = Arrangement.SpaceBetween,
            onBtnClick = { quantity, price, calculator ->
                count = quantity
            }
        )

        //버튼
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            //바로 주문 Btn
            Button(
                onClick = {},
                modifier = Modifier
                    .weight(1f)
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(6.dp),
            ) {
                Text(
                    "바로 주문",
                    color = colorResource(R.color.dark_brown),
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
            }
            //장바구니 담기 Btn
            Button(
                onClick = {
                    cartViewModel.managePriceAndQuantityCartItem(count, price.value * count)
                    cartViewModel.addCartItem(menu, count, price.value, selectedSize, selectedShot)
                    onDismiss()
                },
                modifier = Modifier
                    .weight(1f)
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(6.dp),
            ) {
                Text(
                    "장바구니 담기",
                    color = colorResource(R.color.dark_brown),
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
            }
        }

    }
}


@Composable
fun OrderDetailPage_NoShotOption(
    menu: Menu,
    cartViewModel: CartViewModel,
    onDismiss: () -> Unit
) {

    var selectedSize by remember { mutableStateOf("기본") }

    var isLarge by remember { mutableStateOf(false) }
    var BasePrice by remember { mutableStateOf(menu.price) } //메뉴의 기본 가격
    val price = //옵션 적용 가격
        remember { derivedStateOf { BasePrice + if (isLarge) 500 else 0 } }
    var count by remember { mutableStateOf(1) }


    val spacer = 10.dp
    val itemSpacer = 20.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
            .background(colorResource(R.color.brown_3)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Text(
                    text = menu.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(vertical = 8.dp),
                    color = colorResource(R.color.dark_brown)
                )

                IconButton(
                    onClick = { onDismiss() },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "닫기",
                        tint = colorResource(R.color.dark_brown)
                    )
                }
            }

            Spacer(Modifier.height(itemSpacer))

            //1. 사이즈 선택
            Text(
                "사이즈",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = colorResource(R.color.dark_brown)
            )
            Spacer(Modifier.height(spacer))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("기본", "라지").forEach { size ->
                    Button(
                        onClick = {
                            selectedSize = size
                            if (selectedSize == "라지") isLarge = true else isLarge = false

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedSize == size) Color.White else Color(
                                73f,
                                54f,
                                40f,
                                0.42f
                            ),
                            contentColor = if (selectedSize == size) colorResource(R.color.brown_2) else Color.White
                        ),
                        shape = RoundedCornerShape(0.dp),
                        modifier = Modifier
                            .weight(1f)
                            .border(width = 1.5.dp, color = colorResource(R.color.brown_2))
                            .height(50.dp),
                        contentPadding = PaddingValues(1.dp),
                    ) {
                        Text(text = size, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(Modifier.height(itemSpacer))


        Column(modifier = Modifier.fillMaxWidth()) {
            //2. 수량 선택
            Text(
                "수량",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = colorResource(R.color.dark_brown)
            )
            Spacer(Modifier.height(itemSpacer))


            QuantitySelectorSection(
                price = price.value,
                count = count,
                buttonBackgroundColor = Color.White,
                buttonTextColor = colorResource(R.color.dark_brown),
                textColor = colorResource(R.color.dark_brown),
                quantityFontSize = 20,
                priceFontSize = 30,
                buttonSize = 30,
                buttonTextSize = 15,
                horizontalArrangeMent = Arrangement.SpaceBetween,
                onBtnClick = { quantity, price, calculator ->
                    count = quantity
                }
            )
            Spacer(Modifier.height(itemSpacer))
            //버튼
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 7.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                //바로 주문 Btn
                Button(
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(6.dp),
                ) {
                    Text(
                        "바로 주문",
                        color = colorResource(R.color.dark_brown),
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                }
                //장바구니 담기 Btn
                Button(
                    onClick = {
                        cartViewModel.managePriceAndQuantityCartItem(count, price.value * count)
                        cartViewModel.addCartItem(
                            menu,
                            count,
                            price.value,
                            selectedSize,
                            null
                        )
                        onDismiss()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(6.dp),
                ) {
                    Text(
                        "장바구니 담기",
                        color = colorResource(R.color.dark_brown),
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}

@Composable
fun OrderDetailPage_OnlyCnt(
    menu: Menu,
    cartViewModel: CartViewModel,
    onDismiss: () -> Unit
) {
    var count by remember { mutableStateOf(1) }

    val spacer = 10.dp
    val itemSpacer = 20.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
            .background(colorResource(R.color.brown_3)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Text(
                    text = menu.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(vertical = 8.dp),
                    color = colorResource(R.color.dark_brown)
                )

                IconButton(
                    onClick = { onDismiss() },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "닫기",
                        tint = colorResource(R.color.dark_brown)
                    )
                }
            }

            Spacer(Modifier.height(itemSpacer))


            Column(modifier = Modifier.fillMaxWidth().fillMaxHeight(), verticalArrangement = Arrangement.Bottom) {
                //2. 수량 선택
                Text(
                    "수량",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = colorResource(R.color.dark_brown)
                )
                Spacer(Modifier.height(itemSpacer))

                QuantitySelectorSection(
                    price = menu.price,
                    count = count,
                    buttonBackgroundColor = Color.White,
                    buttonTextColor = colorResource(R.color.dark_brown),
                    textColor = colorResource(R.color.dark_brown),
                    quantityFontSize = 20,
                    priceFontSize = 30,
                    buttonSize = 30,
                    buttonTextSize = 15,
                    horizontalArrangeMent = Arrangement.SpaceBetween,
                    onBtnClick = { quantity, price, calculator ->
                        count = quantity
                    }
                )
                Spacer(Modifier.height(itemSpacer))
                //버튼
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 7.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    //바로 주문 Btn
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .weight(1f)
                            .height(55.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(6.dp),
                    ) {
                        Text(
                            "바로 주문",
                            color = colorResource(R.color.dark_brown),
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                    }
                    //장바구니 담기 Btn
                    Button(
                        onClick = {
                            cartViewModel.managePriceAndQuantityCartItem(count, menu.price * count)
                            cartViewModel.addCartItem(
                                menu,
                                count,
                                menu.price,
                                null,
                                null
                            )
                            onDismiss()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(55.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(6.dp),
                    ) {
                        Text(
                            "장바구니 담기",
                            color = colorResource(R.color.dark_brown),
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                    }
                }
            }
        }
    }
}






