package com.vijay.tatvasoftandroidtask.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ravikoradiya.liveadapter.LiveAdapter
import com.vijay.tatvasoftandroidtask.BR
import com.vijay.tatvasoftandroidtask.R
import com.vijay.tatvasoftandroidtask.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val mHomeViewModel: HomeViewModel by viewModel()
    private lateinit var mBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mHomeViewModel.getUsers()

        setUserListAdapter()
    }

    private fun setUserListAdapter() {

        LiveAdapter(mHomeViewModel.mainList, viewLifecycleOwner)
            .map<HomeViewModel.ItemUser>(R.layout.item_user, BR.user)
            .map<HomeViewModel.ItemImage>(R.layout.item_user_item, BR.userItem)
            .into(mBinding.listUsers)

        (mBinding.listUsers.layoutManager as GridLayoutManager).spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val item = mHomeViewModel.mainList.value?.get(position)
                    if (item is HomeViewModel.ItemUser) {
                        return 2
                    } else if (item is HomeViewModel.ItemImage && item.showFull) {
                        return 2
                    } else {
                        return 1
                    }
                }
            }

        mBinding.listUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = (mBinding.listUsers.layoutManager as GridLayoutManager)
                val visibleItemCount: Int = layoutManager.childCount
                val totalItemCount: Int = layoutManager.itemCount
                val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()

                mHomeViewModel.listScrolled(
                    visibleItemCount,
                    firstVisibleItemPosition,
                    totalItemCount
                )
            }
        })
    }
}