package com.natallia.radaman.eat4us

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        toolbar.title = getString(R.string.screen_setting_title)
        appNameTextView.text = resources.getString(R.string.app_name)
        versionTextView.text = BuildConfig.VERSION_NAME

        if (StatusUtils.getTutorialStatus(this, "showSettings")) {
            TapTargetView.showFor(
                this,
                TapTarget.forView(
                    appIconImageView, getString(R.string.label_icon),
                    "${getString(R.string.msg_icon_settings)} ${appNameTextView.text}"
                ).tintTarget(false).cancelable(false),
                object : TapTargetView.Listener() {
                    override fun onTargetClick(view: TapTargetView) {
                        super.onTargetClick(view)
                        view.dismiss(true)
                        StatusUtils.storeTutorialStatus(this@SettingsActivity, "showSettings", false)
                    }
                })
        }
    }
}