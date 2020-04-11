package com.assignment.coolassignment.di.module

import com.assignment.coolassignment.network.FlickerApi
import com.assignment.coolassignment.network.ListApi
import com.assignment.coolassignment.utils.FLICKER_BASE_URL
import com.assignment.coolassignment.utils.LIST_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
object NetworkModule {

    @Provides
    @Reusable
    @JvmStatic
    internal fun getListPostApi(@Named("list") retrofit: Retrofit): ListApi{
        return retrofit.create(ListApi::class.java)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun getFlickerPostApi(@Named("flicker") retrofit: Retrofit): FlickerApi{
        return retrofit.create(FlickerApi::class.java)
    }

    @Provides
    @Reusable
    @Named("list")
    @JvmStatic
    internal fun provideListRetrofitInstance(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(LIST_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    @Provides
    @Reusable
    @JvmStatic
    @Named("flicker")
    internal fun provideFlickerRetrofitInstance(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(FLICKER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }
}