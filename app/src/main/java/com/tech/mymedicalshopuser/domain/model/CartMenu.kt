package com.tech.mymedicalshopuser.domain.model

data class CartMenu(
    var itemIndex: Int = -1,
    var itemCount: Int = 1,
)

val itemIndexSet = mutableSetOf<Int>()
val cartList: ArrayList<CartMenu> = ArrayList()

fun addCartList(cartMenu: CartMenu) {

    if (itemIndexSet.contains(cartMenu.itemIndex)) {
        for (i in cartList.listIterator()) {
            if (i.itemIndex == cartMenu.itemIndex) {
                i.itemCount = cartMenu.itemCount
            }
        }

    } else {
        itemIndexSet.add(cartMenu.itemIndex)
        cartList.add(cartMenu)
    }
}

fun cartItemRemove(cartIndex: Int) {
    cartList.remove(cartList[cartIndex])
}

