package com.smartificia.projectlms.ui.activities

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.smartificia.projectlms.R
import com.smartificia.projectlms.models.AccountInfo
import com.smartificia.projectlms.utils.GlobalLocalCache
import com.smartificia.projectlms.utils.Labels
import kotlinx.android.synthetic.main.activity_splash.*
import android.util.Pair as UtilPair

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val sharedPrefs = getSharedPreferences(Labels.sharedPrefs, Context.MODE_PRIVATE)
        val userId = sharedPrefs.getString(Labels.accountUserId, "not-set")

        if (userId == "not-set") {
            Handler().postDelayed(
                {
                    val intent = Intent(this, LoginActivity::class.java)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                        val options = ActivityOptions.makeSceneTransitionAnimation(
                            this,
                            UtilPair.create(ivAppIcon, "app_icon")
                        )
                        startActivity(intent, options.toBundle())
                    } else {
                        startActivity(intent)
                    }
                },
                2500
            )
        } else {
            val accountInfo = with(sharedPrefs) {
                AccountInfo(
                    usertype = getString(Labels.accountUserType, "not-set")!!,
                    firstname = getString(Labels.accountFirstName, "not-set")!!,
                    lastname = getString(Labels.accountLastName, "not-set")!!,
                    email = getString(Labels.accountEmail, "not-set")!!,
                    userid = userId!!
                )
            }
            GlobalLocalCache.setAccountInfo(accountInfo)
            Handler().postDelayed(
                { startActivity(Intent(this, MainActivity::class.java)) },
                2500
            )


        }


    }
}
