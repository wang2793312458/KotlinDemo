package com.hail.kotlindemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.telephony.TelephonyManager
import com.hail.kotlindemo.adapter.MainAdapter
import com.hail.kotlindemo.fragment.HomeListFragment
import com.hail.kotlindemo.fragment.GankListFragment
import com.hail.kotlindemo.fragment.GirlListFragment
import com.ly.kotlindemo.conf
import kotlinx.android.synthetic.main.activity_main.*
import net.youmi.android.AdManager
import net.youmi.android.nm.sp.SplashViewSettings

class MainActivity : AppCompatActivity() {
    /**
     * 碎片列表
     */
    val fragmentList = arrayListOf(
            HomeListFragment.newInstance(),
            GankListFragment.newInstance()
//            GirlListFragment.newInstance()
    )
    /**
     * 菜单
     */
    val menuList = arrayListOf(
            "首页", "干货", "最爱"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        AdManager.getInstance(this).init(conf().appId, conf().appSecret, true)
        setSupportActionBar(toolbar)
        vp_content.adapter = MainAdapter(supportFragmentManager, fragmentList, menuList)
        vp_content.offscreenPageLimit = fragmentList.size - 1
        rab_menu.setupWithViewPager(vp_content)
        val tel: TelephonyManager = (getSystemService(TELEPHONY_SERVICE) as TelephonyManager)
        val deviceId = tel.deviceId

        val splashViewSettings: SplashViewSettings = SplashViewSettings()
        splashViewSettings.isAutoJumpToTargetWhenShowFailed = true
        splashViewSettings.targetClass = MainActivity::class.java
    }
}
