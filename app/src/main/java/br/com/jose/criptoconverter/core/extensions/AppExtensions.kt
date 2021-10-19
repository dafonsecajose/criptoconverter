package br.com.jose.criptoconverter.core.extensions

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.NumberFormat
import java.util.*

fun View.hideSoftKeyboard() {
    val inm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inm.hideSoftInputFromWindow(windowToken, 0)
}

fun Double.formatCurrency(locale: Locale = Locale.getDefault()): String {
    return NumberFormat.getCurrencyInstance(locale).format(this)
}