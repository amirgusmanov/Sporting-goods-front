package com.example.springboot_project_front.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.springboot_project_front.R
import com.example.springboot_project_front.data.ProductItem
import com.example.springboot_project_front.databinding.ProductItemBinding

class ProductAdapter(
    private val productList: MutableList<ProductItem> = mutableListOf(),
    private val deleteItem: (Long) -> Unit,
    private val editItem: (Long) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder(
            ProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = productList[position]
        holder.bind(item, position)
    }

    fun addElements(list: List<ProductItem>) {
        productList.clear()
        productList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductItem, position: Int) {
            binding.apply {
                ivPhoto.setImageResource(R.drawable.protein)
                tvName.text = item.name
                tvDescription.text = item.description
                tvQuantity.text = buildString {
                    append("x")
                    append(item.productQuantity)
                }
                tvPrice.text = buildString {
                    append(item.price)
                    append(" KZT")
                }
                this.root.setOnClickListener {
                    editItem(item.id)
                    notifyItemChanged(position)
                }
                this.root.setOnLongClickListener {
                    deleteItem(item.id)
                    productList.removeAt(position)
                    notifyItemRemoved(position)
                    return@setOnLongClickListener true
                }
            }
        }
    }
}