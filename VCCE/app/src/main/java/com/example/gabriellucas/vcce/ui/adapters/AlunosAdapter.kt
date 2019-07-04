package com.example.gabriellucas.vcce.ui.adapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.gabriellucas.vcce.R
import com.example.gabriellucas.vcce.entities.Aluno
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_aluno.view.*

class AlunosAdapter(var alunos: List<Aluno>) :
    RecyclerView.Adapter<AlunosAdapter.AlunoViewHolder>() {

    override fun getItemCount() = alunos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AlunoViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_aluno, parent, false)
        )

    override fun onBindViewHolder(holder: AlunoViewHolder, position: Int) {
        holder.fillUI(alunos[position])
    }

    inner class AlunoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun fillUI(aluno: Aluno) {
            itemView.tvNome.text = aluno.nome
            itemView.tvCurso.text = aluno.curso
            itemView.tvAno.text = aluno.ano
            Picasso
                .get()
                .load("http://192.168.0.102:8000/api/get/aluno/foto/"+aluno.foto)
                .into(itemView.imgFoto)

            val bundle = Bundle()
            bundle.putSerializable("aluno", aluno)
            itemView.setOnClickListener (
                Navigation.createNavigateOnClickListener(R.id.toAlunoDetalhes, bundle)
            )
        }
    }
}