package com.olar.ui.recycleing.helper

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leesche.yyyiotlib.entity.UnitEntity
import com.olar.databinding.ItemDevStatusBinding

class DevStatusAdapter(var list: List<UnitEntity>) :
    RecyclerView.Adapter<DevStatusAdapter.MyViewHolder>() {
    class MyViewHolder(val mView: ItemDevStatusBinding) : RecyclerView.ViewHolder(mView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemDevStatusBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var item = list[position]
        holder.mView.tvUnitName.text = item.unit_name

//        helper.addOnClickListener(R.id.ll_type_root)
        if (item.unit_no == 0) {
            if (item.getUnit_type() == 0) {
                holder.mView.tvUnitName.setBackgroundColor(Color.parseColor("#20D86E"))
            } else {
                holder.mView.tvUnitName.setBackgroundColor(Color.RED)
            }
        }
        if (item.unit_no == 1) {
            if (item.getUnit_type() == 1) {
                holder.mView.tvUnitName.setBackgroundColor(Color.parseColor("#20D86E"))
            } else {
                holder.mView.tvUnitName.setBackgroundColor(Color.RED)
            }
        }
        if (item.unit_no == 2) {
            if (!item.unit_name.contains("ERR")) {
                holder.mView.tvUnitName.setBackgroundColor(Color.parseColor("#20D86E"))
            } else {
                holder.mView.tvUnitName.setBackgroundColor(Color.RED)
            }
        }
    }
    fun setNewData(list: List<UnitEntity>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}