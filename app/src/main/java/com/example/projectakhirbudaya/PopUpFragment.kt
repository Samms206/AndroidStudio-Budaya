package com.example.projectakhirbudaya

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT

import android.widget.Button
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.projectakhirbudaya.room.BudayaEntity
import com.example.projectakhirbudaya.viewmodel.BudayaViewModel
import com.example.projectakhirbudaya.viewmodel.BudayaViewModelFactory


class PopUpFragment(private val budayaEntity: BudayaEntity, position: Int) : DialogFragment() {

    private lateinit var budayaViewModel: BudayaViewModel

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onStart() {
        super.onStart()
        requireDialog().window?.apply {
            setLayout(WRAP_CONTENT, WRAP_CONTENT)
        }

        view?.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            setMargins(16, 16, 16, 16)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pop_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = BudayaViewModelFactory.getInstance(requireContext())
        budayaViewModel = ViewModelProvider(this, factory)[BudayaViewModel::class.java]

        val btnUbah: Button = view.findViewById(R.id.btn_ubah)
        val btnHapus: Button = view.findViewById(R.id.btn_hapus)

        btnUbah.setOnClickListener {
            val intent = Intent(requireContext(), EditBudayaActivity::class.java)
            intent.putExtra("dataBudaya", budayaEntity)
            startActivity(intent)
            dismiss()
        }

        btnHapus.setOnClickListener {
            budayaViewModel.deleteBudaya(budayaEntity)
            dismiss()
        }
    }

    companion object {
        const val TAG = "PopUpFragment"
    }
}