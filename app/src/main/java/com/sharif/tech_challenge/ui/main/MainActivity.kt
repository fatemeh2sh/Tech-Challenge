package com.sharif.tech_challenge.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import com.sharif.tech_challenge.R
import com.sharif.tech_challenge.data.model.CardModel
import com.sharif.tech_challenge.databinding.ActivityMainBinding
import com.sharif.tech_challenge.ui.base.BaseActivity
import com.sharif.tech_challenge.utils.extensions.gone
import com.sharif.tech_challenge.utils.extensions.hide
import com.sharif.tech_challenge.utils.extensions.show
import com.sharif.tech_challenge.utils.networkHelper.ResultNet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel,ActivityMainBinding>(), View.OnClickListener {

    private var loading : Boolean = false
        set(value) {
            showLoading(value)
            field = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getData()

        init()
    }

    private fun init() {
        observeService()
        click()
    }

    private fun observeService(){
        mViewModel.cardResult.observe(this,{
            when(it){
                is ResultNet.Loading -> { loading = true }
                is ResultNet.Success -> {
                    manageSuccess(it.data)
                }
                is ResultNet.ErrorApi -> { manageError("Api",it) }
                is ResultNet.ErrorException -> { manageError("Exception", it) }
                is ResultNet.ErrorNetwork -> { manageError("Net", it) }
            }
        })
    }

    private fun getData(){
        mViewModel.getCards()
    }

    //region manage
    private fun manageSuccess(data: CardModel) {
        loading = false
    }

    private fun <T> manageError(api: String, resultNet: T) {
        loading = false
        when(api){
            "Api" -> {
                Log.e("",(resultNet as ResultNet.ErrorApi<*>).errorCls?.message!!)
            }
            "Exception" -> {
                Log.e("",(resultNet as ResultNet.ErrorException<*>)?.message!!)
            }
            "Net"-> {
                Log.e("",(resultNet as ResultNet.ErrorNetwork<*>)?.message!!)
            }
        }
    }
    //endregion

    //region util
    private fun showLoading(value: Boolean){
        when(value){
            true -> {
                mViewBinding.progressBar?.show()
                mViewBinding.constContainer?.hide()
            }
            false -> {
               // mViewBinding.progressBar?.hide()
                findViewById<ProgressBar>(R.id.progressBar).hide()
               // mViewBinding.constContainer?.show()
                findViewById<ProgressBar>(R.id.constContainer).show()
            }

        }
    }
    private fun click(){
        mViewBinding.btnTry.setOnClickListener(this)
    }
    //endregion

    override fun getViewModel(): MainViewModel {
        val mainViewModel: MainViewModel by viewModels()
        return mainViewModel
    }

    override fun getViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnTry -> {

            }
        }
    }
}