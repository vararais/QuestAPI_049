package com.example.questapi_049.apiservice

import com.example.questapi_049.modeldata.DataSiswa
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ServiceApiSiswa {
    @GET(value = "bacaTeman.php")
    suspend fun getSiswa(): List<DataSiswa>

    @POST(value = "insertTM.php")
    suspend fun postSiswa(@Body dataSiswa: DataSiswa): retrofit2.Response<Void>

    @GET("baca1Teman.php")
    suspend fun getSatuSiswa(@Query("id") id: Int): DataSiswa


}