package jimenez.miguel.com.cinepolis.model.managers

import com.google.gson.Gson
import com.vicpin.krealmextensions.queryAll
import com.vicpin.krealmextensions.queryFirst
import com.vicpin.krealmextensions.save
import jimenez.miguel.com.cinepolis.model.clients.MovieClient
import jimenez.miguel.com.cinepolis.model.entities.Billboard
import jimenez.miguel.com.cinepolis.model.entities.Movie
import jimenez.miguel.com.cinepolis.model.utils.LogUtils

/**
 * Created by migueljimenez on 11/29/17.
 */
class MovieManager {

    val tag = MovieManager::class.java.simpleName!!

    fun fetch(): Boolean {
        try {
            val responseBody = MovieClient().fetchItems()
            return saveFromServer(responseBody?.string())
        } catch (ex: Exception) {
            LogUtils.Error(tag + ".fetch()", ex)
            return false
        }
    }

    fun load(): List<Movie>{
        try {
            return Movie().queryAll()
        } catch (ex: Exception) {
            LogUtils.Error(tag + ".load()", ex)
            return ArrayList()
        }
    }

    fun loadById(id: Int): Movie?{
        try {
            return Movie().queryFirst { query -> query.equalTo("id", id) }
        } catch (ex: Exception) {
            LogUtils.Error(tag + ".load()", ex)
            return Movie()
        }
    }

    fun saveFromServer(json: String?): Boolean {
        try {
            val typeToken = object : com.google.gson.reflect.TypeToken<Billboard>() {}.type
            val itemContent: Billboard = Gson().fromJson(json, typeToken)
            if(itemContent.movies != null){
                for(movie in itemContent.movies!!){
                    movie.save()
                    movie.media?.let {
                        for(media in it){
                            media.save()
                        }
                    }
                    /*movie.cinemas?.let{
                        for(cinema in it){
                            cinema.save()
                        }
                    }
                    */
                }
            }
            if(itemContent.routes != null){
                for(route in itemContent.routes!!){
                    route.save()
                }
            }
            return true

        } catch (ex: Exception) {
            LogUtils.Error(tag + ".saveFromServer()", ex)
            return false
        }
    }

}