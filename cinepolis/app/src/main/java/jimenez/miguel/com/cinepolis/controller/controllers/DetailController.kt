package jimenez.miguel.com.cinepolis.controller.controllers

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import jimenez.miguel.com.cinepolis.R
import jimenez.miguel.com.cinepolis.controller.events.ChangeToolbarTitle
import jimenez.miguel.com.cinepolis.model.entities.Cinema
import jimenez.miguel.com.cinepolis.model.entities.Movie
import jimenez.miguel.com.cinepolis.model.managers.CinemaManager
import jimenez.miguel.com.cinepolis.model.managers.MovieManager
import jimenez.miguel.com.cinepolis.model.utils.C
import nl.komponents.kovenant.task
import org.greenrobot.eventbus.EventBus
import android.text.style.UnderlineSpan
import android.text.SpannableString
import jimenez.miguel.com.cinepolis.controller.events.GoogleMapsIntent


/**
 * Created by migueljimenez on 11/30/17.
 */
class DetailController() : ButterKnifeController() {

    @BindView(R.id.txt_genre) lateinit var txtGenre: TextView
    @BindView(R.id.txt_synopsis) lateinit var txtSynopsis: TextView
    @BindView(R.id.txt_length) lateinit var txtLength: TextView
    @BindView(R.id.txt_rating) lateinit var txtRating: TextView

    @BindView(R.id.txt_street) lateinit var txtStreet: TextView
    @BindView(R.id.txt_name) lateinit var txtName: TextView


    var id: Int? = null
    var manager = MovieManager()
    var cinemaManager = CinemaManager()
    var item: Movie? = null
    var uiStatus: C.UIStatus = C.UIStatus.SHOW_EMPTY
    var idCinema = 32
    var cinema : Cinema? = null

    constructor(id: Int) : this() {
        this.id = id
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View =
            inflater.inflate(R.layout.controller_detail, container, false)

    override fun onAttach(view: View) {
        super.onAttach(view)
        loadContent()
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
    }


    fun loadContent() {
        loadContent(true)
    }

    fun loadContent(firstLoading: Boolean) {
        task {
            item = manager.loadById(id ?: -1)
            cinema = cinemaManager.loadById(idCinema)

            if (cinema == null) {
                if (firstLoading) {
                    fetchContent()
                } else {
                    dissapearCinemaInformation()
                }
            } else {
                showCinemaContent()
            }
            uiStatus = C.UIStatus.SHOW_CONTENT
        } always {
            updateUI()
        }

    }

    fun fetchContent() {
        val downloaded = cinemaManager.fetch(idCinema)
        if (downloaded && isAttached) {
            reloadContent()
        }
    }

    fun reloadContent() {
        loadContent(false)
    }

    fun updateUI() {
        if (isAttached) {
            activity?.runOnUiThread {
                when (uiStatus) {
                    C.UIStatus.SHOW_CONTENT -> {
                        showContent()
                    }
                }
            }
        }
    }

    fun showContent() {
        if (isAttached) {

            txtGenre.text = item?.genre
            txtLength.text = item?.length
            txtRating.text = item?.rating
            txtSynopsis.text = item?.synopsis
            EventBus.getDefault().post(ChangeToolbarTitle(item?.name ?: "Película"))

        }
    }

    fun showCinemaContent(){

        val content = SpannableString(cinema?.address ?: "")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)

        txtName.text = cinema?.name
        txtStreet.text = content
    }

    fun dissapearCinemaInformation(){
        activity?.runOnUiThread {

            txtName.visibility = View.GONE
            txtStreet.visibility = View.GONE

        }
    }

    @OnClick(R.id.txt_street)
    fun googleMapsIntent() {
        EventBus.getDefault().post(GoogleMapsIntent(cinema?.lng ?: 0.0, cinema?.lat ?: 0.0))
    }

}