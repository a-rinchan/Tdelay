package com.a_rin.tdelay.client

import com.a_rin.tdelay.model.Line
import retrofit2.http.GET
import rx.Observable

interface TrainTetsudo {
    @GET("/fhc/api/train_tetsudo/delay.json")
    fun delay(): Observable<List<Line>>
}