package jimenez.miguel.com.cinepolis.model.managers

import com.vicpin.krealmextensions.deleteAll
import jimenez.miguel.com.cinepolis.model.entities.User
import jimenez.miguel.com.cinepolis.model.utils.LogUtils

/**
 * Created by migueljimenez on 11/29/17.
 */
class UserManager {

    val tag = UserManager::class.java.simpleName!!

    fun login(user: User): Boolean {
        return try {

            true
        } catch (ex: Exception) {
            User().deleteAll()
            LogUtils.Error(tag + "login()", ex)

            false
        }
    }

}