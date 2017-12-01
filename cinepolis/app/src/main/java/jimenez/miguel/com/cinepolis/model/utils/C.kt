package jimenez.miguel.com.cinepolis.model.utils

/**
 * Created by migueljimenez on 11/29/17.
 */

class C {
    //https://api-stage.cinepolis.com/v2/oauth/token
    object URLs {
        @JvmField val scheme : String = "https"
        @JvmField val baseUrl : String = "api-stage.cinepolis.com"
        @JvmField val webServicesVersion : String = "v2"
        @JvmField val webServicesOauth : String = "oauth"
        @JvmField val webServicesToken : String = "token"

    }

    enum class LoginSections {
        LOGIN
    }

    enum class MainSections {
        MOVIES
    }

    enum class UIStatus {
        SHOW_CONTENT, SHOW_LOADING, SHOW_EMPTY, UPDATE_CONTENT, NO_CHANGE
    }

}