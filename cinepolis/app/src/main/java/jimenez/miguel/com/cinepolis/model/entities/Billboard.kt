package jimenez.miguel.com.cinepolis.model.entities

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

/**
 * Created by migueljimenez on 11/29/17.
 */
@RealmClass
open class Billboard : RealmObject() {

    var movies: RealmList<Movie>? = null
    var routes: RealmList<Route>? = null

    //list movies
    //list routes

}