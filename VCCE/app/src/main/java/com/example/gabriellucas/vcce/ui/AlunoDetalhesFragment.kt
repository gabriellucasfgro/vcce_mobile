package com.example.gabriellucas.vcce.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.gabriellucas.vcce.R
import com.example.gabriellucas.vcce.entities.Aluno
import com.example.gabriellucas.vcce.entities.AlunosResult
import com.example.gabriellucas.vcce.entities.Carteirinha
import com.example.gabriellucas.vcce.services.ECCEService
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_aluno_detalhes.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class AlunoDetalhesFragment : Fragment() {

    lateinit var service: ECCEService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_aluno_detalhes, container, false)
    }

    private fun configureRetrofit() {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.20.23.105:8000")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        service = retrofit.create<ECCEService>(ECCEService::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val aluno = arguments?.getSerializable("aluno") as Aluno

        configureRetrofit()

        service.getCarteirinha(aluno.matricula).enqueue(object : Callback<Carteirinha> {
            override fun onFailure(call: Call<Carteirinha>, t: Throwable) {
                Log.e("ERRO_getCarteirinha", t.message, t)
            }

            override fun onResponse(call: Call<Carteirinha>, response: Response<Carteirinha>) {
                val carteirinha = response.body() as Carteirinha
                view.txtValidade.setText(carteirinha.validade)

                val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")
                } else {
                    TODO("VERSION.SDK_INT < O")
                }
                val validade = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDate.parse(carteirinha.validade, formatter)
                } else {
                    TODO("VERSION.SDK_INT < O")
                }
                if(validade.isAfter(LocalDate.now())) {
                    view.imgValidade.setImageResource(R.drawable.ic_check_circle_black_24dp)
                    Toast.makeText(context, "CARTEIRINHA VÁLIDA!", Toast.LENGTH_LONG).show()
                } else {
                    view.imgValidade.setImageResource(R.drawable.ic_cancel_black_24dp)
                    Toast.makeText(context, "CARTEIRINHA INVÁLIDA!", Toast.LENGTH_LONG).show()
                }
            }

        })

        Picasso
            .get()
            .load("http://10.20.23.105:8000/api/get/aluno/foto/"+aluno.foto)
            .into(view.imgDetalhe)

        view.txtMatricula.setText(aluno.matricula)
        view.txtNome.setText(aluno.nome)
        view.txtAno.setText(aluno.ano)
        view.txtCurso.setText(aluno.curso)
        view.txtCampus.setText(aluno.campus)
        view.txtModalidade.setText(aluno.modalidade)
        view.txtCpf.setText(aluno.cpf)
        view.txtRg.setText(aluno.rg)
        view.txtNascimento.setText(aluno.nascimento)
        view.txtNaturalidade.setText(aluno.naturalidade)

    }

}
