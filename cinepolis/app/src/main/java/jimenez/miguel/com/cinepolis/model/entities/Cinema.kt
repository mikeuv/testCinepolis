package jimenez.miguel.com.cinepolis.model.entities

import io.realm.RealmObject
import io.realm.annotations.RealmClass

/**
 * Created by migueljimenez on 11/30/17.
 */
@RealmClass
open class Cinema : RealmObject() {

    var id: Int? = null
    var name: String? = null
    var lat: Double? = null
    var lng: Double? = null
    var phone: Long? = null
    var address: String? = null

}