package com.sharif.tech_challenge.ui.base

import com.sharif.tech_challenge.data.api.ApiHelper
import dagger.Component
import javax.inject.Inject

@Component
abstract class BaseRepository {

    @Inject
    lateinit var apiHelper: ApiHelper
}