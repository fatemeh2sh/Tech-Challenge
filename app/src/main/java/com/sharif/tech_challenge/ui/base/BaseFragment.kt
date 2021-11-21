package com.sharif.tech_challenge.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VM : ViewModel, VB : ViewBinding> : Fragment() {

    lateinit var mActivity: AppCompatActivity
    lateinit var currentView: View
    lateinit var navController: NavController
    var mViewBindingFrag: VB? = null
    protected val mViewModelFrag by lazy { getViewModel() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if(mViewBindingFrag == null){
            mViewBindingFrag = getViewBinding()
        }

       return mViewBindingFrag?.root
    }

    abstract fun getViewModel(): VM

    abstract fun getViewBinding(): VB?

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
        navController = findNavController()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewLifecycleOwnerLiveData.removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mViewBindingFrag != null)
            mViewBindingFrag = null
    }
}