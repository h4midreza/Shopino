package com.example.shopino.helper

import android.content.Context
import android.widget.Toast
import com.example.shopino.domain.model.ItemsModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ManagementCart @Inject constructor(@ApplicationContext val context: Context) {

    private val tinyDB = TinyDB(context)
    private val CART_LIST_KEY = "CartList"

    private fun saveCart(items: List<ItemsModel>) {
        tinyDB.putListObject(CART_LIST_KEY, ArrayList(items))
    }

    fun getListCart(): ArrayList<ItemsModel> {
        return tinyDB.getListObject(CART_LIST_KEY) ?: ArrayList()
    }

    fun addItem(itemToAdd: ItemsModel) {
        val listItem = getListCart()
        val existingItemIndex = listItem.indexOfFirst { it.id == itemToAdd.id } // فرض می شود id در ItemsModel نوع یکسانی دارد

        if (existingItemIndex != -1) {
            listItem[existingItemIndex].numberInCart += itemToAdd.numberInCart
        } else {
            if (itemToAdd.numberInCart <= 0) {
                listItem.add(itemToAdd.copy(numberInCart = 1))
            } else {
                listItem.add(itemToAdd)
            }
        }
        saveCart(listItem)
        Toast.makeText(context, "به سبد خرید اضافه شد", Toast.LENGTH_SHORT).show()
    }

    fun removeItem(itemToRemove: ItemsModel) {
        val listItem = getListCart()
        val existingItemIndex = listItem.indexOfFirst { it.id == itemToRemove.id }

        if (existingItemIndex != -1) {
            val currentItem = listItem[existingItemIndex]
            currentItem.numberInCart -= 1
            if (currentItem.numberInCart <= 0) {
                listItem.removeAt(existingItemIndex)
            }
            saveCart(listItem)
            Toast.makeText(context, "از سبد خرید حذف شد", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Completely removes an item from the cart, regardless of its quantity.
     * Assumes ItemsModel.id is Int and productId is a string representation of that Int.
     */
    fun removeProductCompletely(productId: String) {
        val listItem = getListCart()
        val initialSize = listItem.size
        val idToRemove = productId.toIntOrNull()
        if (idToRemove != null) {
            listItem.removeAll { it.id == idToRemove }
        }
        if (listItem.size < initialSize) {
            saveCart(listItem)
            Toast.makeText(context, "آیتم به طور کامل حذف شد", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Assumes ItemsModel.id is Int and itemId is a string representation of that Int.
     */
    fun updateItemQuantity(itemId: String, newQuantity: Int) {
        val listItem = getListCart()
        val idToUpdate = itemId.toIntOrNull()
        if (idToUpdate != null) {
            val itemIndex = listItem.indexOfFirst { it.id == idToUpdate }
            if (itemIndex != -1) {
                if (newQuantity > 0) {
                    listItem[itemIndex].numberInCart = newQuantity
                } else {
                    listItem.removeAt(itemIndex)
                }
                saveCart(listItem)
            }
        }
    }

    fun clearCart() {
        tinyDB.remove(CART_LIST_KEY)
        Toast.makeText(context, "سبد خرید خالی شد", Toast.LENGTH_SHORT).show()
    }

    /**
     * Assumes ItemsModel.id is Int and itemId is a string representation of that Int.
     */
    fun getItemById(itemId: String): ItemsModel? {
        val idToFind = itemId.toIntOrNull()
        return if (idToFind != null) {
            getListCart().find { it.id == idToFind }
        } else {
            null
        }
    }
}
