package br.com.jose.criptoconverter.core.extensions


import android.content.Context
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import br.com.jose.criptoconverter.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.createDialog(dialog: MaterialAlertDialogBuilder.() -> Unit = {}): AlertDialog {
    val builder = MaterialAlertDialogBuilder(this)
    builder.setPositiveButton(android.R.string.ok, null)
    dialog(builder)
    return builder.create()
}

fun Context.createProgressDialog(): AlertDialog {
    return createDialog {
        val padding = this@createProgressDialog.resources
            .getDimensionPixelOffset(R.dimen.default_padding)
        val progressBar = ProgressBar(this@createProgressDialog)
        progressBar.setPadding(padding, padding, padding, padding)
        setView(progressBar)
        setPositiveButton(null, null)
        setCancelable(false)
    }
}