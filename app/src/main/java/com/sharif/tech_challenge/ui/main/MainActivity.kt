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
import com.sharif.tech_challenge.ui.base.BaseActivity
import com.sharif.tech_challenge.utils.createRandom
import com.sharif.tech_challenge.utils.extensions.hide
import com.sharif.tech_challenge.utils.extensions.loadImage
import com.sharif.tech_challenge.utils.extensions.show
import com.sharif.tech_challenge.utils.networkHelper.ResultNet
import com.sharif.tech_challenge.utils.playMusic
import com.sharif.tech_challenge.utils.vibrate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel,ActivityMainBinding>(), View.OnClickListener {

    private var loading : Boolean = false
        set(value) {
            showLoading(value)
            field = value
        }

    companion object{
        val arrayData = arrayListOf<CardResult>()
        val arrayShow = arrayListOf<Int>()
        var randomNumber : Int = -1
    }

    var media:MediaPlayer ?= null

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
//        var i = playMusic("")
//        Handler(Looper.getMainLooper()).postDelayed({
//            i.stop()
//        },2000
//        )
        arrayData.addAll(data.cards)
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
      //  mViewBinding.btnTry.setOnClickListener(this)
        findViewById<Button>(R.id.btnTry).setOnClickListener(this)
    }

    private fun showCard(){
        media?.let {
            it.stop()
        }

        var newNumber = createRandom()
        Toast.makeText(this,newNumber.toString(),Toast.LENGTH_SHORT).show()
        Log.e("random", "$newNumber ppppp")
      //  if(!isFindNumberRandom(newNumber)) {
           // arrayRandomNumber.add(newNumber)

        randomNumber = newNumber
        var item = getCard(randomNumber)
        findViewById<CardView>(R.id.cardMaterial).show()
        findViewById<TextView>(R.id.tvTitle).text = item?.title
        findViewById<TextView>(R.id.tvDesc).text = item?.description

        item?.let {
            checkCard(it)
            checkTheme(it)
        }

//        } else {
//            showCard()
//        }
    }

    private fun isRepeat(numberRandom:Int):Boolean {
       if(isMaxSize())
           return false
       else {
           arrayShow.forEach {
               if (it == numberRandom)
                   return true
           }
           return false
       }
    }

    private fun isMaxSize() : Boolean {
        if(arrayShow.size == arrayData.size) {
            arrayShow.clear()
            return true
        }
       return false
    }

    //endregion

    //region card
    private fun getCard(randomCode:Int): CardResult? {
        var itemCard:CardResult ?= null
        arrayData.forEach {
            if(it.code == randomCode) {
               return it
            }
        }
        return itemCard
    }

    private fun checkCard(item:CardResult) {
        when(item.code){
            0 -> {
                findViewById<ImageView>(R.id.imgCard).show()
                findViewById<ImageView>(R.id.imgCard).loadImage(item.image)
            }
            1 -> {
                findViewById<ImageView>(R.id.imgCard).hide()
                vibrate(this)
            }
            2 -> {
                findViewById<ImageView>(R.id.imgCard).hide()
                item.sound?.let {
                    media = playMusic(it)
                }
            }
        }
    }

    private fun checkTheme(item: CardResult){
        when(item.tag){
            "sport" -> {
                findViewById<CardView>(R.id.cardMaterial).backgroundTintList = getColorStateList(android.R.color.holo_blue_light)
            }
            "art" -> {
                findViewById<CardView>(R.id.cardMaterial).backgroundTintList = getColorStateList(android.R.color.holo_red_light)
            }
            "fun" -> {
                findViewById<CardView>(R.id.cardMaterial).backgroundTintList = getColorStateList(android.R.color.holo_green_dark)
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
                showCard()
            }
        }
    }
}