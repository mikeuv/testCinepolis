package jimenez.miguel.com.cinepolis.model.clients

/**
 * Created by migueljimenez on 11/29/17.
 */
class UserClient : WebClients() {

    val oatuhPath = "oauth"
    val tokenPath = "token"

    @Throws(Exception::class)
    fun login(username: String, password: String): Boolean {
        val url = getLoginWebServiceUrl()
        val response = fetch(url, username, password)
        return response?.code() == 200
    }

    fun getLoginWebServiceUrl(): String =
            getPathBuilderUrl()?.addPathSegment(oatuhPath)?.addPathSegment(tokenPath).toString()

}