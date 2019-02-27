package com.natallia.radaman.eat4us

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import kotlinx.android.synthetic.main.activity_food_detail.*

class FoodDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)

        toolbar.title = intent.getStringExtra("title")
        foodImageView.setImageResource(intent.getIntExtra("image", R.drawable.food_banana))

        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        val intent = Intent()

        btnShare.setOnClickListener {
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, "I'm eating ${toolbar.title}")
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, getString(R.string.label_send_to)))
        }

        btnStore.setOnClickListener {
            intent.action = Intent.ACTION_VIEW
            intent.data = Uri.parse("https://www.freshdirect.com/index.jsp")
            startActivity(intent)
        }


        if (StatusUtils.getTutorialStatus(this, "showDetails")) {
            TapTargetSequence(this).targets(
                TapTarget.forView(
                    btnShare,
                    getString(R.string.label_share_food),
                    getString(R.string.description_share_food)
                ).cancelable(false).transparentTarget(true).targetRadius(70),
                TapTarget.forView(
                    btnStore,
                    getString(R.string.label_buy_food),
                    getString(R.string.description_buy_food)
                ).cancelable(false).transparentTarget(true).targetRadius(70),
                TapTarget.forToolbarNavigationIcon(
                    toolbar,
                    getString(R.string.label_back_arrow),
                    getString(R.string.description_back_arrow)
                ).cancelable(false).tintTarget(true)
            ).listener(object : TapTargetSequence.Listener {

                override fun onSequenceStep(lastTarget: TapTarget?, targetClicked: Boolean) {
                }

                override fun onSequenceFinish() {
                    Toast.makeText(
                        this@FoodDetailActivity, getString(R.string.msg_tutorial_complete),
                        Toast.LENGTH_LONG
                    ).show()
                    StatusUtils.storeTutorialStatus(this@FoodDetailActivity, "showDetails", false)
                }

                override fun onSequenceCanceled(lastTarget: TapTarget) {
                }
            }).start()
        }
    }
}
