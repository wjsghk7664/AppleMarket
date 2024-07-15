package com.example.applemarket

import android.graphics.Bitmap
import android.icu.text.Transliterator.Position
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.applemarket.databinding.FragmentRecyclerviewBinding
import com.example.applemarket.databinding.RecyclerviewItemBinding

class CustomAdapter() : RecyclerView.Adapter<CustomAdapter.Holder>() {

    interface ItemClick {
        fun onClick(view: View, position: Int)
        fun onLongClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val context = holder.binding.root.context
        holder.binding.root.setOnClickListener {
            itemClick?.onClick(it, position)
        }
        holder.binding.root.setOnLongClickListener {
            itemClick?.onLongClick(it, position)
            return@setOnLongClickListener (true)
        }
        val item = Items.items[position]
        holder.image.setImageDrawable(
            context.getDrawable(
                context.resources.getIdentifier(
                    item.image,
                    "drawable",
                    context.packageName
                )
            )
        )
        holder.title.text = item.title
        holder.location.text = item.location
        holder.price.text = item.price
        holder.heart.text = item.like.toString()
        holder.icHeart.setImageResource(if (item.checked) R.drawable.ic_filledheart else R.drawable.ic_heart)
    }

    override fun getItemCount(): Int {
        return Items.items.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class Holder(val binding: RecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val image = binding.itemImage
        val title = binding.itemTitle
        val location = binding.itemLocation
        val price = binding.itemPrice
        val heart = binding.itemHeartCount
        val icHeart = binding.itemIcHeart
    }

}