package com.yaroslavm.cft.repository

import com.google.gson.annotations.SerializedName

data class BinlistResponse(
    @SerializedName("number") var number: Number? = null,
    @SerializedName("scheme") var scheme: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("brand") var brand: String? = null,
    @SerializedName("prepaid") var prepaid: Boolean? = null,
    @SerializedName("country") var country: Country? = null,
    @SerializedName("bank") var bank: Bank? = null
) {

    data class Number(
        @SerializedName("length") var length: Double? = null,
        @SerializedName("luhn") var luhn: Boolean? = null,
    )

    data class Country(
        @SerializedName("numeric") var numeric: String? = null,
        @SerializedName("alpha2") var alpha2: String? = null,
        @SerializedName("name") var name: String? = null,
        @SerializedName("emoji") var emoji: String? = null,
        @SerializedName("currency") var currency: String? = null,
        @SerializedName("latitude") var latitude: Double? = null,
        @SerializedName("longitude") var longitude: Double? = null
    )

    data class Bank(
        @SerializedName("name") var name: String? = null,
        @SerializedName("url") var url: String? = null,
        @SerializedName("phone") var phone: String? = null,
        @SerializedName("city") var city: String? = null
    )
}
