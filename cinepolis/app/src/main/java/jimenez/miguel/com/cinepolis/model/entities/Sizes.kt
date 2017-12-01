package jimenez.miguel.com.cinepolis.model.entities

import io.realm.RealmObject
import io.realm.annotations.RealmClass

/**
 * Created by migueljimenez on 11/30/17.
 */
@RealmClass
open class Sizes : RealmObject() {

    var large: String? = null
    var medium: String? = null
    var small: String? = null

}