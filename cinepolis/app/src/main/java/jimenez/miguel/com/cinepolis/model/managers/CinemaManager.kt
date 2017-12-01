package jimenez.miguel.com.cinepolis.model.managers

import com.google.gson.Gson
import com.vicpin.krealmextensions.queryFirst
import com.vicpin.krealmextensions.save
import jimenez.miguel.com.cinepolis.model.clients.CinemaClient
import jimenez.miguel.com.cinepolis.model.entities.Cinema
import jimenez.miguel.com.cinepolis.model.utils.LogUtils

/**
 * Created by migueljimenez on 11/30/17.
 */
class CinemaManager {

    val tag = CinemaManager::class.java.simpleName!!

    fun fetch(id: Int): Boolean {
        try {
            val responseBody = CinemaClient().fetchItems(id)
            return saveFromServer(responseBody?.string())
        } catch (ex: Exception) {
            LogUtils.Error(tag + ".fetch()", ex)
            return false
        }
    }

    fun loadById(id: Int): Cinema?{
        try {
            return Cinema().queryFirst { query -> query.equalTo("id", id) }
        } catch (ex: Exception) {
            LogUtils.Error(tag + ".load()", ex)
            return Cinema()
        }
    }

    fun saveFromServer(json: String?): Boolean {
        try {
            val typeToken = object : com.google.gson.reflect.TypeToken<Cinema>() {}.type
            val item: Cinema = Gson().fromJson(json, typeToken)

                item.save()

            return true
        } catch (ex: Exception) {
            LogUtils.Error(tag + ".saveFromServer()", ex)
            return false
        }
    }

}