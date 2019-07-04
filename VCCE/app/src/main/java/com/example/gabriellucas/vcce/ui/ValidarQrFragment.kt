package com.example.gabriellucas.vcce.ui


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.gabriellucas.vcce.R
import com.example.gabriellucas.vcce.entities.Aluno
import com.example.gabriellucas.vcce.entities.AlunosResult
import com.example.gabriellucas.vcce.services.ECCEService
import com.google.gson.GsonBuilder
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.fragment_valida_qrcode.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ValidarQrFragment : Fragment() {

    lateinit var service: ECCEService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_valida_qrcode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRetrofit()

        btLer.setOnClickListener {
            IntentIntegrator.forSupportFragment(this).initiateScan()
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            if(result.contents != null) {
                Toast.makeText(context, "SCAN: " + result.contents, Toast.LENGTH_LONG).show()
                service.getAluno(result.contents).enqueue(object : Callback<Aluno> {
                    override fun onFailure(call: Call<Aluno>, t: Throwable) {
                        Toast.makeText(context, "CARTEIRINHA NÃO ENCONTRADA!", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<Aluno>, response: Response<Aluno>) {
                        val aluno = response.body()
                        if (aluno != null) {
                            val bundle = Bundle()
                            bundle.putSerializable("aluno", aluno)
                            findNavController().navigate(R.id.toAlunoDetalhes_validar, bundle)
                        }
                    }
                })
            } else {
                Toast.makeText(context, "SCAN cancelado!", Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}