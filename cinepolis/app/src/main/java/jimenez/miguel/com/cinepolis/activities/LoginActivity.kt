package jimenez.miguel.com.cinepolis.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.Toolbar
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.bluelinelabs.conductor.ChangeHandlerFrameLayout
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import jimenez.miguel.com.cinepolis.R
import jimenez.miguel.com.cinepolis.controller.controllers.LoginController
import jimenez.miguel.com.cinepolis.controller.controllers.Navigator
import jimenez.miguel.com.cinepolis.model.utils.C

class LoginActivity : AppCompatActivity(){

    @BindView(R.id.controller_container) lateinit var controllerContainer: ChangeHandlerFrameLayout
//    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar

    private var router: Router? = null
    private var navigator: Navigator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)
        setupRouter(savedInstanceState)

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        navigateToController(C.LoginSections.LOGIN)

    }

    private fun setupRouter(savedInstanceState: Bundle?) {
        router = Conductor.attachRouter(this, controllerContainer, savedInstanceState)
        navigator = Navigator(router)
    }

    fun navigateToController(section: C.LoginSections) {
        when (section) {
            C.LoginSections.LOGIN -> {
                    router?.setRoot(RouterTransaction.with(LoginController()))
            }
        }

    }

}
