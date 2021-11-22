package com.sharif.tech_challenge.ui.main

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.core.widget.ImageViewCompat
import com.sharif.tech_challenge.R
import com.sharif.tech_challenge.data.model.CardModel
import com.sharif.tech_challenge.data.model.CardResult
import com.sharif.tech_challenge.databinding.ActivityMainBinding
import com.sharif.tech_challenge.service.sound.Sound
import com.sharif.tech_challenge.service.vibrate.Vibrate
import com.sharif.tech_challenge.ui.base.BaseActivity
import com.sharif.tech_challenge.utils.createRandom
import com.sharif.tech_challenge.utils.extensions.*
import com.sharif.tech_challenge.utils.networkHelper.ResultNet
import com.sharif.tech_challenge.utils.playMusic
import com.sharif.tech_challenge.utils.vibrate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel,ActivityMainBinding>(), View.OnClickListener {

    private var loading : Boolean = false
        set(value) {
            showLoading(value)
            field = value
        }

    companion object{
        val arrayData = arrayListOf<CardResult>()
        val arrayShow = arrayListOf<CardResult>()
        var randomNumber : Int = -1
        lateinit var itemCard:CardResult
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
                Log.e("",(resultNet as ResultNet.ErrorApi<*>).errorCls?.message!!)
                mViewBinding.tvError?.text = (resultNet as ResultNet.ErrorApi<*>).errorCls?.message!!
            }
            "Exception" -> {
                Log.e("",(resultNet as ResultNet.ErrorException<*>)?.message!!)
                mViewBinding.tvError?.text = (resultNet as ResultNet.ErrorException<*>)?.message!!
            }
            "Net"-> {
                Log.e("",(resultNet as ResultNet.ErrorNetwork<*>)?.message!!)
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

        if(arrayData.size == arrayShow.size){
            arrayShow.clear()
            //return find
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
        Toast.makeText(this,newNumber.toString(),Toast.LENGTH_SHORT).show()
        Log.e("random", "$newNumber ")

        if(!isItemRepeat(newNumber)) {
            mViewBinding.btnTry.show()

            randomNumber = newNumber

            itemCard?.let {
                mViewBinding.cardMaterial.show()
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
            0 -> {
                mViewBinding.imgCard.show()
                mViewBinding.imgCard.loadImage(item.image)
            }
            1 -> {
                mViewBinding.imgCard.hide()
                mViewModel.startVibrate()
            }
            2 -> {
                mViewBinding.imgCard.hide()
                item.sound?.let {
                    mViewModel.playSound(it)
                }
            }
        }
    }

    private fun changeTheme(item: CardResult){
        when(item.tag){
            "sport" -> {
                mViewBinding.cardMaterial.backgroundTintList = getColorStateList(android.R.color.holo_blue_light)
            }
            "art" -> {
                mViewBinding.cardMaterial.backgroundTintList = getColorStateList(android.R.color.holo_red_light)
            }
            "fun" -> {
                mViewBinding.cardMaterial.backgroundTintList = getColorStateList(android.R.color.holo_green_dark)
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
                mViewBinding.btnTry.hide()
                mViewModel.stopSound()
                showCard()
            }
        }
    }

    override fun onPause() {
        super.onPause()

        mViewModel.stopSound()
    }
}