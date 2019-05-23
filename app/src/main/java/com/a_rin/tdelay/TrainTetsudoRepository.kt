package com.a_rin.tdelay

import android.content.ContentValues.TAG
import android.util.Log
import com.a_rin.tdelay.client.TrainTetsudo
import com.a_rin.tdelay.model.Line
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.schedulers.Schedulers

class TrainTetsudoRepository {
    fun apiLineFetch(): Observable<List<Line>> {
        val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://rti-giken.jp")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
        val apiClient = retrofit.create(TrainTetsudo::class.java)
        return apiClient.delay()
            .subscribeOn(Schedulers.io())
    }
}