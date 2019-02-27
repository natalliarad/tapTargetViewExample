/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.natallia.radaman.eat4us

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var foodName = arrayOf(
        "Banana", "Cheese", "Chocolate cookie", "Chocolate chip cookie",
        "Cupcake", "Pancakes"
    )

    private var foodImage = intArrayOf(
        R.drawable.food_banana, R.drawable.food_cheese,
        R.drawable.food_cookie_chocolate, R.drawable.food_cookie_chocolatechip,
        R.drawable.food_cupcake, R.drawable.food_pancakes
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar.inflateMenu(R.menu.menu_toolbar)
        toolbar.title = resources.getString(R.string.app_name)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = FoodAdapter(this, foodName, foodImage)

        if (StatusUtils.getTutorialStatus(this, "showMain")) {
            TapTargetView.showFor(
                this,
                TapTarget.forToolbarOverflow(
                    toolbar,
                    getString(R.string.label_app_settings),
                    getString(R.string.description_app_setting)
                ).cancelable(false).tintTarget(true),
                object : TapTargetView.Listener() {
                    override fun onTargetClick(view: TapTargetView) {
                        super.onTargetClick(view)
                        view.dismiss(true)
                    }
                })

            TapTargetView.showFor(
                this,
                TapTarget.forToolbarMenuItem(
                    toolbar,
                    R.id.action_search,
                    getString(R.string.label_search),
                    getString(R.string.description_search)
                ).cancelable(false).tintTarget(true),
                object : TapTargetView.Listener() {
                    override fun onTargetClick(view: TapTargetView) {
                        super.onTargetClick(view)
                        view.dismiss(true)
                        StatusUtils.storeTutorialStatus(this@MainActivity, "showMain", false)
                    }
                }
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        return true
    }

    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setMessage(
            "${resources.getString(R.string.msg_sure_exit)}"
                + "${resources.getString(R.string.app_name)}?"
        )
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE,
            getString(R.string.label_ok)
        ) { _, _ ->
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, getString(R.string.label_no)
        ) { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        alertDialog.show()

        if (StatusUtils.getTutorialStatus(this, "showExit")) {
            TapTargetView.showFor(
                alertDialog,
                TapTarget.forView(
                    alertDialog.getButton(DialogInterface.BUTTON_POSITIVE),
                    getString(R.string.label_exit_app),
                    getString(R.string.description_exit)
                ).cancelable(false).tintTarget(false),
                object : TapTargetView.Listener() {
                    override fun onTargetClick(view: TapTargetView) {
                        super.onTargetClick(view)
                        view.dismiss(true)
                        StatusUtils.storeTutorialStatus(this@MainActivity, "showExit", false)
                    }
                })
        }
    }
}
