package com.example.coffeevilage.ViewModel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeevilage.AppDatabase
import com.example.coffeevilage.Data.Category
import com.example.coffeevilage.Data.DrinkType
import com.example.coffeevilage.Data.FavoriteMenu
import com.example.coffeevilage.Data.Menu
import com.example.coffeevilage.R
import com.example.coffeevilage.Repository.FavoriteMenuRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MenuViewModel(application: Application) : AndroidViewModel(application) {
    //Room
    private val favoriteMenuDao = AppDatabase.getInstance(application).favoriteMenuDao()
    private val favoriteMenuRepository = FavoriteMenuRepository(favoriteMenuDao)

    //clickedMenu
    var selectedMenu : Menu? by mutableStateOf(null)

    //즐겨찾기 추가한 메뉴 (FavoriteMenu)List
    private val _favorites = MutableStateFlow<List<FavoriteMenu>>(emptyList())
    val favorites = _favorites.asStateFlow()

    //(Menu)List
    private val _favoriteMenuList = MutableStateFlow<List<Menu>>(emptyList())
    val favoriteMenuList = _favoriteMenuList.asStateFlow()

    //전체 메뉴 리스트
    private val _menuList = MutableStateFlow<List<Menu>>(emptyList())
    val menuList: StateFlow<List<Menu>> = _menuList


    // order Tab 관련 변수 (커피, 논커피,음료,차,디저트)
    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex

    // order Tab 관련 변수 (ice, hot)
    private val _selectedTemperature = MutableStateFlow("ICE")
    val selectedTemperature: StateFlow<String> = _selectedTemperature

    //order tab들 필터링해서 갖고온 리스트
    val filteredMenu: StateFlow<List<Menu>> = combine(
        _menuList, _selectedTabIndex, _selectedTemperature
    ) { menuList, tabIndex, temperature ->
        val category = when (tabIndex) {
            0 -> Category.COFFEE
            1 -> Category.NONCOFFEE
            2 -> Category.BEVERAGE
            3 -> Category.TEA
            4 -> Category.DESSERT
            else -> Category.COFFEE
        }
        val drinkType =
            if (temperature == "ICE") DrinkType.ICE else DrinkType.HOT

        menuList.filter { it.category == category && it.drinkType == drinkType }
    }.distinctUntilChanged() // 동일한 결과면 UI 갱신 안 함
     .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    init {
        loadMenuList()
        viewModelScope.launch {
            _favorites.value = favoriteMenuRepository.getAllFavorites()
            val favoriteIds = _favorites.value.map { it.menuId }.toSet()

            _menuList.value = _menuList.value.map { menu ->
                menu.copy(isFavorite = favoriteIds.contains(menu.id))
            }
        }
    }

    fun initOrderTab() {
        _selectedTabIndex.value = 0
        _selectedTemperature.value = "ICE"
    }


    fun loadMenuList() {
        _menuList.value = listOf(
            //--coffee
            Menu(1, "아이스 아메리카노", 2000, R.drawable.ice_americano, Category.COFFEE, DrinkType.ICE),
            Menu(2, "아이스 헤이즐넛 아메리카노", 2500, R.drawable.ice_americano, Category.COFFEE, DrinkType.ICE),
            Menu(3, "아이스 카페라떼", 3000, R.drawable.ice_latte, Category.COFFEE, DrinkType.ICE),
            Menu(4, "아이스 바닐라라떼", 3500, R.drawable.ice_latte, Category.COFFEE, DrinkType.ICE),
            Menu(5, "아이스 카푸치노", 3500, R.drawable.ice_latte, Category.COFFEE, DrinkType.ICE),
            Menu(6, "아이스 카라멜마끼아또", 3500, R.drawable.ice_latte, Category.COFFEE, DrinkType.ICE),
            Menu(7, "아이스 헤이즐넛라떼", 3500, R.drawable.ice_latte, Category.COFFEE, DrinkType.ICE),
            Menu(8, "아이스 카페모카", 4000, R.drawable.ice_latte, Category.COFFEE, DrinkType.ICE),

            Menu(9, "아메리카노", 2000, R.drawable.americano, Category.COFFEE, DrinkType.HOT),
            Menu(10, "헤이즐넛 아메리카노", 2500, R.drawable.americano, Category.COFFEE, DrinkType.HOT),
            Menu(11, "카페라떼", 3000, R.drawable.latte, Category.COFFEE, DrinkType.HOT),
            Menu(12, "바닐라라떼", 3500, R.drawable.latte, Category.COFFEE, DrinkType.HOT),
            Menu(13, "카푸치노", 3500, R.drawable.latte, Category.COFFEE, DrinkType.HOT),
            Menu(14, "카라멜마끼아또", 3500, R.drawable.latte, Category.COFFEE, DrinkType.HOT),
            Menu(15, "헤이즐넛라떼", 3500, R.drawable.latte, Category.COFFEE, DrinkType.HOT),
            Menu(16, "카페모카", 4000, R.drawable.latte, Category.COFFEE, DrinkType.HOT),
//non--coffee
            Menu(17, "아이스 고구마라떼", 3500, R.drawable.sweetpotato, Category.NONCOFFEE, DrinkType.ICE),
            Menu(18, "아이스 초코라떼", 3500, R.drawable.choco, Category.NONCOFFEE, DrinkType.ICE),
            Menu(19, "아이스 녹차라떼", 3500, R.drawable.greentea, Category.NONCOFFEE, DrinkType.ICE),
            Menu(20, "아이스 초코나무라떼", 3500, R.drawable.coffee, Category.NONCOFFEE, DrinkType.ICE),
            Menu(21, "아이스 곡물라떼", 3500, R.drawable.grain, Category.NONCOFFEE, DrinkType.ICE),

            Menu(22, "고구마라떼", 3500, R.drawable.sweetpotato, Category.NONCOFFEE, DrinkType.HOT),
            Menu(23, "초코라떼", 3500, R.drawable.choco, Category.NONCOFFEE, DrinkType.HOT),
            Menu(24, "녹차라떼", 3500, R.drawable.greentea, Category.NONCOFFEE, DrinkType.HOT),
            Menu(25, "초코나무라떼", 3500, R.drawable.coffee, Category.NONCOFFEE, DrinkType.HOT),
            Menu(26, "곡물라떼", 3500, R.drawable.grain, Category.NONCOFFEE, DrinkType.HOT),
            //--beverage
            Menu(27, "딸기스무디", 3500, R.drawable.strawberry, Category.BEVERAGE, DrinkType.ICE),
            Menu(28, "망고스무디", 3500, R.drawable.mango, Category.BEVERAGE, DrinkType.ICE),
            Menu(29, "블루베리스무디", 3500, R.drawable.blueberry, Category.BEVERAGE, DrinkType.ICE),
            Menu(30, "키위스무디", 3500, R.drawable.kiwi, Category.BEVERAGE, DrinkType.ICE),
            Menu(31, "플레인 요거트 스무디", 4000, R.drawable.yogurt, Category.BEVERAGE, DrinkType.ICE),
            Menu(32, "딸기 요거트 스무디", 4000, R.drawable.strawberry, Category.BEVERAGE, DrinkType.ICE),
            Menu(33, "망고 요거트 스무디", 4000, R.drawable.mango, Category.BEVERAGE, DrinkType.ICE),
            Menu(34, "블루베리 요거트 스무디", 4000, R.drawable.blueberry, Category.BEVERAGE, DrinkType.ICE),
            Menu(35, "키위 요거트 스무디", 4000, R.drawable.kiwi, Category.BEVERAGE, DrinkType.ICE),

            Menu(36, "레몬에이드", 3000, R.drawable.lemon, Category.BEVERAGE, DrinkType.ICE),
            Menu(37, "자몽에이드", 3000, R.drawable.grapefruit, Category.BEVERAGE, DrinkType.ICE),
            Menu(38, "청포도에이드", 3000, R.drawable.greengrape, Category.BEVERAGE, DrinkType.ICE),
            Menu(39, "생과일주스", 3500, R.drawable.tomato, Category.BEVERAGE, DrinkType.ICE),
//--tea
            Menu(36, "복숭아 아이스티", 2500, R.drawable.peach, Category.TEA, DrinkType.ICE),
            Menu(37, "아이스 매실차", 2500, R.drawable.greenplum, Category.TEA, DrinkType.ICE),
            Menu(38, "아이스 레몬차", 3500, R.drawable.lemon, Category.TEA, DrinkType.ICE),
            Menu(39, "아이스 자몽차", 3500, R.drawable.grapefruit, Category.TEA, DrinkType.ICE),
            Menu(40, "아이스 유자차", 3500, R.drawable.yuja, Category.TEA, DrinkType.ICE),
            Menu(41, "아이스 생강차", 3500, R.drawable.yuja, Category.TEA, DrinkType.ICE),

            Menu(42, "매실차", 2500, R.drawable.greenplum, Category.TEA, DrinkType.HOT),
            Menu(43, "레몬차", 3500, R.drawable.lemon, Category.TEA, DrinkType.HOT),
            Menu(44, "자몽차", 3500, R.drawable.grapefruit, Category.TEA, DrinkType.HOT),
            Menu(45, "유자차", 3500, R.drawable.yuja, Category.TEA, DrinkType.HOT),
            Menu(46, "생강차", 3500, R.drawable.yuja, Category.TEA, DrinkType.HOT),

            Menu(47, "아이스 루이보스", 3000, R.drawable.teas, Category.TEA, DrinkType.ICE),
            Menu(48, "아이스 페퍼민트", 3000, R.drawable.teas, Category.TEA, DrinkType.ICE),
            Menu(49, "아이스 캐모마일", 3000, R.drawable.teas, Category.TEA, DrinkType.ICE),
            Menu(50, "루이보스", 3000, R.drawable.teas, Category.TEA, DrinkType.HOT),
            Menu(51, "페퍼민트", 3000, R.drawable.teas, Category.TEA, DrinkType.HOT),
            Menu(52, "캐모마일", 3000, R.drawable.teas, Category.TEA, DrinkType.HOT),

            Menu(53, "옛날 팥빙수", 6000, R.drawable.bingsu, Category.DESSERT, DrinkType.ICE),
        )
    }

    //즐겨찾기 메뉴 갖고오기
    suspend fun getFavoriteMenus() {
        _favorites.value = favoriteMenuRepository.getAllFavorites()
        favorites.collect { favs ->
            _favoriteMenuList.value = menuList.value.filter { menu ->
                favs.any { it.menuId == menu.id }
            }
        }
    }

    //order 탭
    fun updateTabIndex(index: Int) {
        _selectedTabIndex.value = index
    }

    fun updateTemperature(temp: String) {
        _selectedTemperature.value = temp
    }

    //favorit 하트 추가/삭제 함수
    fun clickHeart(menuId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val FavoriteMenu = favoriteMenuDao.getFavoriteByMenuId(menuId)
            val isExist = FavoriteMenu != null
            if (isExist) {
                if (FavoriteMenu != null) {
                    favoriteMenuDao.deleteFavorite(FavoriteMenu)
                }
                Log.d("clickHeart", "delete")
            } else {
                favoriteMenuDao.insertFavorite(FavoriteMenu(id = 0, menuId = menuId))
                Log.d("clickHeart", "insert")
            }

            _menuList.value = _menuList.value.map { menu ->
                if (menu.id == menuId) menu.copy(isFavorite = !menu.isFavorite)
                else menu
            }
        }
    }
}