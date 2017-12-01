package jimenez.miguel.com.cinepolis.controller.controllers

import android.app.Activity
import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputLayout
import android.support.v7.widget.AppCompatButton
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toolbar
import butterknife.BindView
import butterknife.OnClick
import jimenez.miguel.com.cinepolis.R
import jimenez.miguel.com.cinepolis.activities.MainActivity
import jimenez.miguel.com.cinepolis.model.entities.User
import jimenez.miguel.com.cinepolis.model.managers.UserManager
import jimenez.miguel.com.cinepolis.model.utils.LogUtils
import nl.komponents.kovenant.task
import nl.komponents.kovenant.ui.promiseOnUi

/**
 * Created by migueljimenez on 11/29/17.
 */
class LoginController: ButterKnifeController() {

    val tag = LoginController::class.java.simpleName!!


    @BindView(R.id.view_loading) lateinit var viewLoading: LinearLayout
    @BindView(R.id.edit_username) lateinit var editUsername: TextInputLayout
    @BindView(R.id.edit_password) lateinit var editPassword: TextInputLayout
    @BindView(R.id.btn_login) lateinit var btnLogin: AppCompatButton


    override fun onAttach(view: View) {
        super.onAttach(view)
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View =
            inflater.inflate(R.layout.controller_login, container, false)

    override fun onDetach(view: View) {
        super.onDetach(view)
    }

    fun setupListeners() {
        editPassword.editText?.setOnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                hideKeyboard()
                login()
            }
            false
        }
    }

    @OnClick(R.id.btn_login)
    fun btnLoginOnClick() {
        login()
    }

    private fun login() {
        var login = false

        var validateFields = validateFields()

        promiseOnUi {
            if (validateFields) {
                activity?.runOnUiThread {

                    editUsername.visibility = View.GONE
                    editPassword.visibility = View.GONE
                    btnLogin.visibility = View.GONE

                    viewLoading.visibility = View.VISIBLE
                }
            }
        }
        task {
            if (validateFields) {
                var user = User()
                user.username = editUsername.editText?.text.toString()
                user.password = editPassword.editText?.text.toString()

                task {
                    if(UserManager().login(user)){
                        val intent = Intent(activity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        activity?.startActivity(intent)
                    }
                    else{
                        //snackbar bad loggin
                    }
                }
            }
        } always {

            activity?.runOnUiThread {
                if(isAttached) {
                    editUsername.visibility = View.VISIBLE
                    editPassword.visibility = View.VISIBLE
                    btnLogin.visibility = View.VISIBLE

                    viewLoading.visibility = View.GONE
                }
             //   EventBus.getDefault().post(LoginEvent(login))
            }
        }
    }

    private fun validateFields(): Boolean {
        if (!validateField(editUsername)) {
            activity?.runOnUiThread {
                try {
                    Snackbar.make(this.view!!, R.string.txt_user_required, Snackbar.LENGTH_SHORT).show()
                } catch (ex: Exception) {
                    LogUtils.Error(tag, ex)
                }
            }
            return false
        }

        if (!validateField(editPassword)) {
            activity?.runOnUiThread {
                try {
                    Snackbar.make(this.view!!, R.string.txt_password_required, Snackbar.LENGTH_SHORT).show()
                } catch (ex: Exception) {
                    LogUtils.Error(tag, ex)
                }
            }
            return false
        } else {
            return true
        }
    }

    private fun validateField(textInputLayout: TextInputLayout?): Boolean =
            !textInputLayout?.editText?.text.toString().isEmpty()

    fun hideKeyboard() {
        activity?.let {
            val inputMethodManager: InputMethodManager = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = activity?.currentFocus
            if (view == null) {
                view = View(activity)
            }
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}