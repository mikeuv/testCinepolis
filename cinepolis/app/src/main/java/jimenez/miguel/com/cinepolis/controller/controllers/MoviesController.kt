package jimenez.miguel.com.cinepolis.controller.controllers

import android.content.Intent
import android.support.v4.widget.NestedScrollView
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.BindView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import jimenez.miguel.com.cinepolis.R
import jimenez.miguel.com.cinepolis.activities.DetailActivity
import jimenez.miguel.com.cinepolis.controller.adapters.MovieItem
import jimenez.miguel.com.cinepolis.controller.support.ItemClickSupport
import jimenez.miguel.com.cinepolis.model.clients.MovieClient
import jimenez.miguel.com.cinepolis.model.entities.Movie
import jimenez.miguel.com.cinepolis.model.managers.MovieManager
import jimenez.miguel.com.cinepolis.model.utils.C
import nl.komponents.kovenant.task
import java.util.ArrayList

/**
 * Created by migueljimenez on 11/29/17.
 */
class MoviesController : ButterKnifeController() {

    @BindView(R.id.list_items) lateinit var list: RecyclerView
    @BindView(R.id.view_progress) lateinit var viewProgress: NestedScrollView
    @BindView(R.id.img_progress_empty) lateinit var imgProgessEmpty: ImageView
    @BindView(R.id.progress_bar) lateinit var progressBar: ProgressBar
    @BindView(R.id.txt_progress) lateinit var txtProgress: TextView
    @BindView(R.id.swipe_refresh) lateinit var swipeRefresh: SwipeRefreshLayout

    var manager = MovieManager()

    var fastAdapter: FastAdapter<MovieItem>? = null
    var itemAdapter: ItemAdapter<MovieItem>? = null

    var items: List<Movie>? = null

    var adaptersItemBuffer: ArrayList<MovieItem>? = null
    var adapterItems: ArrayList<MovieItem>? = null

    var time: Long? = null

    var uiStatus: C.UIStatus = C.UIStatus.SHOW_EMPTY


    override fun onAttach(view: View) {
        super.onAttach(view)

        setupUI()
        setupListeners()
        setupStyles()
        loadContent()

    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View =
            inflater.inflate(R.layout.controller_movies, container, false)

    override fun onDetach(view: View) {
        super.onDetach(view)

        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
    }

    fun setupUI() {
        items = ArrayList()
        adapterItems = ArrayList()
        adaptersItemBuffer = ArrayList()
        list.layoutManager = StaggeredGridLayoutManager(1, 1)// LinearLayoutManager(activity)

        fastAdapter = FastAdapter()
        itemAdapter = ItemAdapter()

        fastAdapter?.setHasStableIds(true)

        list.layoutManager = LinearLayoutManager(activity)
        list.itemAnimator = DefaultItemAnimator()
        list.adapter = itemAdapter?.wrap(fastAdapter)

    }

    fun setupStyles() {
        swipeRefresh.setColorSchemeResources(R.color.color_accent, R.color.color_primary)
    }

    fun setupListeners() {
        ItemClickSupport.addTo(list).setOnItemClickListener(object : ItemClickSupport.OnItemClickListener {
            override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
                items?.let {
                    if (it.size > position) {
                        val intent = Intent(activity, DetailActivity::class.java)
                        intent.putExtra("id", it[position].id ?: -1)
                        activity?.startActivity(intent)
                    }
                }
            }
        })
    }

    fun loadContent() {
        loadContent(true)
    }

    fun loadContent(firstLoading: Boolean) {

        showLoading()

        task {
            adaptersItemBuffer?.clear()
            items = manager.load()
            items?.let {
                if (it.isEmpty()) {
                    if (firstLoading) {
                        fetchContent()
                    } else {
                        uiStatus = C.UIStatus.SHOW_EMPTY
                    }
                } else {
                    var adapterItem: MovieItem? = null

                    for (item in it) {

                        adapterItem = MovieItem()
                        adapterItem.item = item

                        adaptersItemBuffer?.add(adapterItem)
                    }

                    uiStatus = C.UIStatus.UPDATE_CONTENT
                }
            } ?: run {
                uiStatus = C.UIStatus.NO_CHANGE
            }
        } always {
            updateUI()
        }
    }

    fun fetchContent() {
        val downloaded = manager.fetch()
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
                    C.UIStatus.SHOW_LOADING -> {
                        showLoading()
                    }
                    C.UIStatus.SHOW_EMPTY -> {
                        showEmpty()
                    }
                    C.UIStatus.UPDATE_CONTENT -> {
                        updateContent()
                    }
                }
            }
        }
    }

    fun showLoading() {
        if (isAttached) {
            swipeRefresh.isRefreshing = true
        }
    }

    fun showContent() {
        if (isAttached) {
            if (swipeRefresh.isRefreshing) {
                swipeRefresh.isRefreshing = false
            }

            viewProgress.visibility = View.GONE
            adapterItems?.clear()
            adapterItems?.addAll(adaptersItemBuffer?.toTypedArray()!!)

            itemAdapter?.clear()
            itemAdapter?.add(adapterItems)
        }
    }

    fun updateContent() {
        showContent()
    }

    fun showEmpty() {
        if (isAttached) {

            if (swipeRefresh.isRefreshing) {
                swipeRefresh.isRefreshing = false
            }

            viewProgress.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            txtProgress.setText(R.string.txt_content_unavailable)
        }
    }

    fun showNoInternet() {
        if (isAttached) {

            if (swipeRefresh.isRefreshing) {
                swipeRefresh.isRefreshing = false
            }

            viewProgress.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            txtProgress.setText(R.string.txt_no_internet)
        }
    }

}