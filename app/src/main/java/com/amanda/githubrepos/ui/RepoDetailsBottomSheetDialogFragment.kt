package com.amanda.githubrepos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.amanda.githubrepos.R
import com.amanda.githubrepos.data.UserReposItem
import com.amanda.githubrepos.databinding.RepoDetailsBottomSheetModalBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

//This is the fragment that will be used to show the bottomsheet dialog with repo details
class RepoDetailsBottomSheetDialogFragment(private val repo: UserReposItem) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Using databinding to try to reduce the boilerplate code a little
        val binding: RepoDetailsBottomSheetModalBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.repo_details_bottom_sheet_modal,
            container,
            false
        )
        binding.repo = this.repo
        return binding.root
    }
}