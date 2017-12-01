package jimenez.miguel.com.cinepolis.controller.controllers

import android.os.Bundle
import android.support.annotation.NonNull
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.bluelinelabs.conductor.Controller

/**
 * Created by migueljimenez on 11/29/17.
 */
abstract class ButterKnifeController : Controller {

    var unbinder: Unbinder? = null

    protected constructor() {}

    protected constructor(args: Bundle) : super(args) {}

    protected abstract fun inflateView(@NonNull inflater: LayoutInflater, @NonNull container: ViewGroup): View

    @NonNull
    override fun onCreateView(@NonNull inflater: LayoutInflater, @NonNull container: ViewGroup): View {
        val view = inflateView(inflater, container)
        unbinder = ButterKnife.bind(this, view)
        onViewBound(view)
        return view
    }

    protected fun onViewBound(@NonNull view: View) {
    }

    override fun onDestroyView(@NonNull view: View) {
        super.onDestroyView(view)
        unbinder?.unbind()
        unbinder = null
    }


}
