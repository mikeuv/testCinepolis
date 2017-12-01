package jimenez.miguel.com.cinepolis.model.entities

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by migueljimenez on 11/29/17.
 */
@RealmClass
open class Movie : RealmObject() {
    @PrimaryKey
    var id: Int? = null
    var rating: String? = null
    var media: RealmList<Medium>? = null
//    var cinemas : RealmList<Cinema>? = null
    var position: Int? = null
    //list categories
    var genre: String? = null
    var synopsis: String? = null
    var length: String? = null
    @SerializedName("release_date")
    var releaseDate: String? = null
    var name: String? = null
    var code: String? = null
    @SerializedName("original_name")
    var originalName: String? = null

}