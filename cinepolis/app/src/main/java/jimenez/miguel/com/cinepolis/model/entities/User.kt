package jimenez.miguel.com.cinepolis.model.entities

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by migueljimenez on 11/29/17.
 */
@RealmClass
open class User : RealmObject() {

    @PrimaryKey
    var username: String? = null
    var password: String? = null
    var loggedIn: Boolean? = null
}