package jimenez.miguel.com.cinepolis.model.clients

import jimenez.miguel.com.cinepolis.model.utils.C
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by migueljimenez on 11/29/17.
 */
open class WebClients {

    open var httpClient: OkHttpClient? = null

    val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()!!

    val TEXTPLAIN = MediaType.parse("text/plain; charset=utf-8")

    var formBody: RequestBody = FormBody.Builder().build()

    var url: String? = null

    val apiKey = "199e2ce46ac525fddf"

    fun getPathBuilderUrl(): HttpUrl.Builder? {

        val build = HttpUrl.Builder()
                .scheme(C.URLs.scheme)
                .host(C.URLs.baseUrl)
                .addPathSegment(C.URLs.webServicesVersion)
        return build
    }

    @Throws(Exception::class)
    fun fetch(url: String, username: String, password: String): Response? {

                val body = RequestBody.create(TEXTPLAIN, "country_code=MX&username=pruebas_beto_ia%40yahoo.com&password=Prueb\n" +
                        "as01&grant_type=password&client_id=IATestCandidate&client_secret=c8\n" +
                        "40457e777b4fee9b510fbcd4985b68")

        val request = Request.Builder().url(url).post(body).addHeader("api_key", apiKey).build()
        val response = client.newCall(request).execute()

        if (response?.isSuccessful != true) {
            throw IOException("Unexpected code " + response)
        }
        return response
    }

    @Throws(Exception::class)
    fun fetchItems(url: String): Response? {

        val request = Request.Builder()
                .url(url).addHeader("api_key", apiKey)
                .build()

        val response = client.newCall(request).execute()

        if (response?.isSuccessful != true) {
            throw IOException("Unexpected code " + response)
        }
        return response
    }

}