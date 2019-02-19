package com.smartificia.projectlms.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.smartificia.projectlms.R
import com.smartificia.projectlms.api.ApiService
import com.smartificia.projectlms.models.AccountInfo
import com.smartificia.projectlms.models.GenericResponse
import com.smartificia.projectlms.utils.GlobalLocalCache
import com.smartificia.projectlms.utils.Labels
import com.smartificia.projectlms.utils.isConnected
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        fabLogin.setOnClickListener {
            if (checkFieldsNotEmpty()) {
                if (isConnected(this))
                    validateLogin()
                else
                    showToast("Not Connected to Internet")
            }
        }
    }


    private fun showLoading(status: Boolean) {
        if (status) {
            loginLoader.visibility = View.VISIBLE
            fabLogin.hide()
        } else {
            loginLoader.visibility = View.GONE
            fabLogin.show()
        }
    }

    private fun validateLogin() {

        showLoading(status = true)
        val username = etEmailId.text.toString()
        val password = etPassword.text.toString()

        val loginApi = ApiService.getInstance().getLoginApi()
        loginApi.validateLogin(username, password)
            .enqueue(object : Callback<GenericResponse<AccountInfo>> {
                override fun onFailure(call: Call<GenericResponse<AccountInfo>>, t: Throwable) {
                    showLoading(status = false)
                    Log.i("PUI", "fail message ${t.message}")
                    showToast("Login Failed ! Try again later")
                }

                override fun onResponse(
                    call: Call<GenericResponse<AccountInfo>>,
                    response: Response<GenericResponse<AccountInfo>>
                ) {
                    showLoading(status = false)
                    val loginInfo = response.body()
                    if (loginInfo != null) {
                        Log.i("PUI", " status message : ${loginInfo.message}")
                        if (loginInfo.message == "success") {
                            showToast("Login Success")
                            val userInfo = loginInfo.response[0]
                            GlobalLocalCache.setAccountInfo(userInfo)
                            saveToSharedPrefs(userInfo)
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        } else {
                            showToast("Fail!! Check username or password")
                        }
                    } else {
                        showToast("Ooops! Something bad happened")
                    }
                }
            })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun checkFieldsNotEmpty() = when {
        etEmailId.text.isEmpty() -> {
            etEmailId.hint = "Email-Id Cannot be empgty"
            false
        }
        etPassword.text.isEmpty() -> {
            etPassword.hint = "Password Cannot be empgty"
            false
        }
        else -> true
    }

    private fun saveToSharedPrefs(accountInfo: AccountInfo) {
        val sharedPrefs = getSharedPreferences(Labels.sharedPrefs, Context.MODE_PRIVATE)

        sharedPrefs.edit().apply {
            putString(Labels.accountUserId, accountInfo.userid)
            putString(Labels.accountEmail, accountInfo.email)
            putString(Labels.accountFirstName, accountInfo.firstname)
            putString(Labels.accountLastName, accountInfo.lastname)
            putString(Labels.accountUserType, accountInfo.usertype)
        }.apply()
    }
}
