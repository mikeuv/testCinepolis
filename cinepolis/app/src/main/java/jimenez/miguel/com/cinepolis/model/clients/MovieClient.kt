package jimenez.miguel.com.cinepolis.model.clients

import okhttp3.ResponseBody

/**
 * Created by migueljimenez on 11/29/17.
 */
class MovieClient : WebClients(){

    val moviePath = "movies"
    val countryCodeParameter = "country_code"
    val cinemasParameter = "cinemas"

    @Throws(Exception::class)
    fun fetchItems(): ResponseBody? {

        val url = getWebServiceUrl()
        val response = WebClients().fetchItems(url)
        if (response?.code() == 200) {
            return response.body()
        }
        throw IllegalArgumentException("Response unsuccessful")
    }

    fun getWebServiceUrl(): String =
            getPathBuilderUrl()
                    ?.addPathSegment(moviePath)
                    ?.addEncodedQueryParameter(countryCodeParameter, "MX")
                    ?.addEncodedQueryParameter(cinemasParameter, "32")
                    .toString()

}