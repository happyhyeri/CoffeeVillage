package com.example.coffeevilage.Screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.animation.AnimatedContent

import androidx.compose.animation.slideInHorizontally
import com.example.coffeevilage.Widget.ImageButton
import com.example.coffeevilage.Widget.CallDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.coffeevilage.Data.FavoriteMenu
import com.example.coffeevilage.Data.Menu


import com.example.coffeevilage.Data.ScreenItem
import com.example.coffeevilage.R
import com.example.coffeevilage.ViewModel.CartViewModel
import com.example.coffeevilage.ViewModel.MenuViewModel
import com.example.coffeevilage.ViewModel.StateViewModel
import com.example.coffeevilage.ViewModel.UserViewModel
import com.example.coffeevilage.Widget.CoffeeImageCard
import com.example.coffeevilage.Widget.CommonTopAppBar
import com.example.coffeevilage.Widget.QuickOrderCard
import com.example.coffeevilage.Widget.TabText
import com.example.coffeevilage.Widget.TeaImageCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun homeScreen(
    stateViewModel: StateViewModel,
    menuViewModel: MenuViewModel,
    userViewModel: UserViewModel,
    cartViewModel: CartViewModel,
    navHostController: NavHostController
) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    var showCallDialog by remember { mutableStateOf(false) }

    //즐겨찾기
    val favoriteMenuList by menuViewModel.favoriteMenuList.collectAsState()
    //최근 주문
    val recentOrderMenuList = emptyList<Menu>()

    LaunchedEffect(Unit) {
        menuViewModel.getFavoriteMenus() //여기서 Favorite -> Menu
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.brown_3))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CommonTopAppBar(
            "COFFEE VILLAGE",
            null,
            R.color.brown_3,
            R.drawable.icon_call,
            { showCallDialog = true })
        Card(
            userViewModel = userViewModel,
            phoneRegisterClickEvent = { navHostController.navigate(ScreenItem.PhoneRegisterScreen.route) },
            pointClickEvent = { navHostController.navigate(ScreenItem.PointHistoryScreen.route) }
        )
        AnimatedImageCards()
        QuickOrderSection(recentOrderMenuList, favoriteMenuList,stateViewModel, menuViewModel )
        if (showCallDialog) {

            CallDialog(onClickYes = {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${stateViewModel.phoneNumber}")
                }
                context.startActivity(intent)
            }, onClickNo = { showCallDialog = false })
        }
        if (stateViewModel.showOrderBottomSheet) {
            Dialog(
                onDismissRequest = { stateViewModel.showOrderBottomSheet = false },
                properties = DialogProperties(
                    usePlatformDefaultWidth = false
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.75f)
                            .background(
                                color = colorResource(R.color.brown_3),
                                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                            )
                            .padding(10.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        menuViewModel.selectedMenu?.let {
                            OrderDetailPage(
                                menu = it,
                                cartViewModel,
                                { stateViewModel.showOrderBottomSheet = false },
                            )
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun Card(
    userViewModel: UserViewModel,
    phoneRegisterClickEvent: () -> Unit,
    pointClickEvent: () -> Unit
) {
    val fontWeight = FontWeight.Bold
    val fontColor = colorResource(R.color.dark_brown)

    val horizontalPaddingValue = 15.dp

    androidx.compose.material.Card(
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(135.dp)
            .padding(horizontal = 15.dp)
    ) {
        if (userViewModel.isPhoneNumberExist) { //휴대폰 등록 되어 있을 경우
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier.weight(2f)) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = horizontalPaddingValue)
                            .padding(top = 25.dp, bottom = 5.dp),
                    ) {
                        Text(text = "반갑습니다.", fontWeight = fontWeight, color = fontColor)
                        Row(
                            Modifier.padding(top = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = userViewModel.registeredPhoneNumber,
                                fontWeight = fontWeight,
                                color = fontColor
                            )
                            Text(text = "  님", fontWeight = fontWeight, color = fontColor)
                            ImageButton(
                                R.drawable.icon_arrow_right,
                                30,
                                { phoneRegisterClickEvent() })
                        }

                    }
                }

                //구분선
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .height(1.dp)
                        .background(colorResource(R.color.dark_brown))
                )

                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = horizontalPaddingValue),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "보유포인트", fontWeight = fontWeight, color = fontColor)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("0", fontWeight = fontWeight, color = fontColor)
                            Text(
                                "P",
                                fontWeight = fontWeight,
                                color = fontColor,
                                modifier = Modifier.padding(start = 7.dp)
                            )
                            ImageButton(R.drawable.icon_arrow_right, 30, { pointClickEvent() })
                        }
                    }
                }
            }
        } else {


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPaddingValue)
                    .padding(top = 25.dp, bottom = 5.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "안녕하세요.", fontWeight = fontWeight, color = fontColor)
                Spacer(Modifier.height(5.dp))
                Text(text = "포인트 적립을 원하시면 휴대폰 번호를 등록해주세요.", fontWeight = fontWeight, color = colorResource(R.color.brown_2), fontSize = 13.sp)
                Row(
                    Modifier.padding(top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "휴대폰 번호 등록 하러 가기",
                        fontWeight = fontWeight,
                        color = fontColor
                    )

                    ImageButton(
                        R.drawable.icon_arrow_right,
                        30,
                        { phoneRegisterClickEvent() })
                }

            }

        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedImageCards() {
    val ImgCards: List<@Composable () -> Unit> = listOf(
        { CoffeeImageCard() },
        { TeaImageCard() }
    )

    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            currentIndex = (currentIndex + 1) % ImgCards.size
        }
    }

    AnimatedContent(
        targetState = currentIndex,
        transitionSpec = {
            slideInHorizontally(
                animationSpec = tween(1000),
                initialOffsetX = { fullWidth -> fullWidth } // 오른쪽에서 들어옴
            ) with slideOutHorizontally(
                animationSpec = tween(1000),
                targetOffsetX = { fullWidth -> -fullWidth } // 왼쪽으로 나감
            )
        }, label = "cardAnimation"
    ) { index ->
        ImgCards[index]()
    }
}


