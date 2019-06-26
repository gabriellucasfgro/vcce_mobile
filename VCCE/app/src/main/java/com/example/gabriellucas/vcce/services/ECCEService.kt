package com.example.gabriellucas.vcce.services

import com.example.gabriellucas.vcce.entities.AlunosResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ECCEService {
    @Headers("Accept: application/json")
    @GET("/api/get/alunos")
    fun getAlunos(): Call<AlunosResult>

    @Headers("Accept: application/json")
    @GET("/api/get/alunos/search/{pesquisa}")
    fun searchAlunos(
        @Path("pesquisa")
        pesquisa: String
    ): Call<AlunosResult>
}