package com.hail.kotlindemo.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.hail.kotlindemo.R
import com.hail.kotlindemo.model.GankResults

/**
 * Created by 小智
 * on 2017/7/29
 * 描述：
 */

class HomeAdapter(context: Context) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    private var context: Context = context
    private var data = ArrayList<GankResults.Item>()
    private var itemClickListener: ItemClickListener? = null

    fun setData(data: List<GankResults.Item>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun addData(data: List<GankResults.Item>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: HomeViewHolder?, position: Int) {
        val dataItem: GankResults.Item = data[position]
        val type: String? = dataItem.type
        when (type) {
            "休息视频" -> {
                holder?.rlMessage?.visibility = View.VISIBLE
                holder?.ivPart?.visibility = View.GONE
                holder?.ivVedio?.visibility = View.VISIBLE
                holder?.tvItem?.text = dataItem.desc
            }
            "福利" -> {
                holder?.rlMessage?.visibility = View.GONE
                holder?.ivPart?.visibility = View.VISIBLE
                holder?.ivVedio?.visibility = View.GONE
                Glide.with(context).load(dataItem.url).into(holder?.ivPart)
            }
            else -> {
                holder?.rlMessage?.visibility = View.VISIBLE
                holder?.ivPart?.visibility = View.GONE
                holder?.ivVedio?.visibility = View.GONE
                holder?.tvItem?.text = dataItem.desc
            }
        }
        when (dataItem.type) {
            "Android" -> holder?.ivType?.setImageResource(R.mipmap.android_icon)
            "iOS" -> holder?.ivType?.setImageResource(R.mipmap.ios_icon)
            "前端" -> holder?.ivType?.setImageResource(R.mipmap.js_icon)
            "拓展资源" -> holder?.ivType?.setImageResource(R.mipmap.other_icon)
            else -> holder?.ivType?.setImageResource(R.mipmap.android_icon)
        }
        val author = dataItem.who
        if (author != null) {
            holder?.tvAuthor?.text = author
            holder?.tvAuthor?.setTextColor(Color.parseColor("#87000000"))
        } else {
            holder?.tvAuthor?.text = ""
        }
        holder?.tvTime?.text = dataItem.createdAt
        holder?.tvType?.text = type

        holder?.itemView?.setOnClickListener {
            itemClickListener?.onItemclick(dataItem.url, dataItem.desc)
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): HomeViewHolder {
        return HomeViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_home, parent, false))
    }

    inner class HomeViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        internal var ivType: ImageView
        internal var tvType: TextView
        internal var ivAuthor: ImageView
        internal var tvAuthor: TextView
        internal var tvTime: TextView
        internal var rlMessage: RelativeLayout
        internal var ivPart: ImageView
        internal var ivVedio: ImageView
        internal var tvItem: TextView

        init {
            ivType = itemView.findViewById(R.id.iv_type) as ImageView
            tvType = itemView.findViewById(R.id.tv_type) as TextView
            ivAuthor = itemView.findViewById(R.id.iv_author) as ImageView
            tvAuthor = itemView.findViewById(R.id.tv_author) as TextView
            tvTime = itemView.findViewById(R.id.tv_time) as TextView
            rlMessage = itemView.findViewById(R.id.rl_message) as RelativeLayout
            ivPart = itemView.findViewById(R.id.iv_part) as ImageView
            ivVedio = itemView.findViewById(R.id.iv_vedio) as ImageView
            tvItem = itemView.findViewById(R.id.tv_item) as TextView
        }
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemclick(url: String?, desc: String?)
    }
}
