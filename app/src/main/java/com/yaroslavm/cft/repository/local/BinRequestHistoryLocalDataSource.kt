package com.yaroslavm.cft.repository.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yaroslavm.cft.ui.request.BinRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.reflect.Type
import javax.inject.Inject


class BinRequestHistoryLocalDataSource @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val gson: Gson
    ): LocalDataSource<Unit, BinRequest, BinRequest> {

    override suspend fun get(key: Unit): BinRequest {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): List<BinRequest> {

        val prefs = appContext.getSharedPreferences(
            SharedPreferencesConfig.SEARCH_HISTORY_KEY,
            Context.MODE_PRIVATE
        )

        val json: String? = prefs.getString(
            SharedPreferencesConfig.SEARCH_HISTORY_KEY,
            ""
        )

        if (json == "") return emptyList()

        val listType: Type = object : TypeToken<List<BinRequest>>() {}.type

        return gson.fromJson(
            json,
            listType
        ) ?: emptyList()
    }

    override suspend fun saveAll(list: List<BinRequest>) {
        val json = gson.toJson(list)
        val prefs = appContext.getSharedPreferences(
            SharedPreferencesConfig.SEARCH_HISTORY_KEY,
            Context.MODE_PRIVATE
        )
        prefs.edit().putString(
            SharedPreferencesConfig.SEARCH_HISTORY_KEY,
            json
        ).apply()
    }

    override suspend fun deleteAll(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun delete(key: Unit): Boolean {
        TODO("Not yet implemented")
    }

}
