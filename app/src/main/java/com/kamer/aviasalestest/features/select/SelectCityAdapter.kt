package com.kamer.aviasalestest.features.select

import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kamer.aviasalestest.R
import com.kamer.aviasalestest.model.City
import kotlinx.android.synthetic.main.item_city.view.*
import kotlinx.android.synthetic.main.item_message.view.*


//If I had the time, I would implement delegation
class SelectCityAdapter(
    private val onCityClicked: (City) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<SelectCityItem>() {
        override fun areItemsTheSame(p0: SelectCityItem, p1: SelectCityItem): Boolean {
            return p0 == p1
        }

        override fun areContentsTheSame(p0: SelectCityItem, p1: SelectCityItem): Boolean {
            return p0 == p1
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (Type.values()[viewType]) {
            Type.CITY ->
                CityViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false),
                    onCityClicked
                )
            Type.MESSAGE ->
                MessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false))
            else ->
                ProgressViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_progress, parent, false))
        }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CityViewHolder -> holder.bind(differ.currentList[position] as CityItem)
            is MessageViewHolder -> holder.bind(differ.currentList[position] as MessageItem)
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (differ.currentList[position]) {
            is CityItem -> Type.CITY.ordinal
            is MessageItem -> Type.MESSAGE.ordinal
            else -> Type.PROGRESS.ordinal
        }

    fun setData(data: List<SelectCityItem>) {
        differ.submitList(data)
    }

    class CityViewHolder(view: View, onCityClicked: (City) -> Unit) : RecyclerView.ViewHolder(view) {

        private val nameView: TextView = view.nameView
        private val iataView: TextView = view.iataView

        lateinit var city: City

        init {
            view.setOnClickListener { onCityClicked(city) }
        }

        fun bind(model: CityItem) {
            city = model.city
            nameView.text = city.name
            iataView.text = city.iata
        }

    }

    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val messageView: TextView = view.messageView

        fun bind(model: MessageItem) {
            messageView.text = model.message
        }

    }

    class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view)
}

private enum class Type {
    CITY,
    MESSAGE,
    PROGRESS
}