package com.example.gabriellucas.vcce.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import com.example.gabriellucas.vcce.R
import com.example.gabriellucas.vcce.entities.Aluno
import com.example.gabriellucas.vcce.entities.AlunosResult
import com.example.gabriellucas.vcce.services.ECCEService
import com.example.gabriellucas.vcce.ui.adapters.AlunosAdapter
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_list_alunos.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ListAlunosFragment : Fragment() {

    lateinit var service: ECCEService
    lateinit var adapter: AlunosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_alunos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRetrofit()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchAlunos(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //searchAlunos(newText!!)
                return true
            }
        })

        loadAlunos()
    }

    private fun configureRetrofit() {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.102:8000")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        service = retrofit.create<ECCEService>(ECCEService::class.java)
    }

    private fun loadAlunos() {
        service.getAlunos().enqueue(object : Callback<AlunosResult> {
            override fun onFailure(call: Call<AlunosResult>, t: Throwable) {
                Log.e("ERRO_getAlunos", t.message, t)
            }

            override fun onResponse(call: Call<AlunosResult>, response: Response<AlunosResult>) {
                val alunos = response.body()?.alunos
                if (alunos != null)
                    loadRecyclerView(alunos)
            }

        })
    }

    private fun searchAlunos(search : String) {
        service.searchAlunos(search).enqueue(object : Callback<AlunosResult> {
            override fun onFailure(call: Call<AlunosResult>, t: Throwable) {
                Log.e("ERRO_searchAlunos", t.message, t)
            }

            override fun onResponse(call: Call<AlunosResult>, response: Response<AlunosResult>) {
                val alunos = response.body()?.alunos
                if (alunos != null)
                    loadRecyclerView(alunos)
            }

        })
    }

    private fun loadRecyclerView(alunos: List<Aluno>) {
        if (listAlunos != null) {
            adapter = AlunosAdapter(alunos)
            listAlunos.adapter = adapter
        }
    }
}
