package com.urnmani.vroung.presentation.activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.dakuinternational.common.domain.model.DataContent
import com.dakuinternational.common.domain.model.Response
import com.dakuinternational.common.ui.ActivityViewModel
import com.dakuinternational.common.ui.base.BaseActivity
import com.dakuinternational.common.ui.binding.viewBinding
import com.dakuinternational.common.ui.dialog.AlertUtils
import com.dakuinternational.common.ui.utils.showToast
import com.dakuinternational.common.ui.utils.writeLog
import com.google.gson.Gson
import com.urnmani.vroung.R
import com.urnmani.vroung.databinding.ActivityMainBinding
import com.urnmani.vroung.presentation.adapter.DashboardAdapter
import com.urnmani.vroung.presentation.fragment.DashboardFragment
import com.urnmani.vroung.presentation.fragment.DashboardFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), DashboardAdapter.OnItemClickListener {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    private val viewModel by viewModels<ActivityViewModel>()

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.navigation_host) as NavHostFragment
    }

    private val navController get() = navHostFragment.navController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.getData(DATABASE_NAME)
        viewModel.uiState.observe(this){
            when(it){
                is Response.Loading -> showLoading(it.isLoading)
                is Response.Error -> showToast(it.message)
                is Response.Success ->  shareData(it.data)
                else -> {
                    //no - op
                }
            }
        }
    }

    private fun shareData(data: List<DataContent>) {
        writeLog(Gson().toJson(data))
        intent.putExtra(DashboardFragment.LIST_CONTENT, Gson().toJson(data))
        navController.setGraph(R.navigation.navigation_graph, intent.extras)
    }

    override fun onBackPressed() {
        if(navHostFragment.childFragmentManager.backStackEntryCount == 0) {
            AlertUtils.alertExit(this){ p0, p1 ->
                when(p1){
                    DialogInterface.BUTTON_POSITIVE -> super.onBackPressed()
                    else ->  p0.dismiss()
                }
            }.show()
        }else super.onBackPressed()
    }

    companion object{
        private val DATABASE_NAME = "vietnam_earn_money"
        fun createIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

    override fun onItemClick(data: DataContent) {
        val direction = DashboardFragmentDirections.actionDashboardFragmentToDetailsFragment(Gson().toJson(data))
        navController.navigate(direction)
    }
}