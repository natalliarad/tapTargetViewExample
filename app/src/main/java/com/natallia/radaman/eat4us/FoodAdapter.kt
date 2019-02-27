package com.natallia.radaman.eat4us

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

class FoodAdapter(
    private val context: Context,
    private val foodTitle: Array<String>,
    private val foodImages: IntArray
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_food_row, parent, false)
        return FoodViewHolder(layout)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.title.text = foodTitle[position]
        holder.image.setImageResource(foodImages[position])
        holder.layout.setOnClickListener {
            val intent = Intent(context, FoodDetailActivity::class.java)
            intent.putExtra("title", foodTitle[position])
            intent.putExtra("image", foodImages[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return foodTitle.size
    }
    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.foodNameTextView)
        val image: ImageView = itemView.findViewById(R.id.foodImageView)
        val layout: RelativeLayout = itemView.findViewById(R.id.layout)
    }
}
