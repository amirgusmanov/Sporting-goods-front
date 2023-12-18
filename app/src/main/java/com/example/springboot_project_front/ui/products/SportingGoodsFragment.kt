package com.example.springboot_project_front.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.springboot_project_front.R
import com.example.springboot_project_front.base.makeInvisible
import com.example.springboot_project_front.base.show
import com.example.springboot_project_front.databinding.DialogCreateLayoutBinding
import com.example.springboot_project_front.databinding.DialogLayoutBinding
import com.example.springboot_project_front.databinding.FragmentSportingGoodsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class SportingGoodsFragment : Fragment() {

    private var binding: FragmentSportingGoodsBinding? = null
    private val viewModel: SportingGoodsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSportingGoodsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        setupObservers()
        setupProductList()
    }

    private fun bindViews() {
        val token = arguments?.getString("token")
        binding?.tvToken?.text = token
        binding?.create?.setOnClickListener { createElement() }
        binding?.refresh?.setOnClickListener { viewModel.getAllSportingGoods() }
    }

    private fun setupProductList() {
//        viewModel.getAllSportingGoods()
        binding?.rvProducts?.adapter = ProductAdapter(
            deleteItem = ::deleteElement,
            editItem = ::editElement
        )
        binding?.rvProducts?.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun createElement() {
        val dialogView = DialogCreateLayoutBinding.inflate(layoutInflater)
        val nameEditText = dialogView.tlName
        val priceEditText = dialogView.tlPrice
        val descriptionEditText = dialogView.tlDescription
        val quantityEditText = dialogView.tlCount

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.create_product))
            .setView(dialogView.root)
            .setPositiveButton(getString(R.string.save)) { dialog, _ ->
                val name = nameEditText.editText?.text.toString()
                val price = priceEditText.editText?.text.toString().toDoubleOrNull() ?: 0.0
                val description = descriptionEditText.editText?.text.toString()
                val quantity = quantityEditText.editText?.text.toString().toIntOrNull() ?: 0
                viewModel.createElement(
                    name = name,
                    price = price,
                    description = description,
                    count = quantity
                )
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
            .create()

        dialog.show()
    }

    private fun deleteElement(id: Long) {
        viewModel.deleteElement(id)
    }

    private fun editElement(id: Long) {
        val dialogView = DialogLayoutBinding.inflate(layoutInflater)
        val quantityEditText = dialogView.editTextQuantity

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.enter_product_quantity))
            .setView(dialogView.root)
            .setPositiveButton(getString(R.string.save)) { dialog, _ ->
                val quantity = quantityEditText.editText?.text.toString().toIntOrNull() ?: 0
                viewModel.updateElement(id, quantity)
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
            .create()

        dialog.show()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is SportingGoodsViewModel.State.HideLoading -> {
                        binding?.rvProducts?.show()
                        binding?.pbProgress?.makeInvisible()
                        binding?.refresh?.show()
                        binding?.create?.show()
                    }

                    is SportingGoodsViewModel.State.Products -> {
                        (binding?.rvProducts?.adapter as ProductAdapter).addElements(state.list)
                    }

                    is SportingGoodsViewModel.State.ShowLoading -> {
                        binding?.rvProducts?.makeInvisible()
                        binding?.pbProgress?.show()
                        binding?.refresh?.makeInvisible()
                        binding?.create?.makeInvisible()
                    }

                    is SportingGoodsViewModel.State.Success -> {
                        viewModel.getAllSportingGoods()
                    }
                }
            }
        }
    }
}