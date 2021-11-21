package com.sharif.tech_challenge.utils.url

import javax.inject.Inject

class BaseUrl @Inject constructor(){
    fun getBaseUrl():String = "https://raw.githubusercontent.com/AmirrezaRotamian/Tech-Challenge/"
}
