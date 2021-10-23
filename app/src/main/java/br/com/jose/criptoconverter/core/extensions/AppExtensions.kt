package br.com.jose.criptoconverter.core.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.textfield.TextInputLayout
import java.math.BigDecimal
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

var TextInputLayout.text: String
    get() = editText?.text?.toString() ?: ""
    set(value) {
        editText?.setText(value)
    }

fun View.hideSoftKeyboard() {
    val inm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inm.hideSoftInputFromWindow(windowToken, 0)
}

fun BigDecimal.formatCurrency(locale: Locale = Locale("pt", "BR")): String {
    return NumberFormat.getCurrencyInstance(locale).format(this)
}

@SuppressLint("SimpleDateFormat")
fun Long.formatDate(dateFormat: String): String {
    val formatter = SimpleDateFormat(dateFormat)

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return formatter.format(calendar.time)

}

