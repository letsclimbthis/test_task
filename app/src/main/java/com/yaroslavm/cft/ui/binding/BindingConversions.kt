package com.yaroslavm.cft.ui.binding

import androidx.databinding.BindingConversion
import java.text.SimpleDateFormat
import java.util.*

@BindingConversion
fun capitalizeString(string: String?): String {
    var result = string ?: "-"
    if("www" !in result && result != "-") result = result.replaceFirstChar { char -> char.uppercase() }
    return result
}

@BindingConversion
fun convertBooleanToYesOrNo(bool: Boolean) = run { if (bool) "Yes" else "No" }

@BindingConversion
fun millisecondsToDate(milliseconds: Long): String {
    val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy 'at' HH:mm", Locale.getDefault(Locale.Category.FORMAT))
    return simpleDateFormat.format(milliseconds)
}