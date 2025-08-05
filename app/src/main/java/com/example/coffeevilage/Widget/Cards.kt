package com.example.coffeevilage.Widget

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevilage.Data.CartItem
import com.example.coffeevilage.Data.Menu
import com.example.coffeevilage.R
import com.example.coffeevilage.ViewModel.CartViewModel
import com.example.coffeevilage.ViewModel.MenuViewModel


//이미지 카드

@Composable
fun CoffeeImageCard() {

    val fontsize: Int = 12
    val backgroundColor = Color(0x80000000)
    val fontColor = colorResource(R.color.brown_white)
    Box(
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .fillMaxWidth()
            .height(240.dp)
            .clip(
                RoundedCornerShape(14.dp)
            )
    ) {
        Image(
            painter = painterResource(R.drawable.coffee),
            contentDescription = "커피",
            modifier = Modifier
                .matchParentSize(),
            contentScale = ContentScale.Crop
        )


        Column(
            modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(20.dp)
            ) {
                Text(
                    text = "안녕하세요\n커피빌리지 입니다",
                    color = fontColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.Start),
                horizontalAlignment = AbsoluteAlignment.Left
            ) {
                // 강조 텍스트 박스
                Text(
                    text = "저희는",
                    color = fontColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = fontsize.sp,
                    modifier = Modifier
                        .background(color = backgroundColor, RoundedCornerShape(1.dp)) // 반투명 배경
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "예가체프 (에티오피아 남부)",
                    color = fontColor,
                    fontSize = fontsize.sp,
                    modifier = Modifier
                        .background(color = backgroundColor, RoundedCornerShape(1.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "케냐 AA (케냐 고지대)",
                    color = fontColor,
                    fontSize = fontsize.sp,
                    modifier = Modifier
                        .background(color = backgroundColor, RoundedCornerShape(1.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "5:5 비율로 블렌딩하여 로스팅하고 있습니다",
                    color = fontColor,
                    fontSize = fontsize.sp,
                    modifier = Modifier
                        .background(color = backgroundColor, RoundedCornerShape(1.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun TeaImageCard() {

    val fontsize: Int = 12
    val backgroundColor = Color(0x80000000)
    val fontColor = colorResource(R.color.brown_white)
    Box(
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .fillMaxWidth()
            .height(240.dp)
            .clip(
                RoundedCornerShape(14.dp)
            )
    ) {
        Image(
            painter = painterResource(R.drawable.tea),
            contentDescription = "tea",
            modifier = Modifier
                .matchParentSize(),
            contentScale = ContentScale.Crop
        )


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(20.dp)
            ) {
                Text(
                    text = "100% 직접 담근 수제청",
                    color = fontColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(color = backgroundColor, RoundedCornerShape(1.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }

            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.Start),
                horizontalAlignment = AbsoluteAlignment.Left
            ) {
                // 강조 텍스트 박스
                Text(
                    text = "레몬, 자몽, 유자, 생강",
                    color = fontColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = fontsize.sp,
                    modifier = Modifier
                        .background(color = backgroundColor, RoundedCornerShape(1.dp)) // 반투명 배경
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "저희 가족이 먹는다는 생각으로 정성을 담았습니다.",
                    color = fontColor,
                    fontSize = fontsize.sp,
                    modifier = Modifier
                        .background(color = backgroundColor, RoundedCornerShape(1.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}




@Composable
fun QuickOrderCard(menu: Menu, modifier: Modifier, isSelected: Boolean, addCartItem: () -> Unit) {

    val backgroundColor = colorResource(R.color.brown_white).copy(0.5f)
    Column(
        modifier = modifier
            .width(250.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) backgroundColor else backgroundColor.copy(alpha = 0.3f))
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //이미지와 정보
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .weight(2.5f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Image(
                    painter = painterResource(menu.imageRes),
                    contentDescription = menu.name,
                    modifier = Modifier
                        .weight(1.5f)
                        .aspectRatio(1f)//정사각형 유지
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxSize()
                    .padding(vertical = 20.dp, horizontal = 10.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = menu.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp, color = colorResource(R.color.dark_brown)
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "${menu.price}원",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = colorResource(R.color.dark_brown)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { addCartItem() },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.dark_brown),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(40),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = "주문 하기",
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.brown_white)
            )
        }
    }
}

//주문 방법 선택 카드
@Composable
fun OrderMethodCard(text: String, imgRes: Int, onclick: () -> Unit) {
    androidx.compose.material3.Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .size(180.dp, 140.dp)
            .clickable(
                onClick = onclick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.matchParentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = text,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.dark_brown)
                )
                Spacer(Modifier.height(10.dp))
                Image(
                    painter = painterResource(imgRes),
                    contentDescription = null,
                    modifier = Modifier.size(35.dp)
                )
            }
        }
    }
}

//장바구니 card
@Composable
fun CartItmeCard(cartItem: CartItem, modifier: Modifier, cartViewModel: CartViewModel) {
    val backgroundColor = colorResource(R.color.brown_white)
    Column(
        modifier = modifier
            .width(355.dp)
            .height(130.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .padding(7.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.7f),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = {
                    Log.d("index_cart_q", "${cartItem.quantity}")
                    cartViewModel.managePriceAndQuantityCartItem(
                        cartItem.quantity,
                        cartItem.optionAppliedPrice * cartItem.quantity,
                        false
                    )
                    cartViewModel.removeCartItem(cartItem)
                }, modifier = Modifier.size(25.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_clear),
                    contentDescription = null
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .fillMaxWidth()
                .weight(2.5f),
            verticalAlignment = Alignment.Top,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(cartItem.menu.imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .aspectRatio(1f)//정사각형 유지
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxSize()
                    .padding(start = 10.dp),
                verticalArrangement = Arrangement.Top
            ) {

                Text(
                    text = cartItem.menu.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp, color = colorResource(R.color.dark_brown)
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    if (cartItem.size != null && cartItem.shot != null) {
                        Text(
                            text = "사이즈 선택:  ${cartItem.size.toString()}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp, color = colorResource(R.color.dark_brown)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "샷 선택:  ${cartItem.shot.toString()}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp, color = colorResource(R.color.dark_brown)
                        )
                    } else {
                        if (cartItem.size != null) {
                            Text(
                                text = "사이즈 선택:  ${cartItem.size}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp, color = colorResource(R.color.dark_brown)
                            )
                        }
                        if(cartItem.shot != null) {
                            Text(
                                text = "샷 선택:  ${cartItem.shot}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp, color = colorResource(R.color.dark_brown)
                            )
                        }

                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                QuantitySelectorSection(
                    price = cartItem.optionAppliedPrice,
                    count = cartItem.quantity,
                    buttonBackgroundColor = colorResource(R.color.brown_2),
                    buttonTextColor = colorResource(R.color.white),
                    textColor = colorResource(R.color.dark_brown),
                    quantityFontSize = 16,
                    priceFontSize = 18,
                    onBtnClick = { price, quantaty, plusOrMinus
                        ->
                        cartViewModel.managePriceAndQuantityCartItem(
                            1,
                            cartItem.optionAppliedPrice,
                            plusOrMinus
                        )
                        cartViewModel.updateCartItem(
                            cartItem,
                            1,
                            cartItem.optionAppliedPrice,
                            plusOrMinus
                        )
                    }
                )

            }
        }
    }
}

//+-수량 버튼 및 가격
@Composable
fun QuantitySelectorSection(
    price: Int = 2000,// 기본 단가
    count: Int = 1,
    buttonBackgroundColor: Color,
    buttonTextColor: Color,
    textColor: Color,
    quantityFontSize: Int = 18,
    priceFontSize: Int = 20,
    buttonSize: Int = 30,
    buttonTextSize: Int = 15,
    horizontalArrangeMent: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(10.dp),
    onBtnClick: (Int, Int, Boolean) -> Unit, //Boolean false = minus, true = plus
) {
    val totalPrice = count * price
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangeMent,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Button(
            onClick = {
                if (count > 1) {
                    onBtnClick(count - 1, (count - 1) * price, false)
                }
            },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = buttonBackgroundColor),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.size(buttonSize.dp)
        ) {
            Text("－", color = buttonTextColor, fontSize = buttonTextSize.sp)

        }

        Text(
            text = count.toString(),
            color = textColor,
            fontSize = quantityFontSize.sp,
            fontWeight = FontWeight.Bold
        )

        Button(
            onClick = {

                onBtnClick(count + 1, (count + 1) * price, true)
            },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonBackgroundColor,
            ),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.size(buttonSize.dp)
        ) {
            Text("+", color = buttonTextColor, fontSize = buttonTextSize.sp)

        }

        Text(
            text = "${totalPrice} 원",
            color = textColor,
            fontSize = priceFontSize.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}


@Composable
fun OrderMenuCard(
    menu: Menu,
    menuViewModel: MenuViewModel,
    showOrderBottomSheet: () -> Unit,
    clickedMenu: (Menu) -> Unit
) {
    //var isFavorite by remember { mutableStateOf(drink.isFavorite) }

    Card(
        shape = RoundedCornerShape(12.dp),
        backgroundColor = colorResource(R.color.brown_white),
        modifier = Modifier
            .width(120.dp)
            .height(190.dp)
            .padding(8.dp)
            .clickable {
                showOrderBottomSheet()
                clickedMenu(menu)
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 7.dp, vertical = 15.dp)
                    .padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Image(
                    painter = painterResource(id = menu.imageRes),
                    contentDescription = menu.name,
                    modifier = Modifier
                        .size(55.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )


                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = menu.name,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(R.color.dark_brown),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(10.dp))


                    Text(
                        text = "${menu.price}원",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.dark_brown),
                        textAlign = TextAlign.Center
                    )
                }
            }

            //하트 아이콘
            IconButton(
                onClick = { /*isFavorite = !isFavorite*/
                    menuViewModel.clickHeart(menu.id)
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(20.dp)
            ) {
                Icon(
                    painter = painterResource(
                        id = if (menu.isFavorite)
                            R.drawable.icon_favorite_fill
                        else
                            R.drawable.icon_favorite_empty
                    ),
                    contentDescription = "Favorite",
                    tint = if (menu.isFavorite) Color.Red else Color.Gray
                )
            }
        }
    }

}


