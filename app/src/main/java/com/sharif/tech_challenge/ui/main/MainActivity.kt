package com.sharif.tech_challenge.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.sharif.tech_challenge.R
import com.sharif.tech_challenge.data.model.CardModel
import com.sharif.tech_challenge.data.model.CardResult
import com.sharif.tech_challenge.databinding.ActivityMainBinding
import com.sharif.tech_challenge.iinterface.StatePlayerListener
import com.sharif.tech_challenge.ui.base.BaseActivity
import com.sharif.tech_challenge.utils.CodeType
import com.sharif.tech_challenge.utils.ThemeType
import com.sharif.tech_challenge.utils.createRandom
import com.sharif.tech_challenge.utils.extensions.*
import com.sharif.tech_challenge.utils.networkHelper.ResultNet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel,ActivityMainBinding>(), View.OnClickListener,StatePlayerListener {

    companion object{
        val arrayData = arrayListOf<CardResult>()
        val arrayShow = arrayListOf<CardResult>()
        lateinit var itemCard:CardResult
    }

    private var loading : Boolean = false
        set(value) {
            showLoading(value)
            field = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                is ResultNet.Success -> { manageSuccess(it.data) }
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
        mViewBinding.constContainer?.show()
        mViewBinding.tvError?.hide()

        arrayData.addAll(data.cards)
    }

    private fun <T> manageError(api: String, resultNet: T) {
        loading = false
        mViewBinding.constContainer?.hide()
        mViewBinding.tvError?.show()

        when(api){
            "Api" -> {
                mViewBinding.tvError?.text = (resultNet as ResultNet.ErrorApi<*>).errorCls?.message!!
            }
            "Exception" -> {
                mViewBinding.tvError?.text = (resultNet as ResultNet.ErrorException<*>)?.message!!
            }
            "Net"-> {
                mViewBinding.tvError?.text = (resultNet as ResultNet.ErrorNetwork<*>)?.message!!
            }
        }
    }
    //endregion

    //region util
    private fun click(){
        mViewBinding.btnTry.setOnClickListener(this)
    }

    private fun showLoading(value: Boolean){
        when(value){
            true -> {
                mViewBinding.progressBar?.show()
            }
            false -> {
                mViewBinding.progressBar?.hide()
            }
        }
    }

    private fun isItemRepeat(number :Int): Boolean {
        var item : CardResult ?= null
        var find = false

        if(arrayData.size == arrayShow.size) {
            arrayShow.clear()
        }

        for(i in 0 until arrayData.size) {
            if(number == arrayData[i].code) {
                item = arrayData[i]
                find = false

                for (j in 0 until arrayShow.size) {
                    if (item == arrayShow[j]){
                        find = true
                    }
                }

                if(!find) {
                    item?.let {
                        itemCard = it
                        addToArray(it)
                    }
                    return find
                }
            }
        }

        return find
    }

    private fun addToArray(item: CardResult){
        arrayShow.add(item)
    }


    private fun showCard(){
        var newNumber = createRandom()
        //Toast.makeText(this,newNumber.toString(),Toast.LENGTH_SHORT).show()
        Log.e("random", "$newNumber ")

        if(!isItemRepeat(newNumber)) {
            mViewBinding.btnTry.active()

            itemCard?.let {
                mViewBinding.cardMaterial.show()
                mViewBinding.imgIcon.show()
                mViewBinding.tvTitle.text = it?.title
                mViewBinding.tvDesc.text = it?.description

                changeTheme(it)
                actionCard(it)
            }
        } else {
            showCard()
        }
    }

    //endregion

    //region card
    private fun actionCard(item:CardResult) {
        when(item.code){
            CodeType.Picture.type -> {
                mViewBinding.imgCard.show()
                mViewBinding.imgCard.loadImage(item.image)
            }
            CodeType.Vibrate.type -> {
                mViewBinding.imgCard.gone()
                mViewModel.startVibrate()
            }
            CodeType.Sound.type -> {
                mViewBinding.imgCard.gone()
                item.sound?.let {
                    mViewModel.playSound(it,this)
                }
            }
        }
    }

    private fun changeTheme(item: CardResult){
        when(item.tag){
            ThemeType.Sport.type -> {
                mViewBinding.cardMaterial.backgroundTintList = getColorStateList(android.R.color.holo_blue_light)
                mViewBinding.imgIcon.setImageResource(R.drawable.ic_sport)
            }
            ThemeType.Art.type -> {
                mViewBinding.cardMaterial.backgroundTintList = getColorStateList(android.R.color.holo_red_light)
                mViewBinding.imgIcon.setImageResource(R.drawable.ic_art)
            }
            ThemeType.Fun.type -> {
                mViewBinding.cardMaterial.backgroundTintList = getColorStateList(android.R.color.holo_green_dark)
                mViewBinding.imgIcon.setImageResource(R.drawable.ic_fun)
            }
        }
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
                mViewBinding.btnTry.deActive()
                mViewModel.stopSound()
                showCard()
            }
        }
    }

    override fun start() {
       mViewBinding.progressBar.show()
    }

    override fun stop() {
       mViewBinding.progressBar.hide()
    }

    override fun onPause() {
        super.onPause()
        mViewModel.stopSound()
    }
}