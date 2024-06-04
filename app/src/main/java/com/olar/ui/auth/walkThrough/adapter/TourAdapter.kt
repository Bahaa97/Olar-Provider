package com.olar.ui.auth.walkThrough.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.olar.Model.TourModal
import com.olar.databinding.TourItemBinding

class TourAdapter(val con: Context, val list: ArrayList<TourModal>) : PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding: TourItemBinding = TourItemBinding.inflate(
            LayoutInflater.from(
                con
            ), container, false
        )


        binding.ivMain.setImageResource(list[position].img)
        binding.tvTitle.text = list[position].title
        binding.tvDes.text = list[position].des


        container.addView(binding.root, 0)
        return binding.root
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
