package jimenez.miguel.com.cinepolis.model.entities

import io.realm.RealmObject
import io.realm.annotations.RealmClass

/**
 * Created by migueljimenez on 11/30/17.
 */
@RealmClass
open class Route : RealmObject() {

    var code: String? = null
    var sizes: Sizes? = null


}