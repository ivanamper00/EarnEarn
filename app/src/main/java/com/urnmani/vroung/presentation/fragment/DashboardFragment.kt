package com.urnmani.vroung.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.dakuinternational.common.domain.model.DataContent
import com.dakuinternational.common.ui.base.BaseFragment
import com.dakuinternational.common.ui.binding.viewBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.urnmani.vroung.R
import com.urnmani.vroung.databinding.FragmentDashboardBinding
import com.urnmani.vroung.presentation.adapter.DashboardAdapter


class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    private val binding by viewBinding(FragmentDashboardBinding::bind)

    private val args: DashboardFragmentArgs by navArgs()

    private val dashboardAdapter by lazy {
        DashboardAdapter(requireActivity() as DashboardAdapter.OnItemClickListener)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.recyclerView){
            adapter = dashboardAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        val list: List<DataContent> = Gson().fromJson(args.listContent, object:TypeToken<List<DataContent>>(){}.type)
        dashboardAdapter.setList(list)
    }

    companion object{
        const val LIST_CONTENT = "listContent"
    }
}