package jimenez.miguel.com.cinepolis.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.bluelinelabs.conductor.ChangeHandlerFrameLayout
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import jimenez.miguel.com.cinepolis.R
import jimenez.miguel.com.cinepolis.controller.controllers.LoginController
import jimenez.miguel.com.cinepolis.controller.controllers.MoviesController
import jimenez.miguel.com.cinepolis.controller.controllers.Navigator
import jimenez.miguel.com.cinepolis.model.utils.C

/**
 * Created by migueljimenez on 11/29/17.
 */
class MainActivity : AppCompatActivity() {
    val tag = MainActivity::class.java.simpleName

    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar
    @BindView(R.id.controller_container) lateinit var controllerContainer: ChangeHandlerFrameLayout

    private var router: Router? = null
    private var navigator: Navigator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)
        setupRouter(savedInstanceState)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Cartelera"

        navigateToController(C.MainSections.MOVIES)

    }

    private fun setupRouter(savedInstanceState: Bundle?) {
        router = Conductor.attachRouter(this, controllerContainer, savedInstanceState)
        navigator = Navigator(router)
    }

    fun navigateToController(section: C.MainSections) {
        when (section) {
            C.MainSections.MOVIES -> {
                router?.setRoot(RouterTransaction.with(MoviesController()))
            }
        }
    }

}