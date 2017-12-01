package jimenez.miguel.com.cinepolis.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import butterknife.BindView
import butterknife.ButterKnife
import jimenez.miguel.com.cinepolis.R
import com.bluelinelabs.conductor.ChangeHandlerFrameLayout
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import jimenez.miguel.com.cinepolis.controller.controllers.DetailController
import jimenez.miguel.com.cinepolis.controller.controllers.Navigator
import jimenez.miguel.com.cinepolis.controller.events.ChangeToolbarTitle
import jimenez.miguel.com.cinepolis.controller.events.GoogleMapsIntent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import android.content.Intent
import android.net.Uri
import android.util.Log


class DetailActivity : AppCompatActivity() {

    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar
    @BindView(R.id.controller_container) lateinit var controllerContainer: ChangeHandlerFrameLayout

    private var router: Router? = null
    private var navigator: Navigator? = null

    var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        ButterKnife.bind(this)
        setupRouter(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        setupToolbar()

        val intent = intent
        id = intent.getIntExtra("id", 0)

        navigateToController()
    }

    override fun onResume() {
        EventBus.getDefault().register(this)
        super.onResume()
    }

    override fun onPause() {
        EventBus.getDefault().unregister(this)
        super.onPause()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finish()
    }

    private fun setupRouter(savedInstanceState: Bundle?) {
        router = Conductor.attachRouter(this, controllerContainer, savedInstanceState)
        navigator = Navigator(router)
    }

    fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
    }

    fun navigateToController() {
        router?.setRoot(RouterTransaction.with(DetailController(id ?: -1)))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: ChangeToolbarTitle) {
        supportActionBar?.title = event.title
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: GoogleMapsIntent) {

        Log.e("***", event.latitude.toString())
        Log.e("***", event.longitude.toString())

        val intent = Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/maps/search/?api=1&query=" + event.latitude.toString() + "," + event.longitude.toString()))
        startActivity(intent)
        
    }

}
