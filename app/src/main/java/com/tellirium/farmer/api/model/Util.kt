package com.tellirium.farmer.api.model

import android.content.Context
import android.preference.PreferenceManager
import com.tellirium.farmer.util.Constants.KEY_FIRST_TIME
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(
                status = Status.SUCCESS,
                data = data,
                message = null)

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(
                status = Status.ERROR,
                data = data,
                message = message)

        fun <T> loading(data: T?): Resource<T> =
            Resource(
                status = Status.LOADING,
                data = data,
                message = null)
    }
}

enum class Status { SUCCESS, ERROR, LOADING }


/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}

@Singleton
class MyPreference @Inject constructor(@ApplicationContext context : Context){
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    fun getStoredTag(): Boolean {
        return prefs.getBoolean(KEY_FIRST_TIME, true)!!
    }
    fun setStoredTag(query: Boolean) {
        prefs.edit().putBoolean(KEY_FIRST_TIME, query).apply()
    }
}