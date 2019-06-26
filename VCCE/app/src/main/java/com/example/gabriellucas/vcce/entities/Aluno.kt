package com.example.gabriellucas.vcce.entities

import java.io.Serializable
import java.util.*

data class Aluno(
    var matricula: String,
    var nome: String,
    var curso: String,
    var ano: String,
    var cpf: String,
    var rg: String,
    var nascimento: String,
    var modalidade: String,
    var campus: String,
    var naturalidade: String,
    var foto: String
) : Serializable