@Composable
fun QuickOrderSection(recentOrderMenuList: List<Menu>, favoriteMenuList: List<Menu>,stateViewModel: StateViewModel,menuViewModel: MenuViewModel) {

    var selectedTab by remember { mutableStateOf("최근 주문") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(color = colorResource(R.color.brown_3)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Quick Order",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(R.color.brown_white)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.padding(end = 15.dp)
        ) {
            TabText(
                text = "최근 주문",
                isSelected = selectedTab == "최근 주문",
                onClick = { selectedTab = "최근 주문" }
            )
            TabText(
                text = "즐겨찾기",
                isSelected = selectedTab == "즐겨찾기",
                onClick = { selectedTab = "즐겨찾기" }
            )
        }
    }
    if (selectedTab == "최근 주문") {
        QuickOrderScreen(recentOrderMenuList, "최근 주문",stateViewModel, menuViewModel)
    } else {
        QuickOrderScreen(favoriteMenuList, "즐겨찾기",stateViewModel, menuViewModel)
    }
}


@Composable
fun QuickOrderScreen(menus: List<Menu>, type: String, stateViewModel: StateViewModel,menuViewModel: MenuViewModel) {
    val listState = rememberLazyListState()
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    val coroutineScope = rememberCoroutineScope()
    val itemWidth = 250.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val centerOffsetPx =
        with(LocalDensity.current) { (screenWidth / 2 - itemWidth / 2).toPx().toInt() }

    var isClickedItem by remember { mutableStateOf(0) }

    if (menus.size == 0) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp),
            contentAlignment = Alignment.Center
        ) {
            if (type == "최근 주문") {
                Text("최근 주문한 메뉴가 없습니다", color = Color.White, fontWeight = FontWeight.SemiBold)
            } else {
                Text("즐겨찾기 메뉴가 없습니다.", color = Color.White, fontWeight = FontWeight.SemiBold)
            }
        }
    } else {
        LazyRow(
            state = listState,
            flingBehavior = flingBehavior, //스냅 적용
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(menus) { index, menu ->
                QuickOrderCard(
                    menu = menu,
                    modifier = Modifier
                        .width(itemWidth)
                        .height(200.dp)
                        .clickable {
                            coroutineScope.launch {
                                listState.animateScrollToItem(
                                    index,
                                    -centerOffsetPx
                                )
                            }
                            isClickedItem = index
                        },
                    isSelected = index == isClickedItem,
                    addCartItem = {
                        menuViewModel.selectedMenu = menu
                        stateViewModel.showOrderBottomSheet = true
                    }
                )
            }
        }
    }

}

@Composable
fun FavoriteOrderScreen() {
    Text("즐겨찾기 화면", modifier = Modifier.padding(16.dp))
}






