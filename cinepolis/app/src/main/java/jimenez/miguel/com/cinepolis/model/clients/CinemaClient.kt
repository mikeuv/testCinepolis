package jimenez.miguel.com.cinepolis.model.clients

import okhttp3.ResponseBody

/**
 * Created by migueljimenez on 11/30/17.
 */
class CinemaClient : WebClients(){

    val locationPath = "locations"
    val cinemasPath = "cinemas"
    val countryCodeParameter = "country_code"

    @Throws(Exception::class)
    fun fetchItems(id: Int): ResponseBody? {

        val url = getWebServiceUrl(id)
        val response = WebClients().fetchItems(url)
        if (response?.code() == 200) {
            return response.body()
        }
        throw IllegalArgumentException("Response unsuccessful")
    }

    fun getWebServiceUrl(id: Int): String =
            getPathBuilderUrl()
                    ?.addPathSegment(locationPath)
                    ?.addPathSegment(cinemasPath)
                    ?.addPathSegment(id.toString())
                    ?.addEncodedQueryParameter(countryCodeParameter, "MX")
                    .toString()

}