package com.hail.kotlindemo.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hail.kotlindemo.R
import com.hail.kotlindemo.activity.SeeWebViewActivity
import com.hail.kotlindemo.adapter.HomeAdapter
import com.hail.kotlindemo.model.GankResults
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.ly.kotlindemo.net.ApiServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * Created by 小智
 * on 2017/7/29
 * 描述：
 */
class HomeListFragment : Fragment() {

    var pagerNo: Int = 1
    private var homeAdapter: HomeAdapter? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(activity).inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeAdapter = HomeAdapter(activity)
        xrlv_list.adapter = homeAdapter
        getData(pagerNo)
        xrlv_list.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onLoadMore() {

                getData(pagerNo)
            }

            override fun onRefresh() {
                pagerNo = 1
                getData(pagerNo)
            }
        })
        homeAdapter?.setItemClickListener(object : HomeAdapter.ItemClickListener {
            override fun onItemclick(url: String?, desc: String?) {
                val intent = Intent(activity, SeeWebViewActivity::class.java)
                intent.putExtra("url", url)
                intent.putExtra("desc", desc)
                startActivity(intent)
            }
        })
    }

    private fun getData(page: Int) {
        ApiServices.SearchRepository
                .SearchRepositoryProvider
                .provideSearchRepository().searchUsers("all", 10, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    result ->
                    when (page) {
                        1 -> homeAdapter?.setData(result.results as List<GankResults.Item>)
                        else -> homeAdapter?.addData(result.results as List<GankResults.Item>)
                    }
                    pagerNo++
                    xrlv_list?.loadMoreComplete()
                    xrlv_list?.refreshComplete()
                }, {
                    error ->
                    xrlv_list?.loadMoreComplete()
                    xrlv_list?.refreshComplete()
                    error.printStackTrace()
                })
    }

    companion object {
        fun newInstance(): HomeListFragment {
            val args = Bundle()
            val fragment = HomeListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}