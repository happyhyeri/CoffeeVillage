package com.example.coffeevilage.Data

//price의 경우 옵션적용시 메뉴당 가격의 값을 넣기 위함임.
data class CartItem(
    val menu: Menu,
    val quantity: Int,
    val optionAppliedPrice: Int,
    val shot: Shot? = null,
    val size: Size? = null
)

enum class Shot(val label: String) {
    ONE("기본"), TWO("샷 추가"), HALF("연하게");

    override fun toString(): String {
        return label
    }
    companion object {
        fun fromLabel(label: String): Shot? =
            entries.find { it.label == label }
    }
}

enum class Size(val label : String) { REGULAR("기본"), LARGE("라지");

    override fun toString(): String {
        return label
    }
    companion object {
        fun fromLabel(label: String): Size? =
            Size.entries.find { it.label == label }
    }
}


