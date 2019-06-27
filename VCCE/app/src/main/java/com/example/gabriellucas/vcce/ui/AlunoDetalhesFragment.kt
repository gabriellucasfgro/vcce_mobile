package com.example.gabriellucas.vcce.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gabriellucas.vcce.R
import com.example.gabriellucas.vcce.entities.Aluno
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_aluno_detalhes.view.*


class AlunoDetalhesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_aluno_detalhes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val aluno = arguments?.getSerializable("aluno") as Aluno

        Picasso
            .get()
            .load("http://10.0.2.2:8000/api/get/aluno/foto/"+aluno.foto)
            .into(view.imgDetalhe)

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
