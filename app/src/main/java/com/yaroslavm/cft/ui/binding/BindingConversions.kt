package com.yaroslavm.cft.ui.binding

import androidx.databinding.BindingConversion

@BindingConversion
fun capitalizeString(string: String): String {
    var result = string
    if("www" !in string && string != "") result = string.replaceFirstChar { char -> char.uppercase() }
    return result
}

@BindingConversion
fun convertBooleanToYesOrNo(bool: Boolean) = run { if (bool) "Yes" else "No" }