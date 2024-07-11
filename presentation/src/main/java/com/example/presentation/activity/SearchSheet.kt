package com.example.presentation.activity

import com.example.presentation.R
import com.example.presentation.databinding.SearchSheetLayoutBinding
import com.example.presentation.util.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SearchSheet : BottomSheetDialogFragment(R.layout.search_sheet_layout) {
    private val binding by viewBinding(SearchSheetLayoutBinding::bind)



    companion object {
        const val TAG = "SearchSheet"
    }
}