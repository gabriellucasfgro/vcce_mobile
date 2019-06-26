package com.example.gabriellucas.vcce.entities

import java.io.Serializable

class Carteirinha(
    var aluno_matricula: String,
    var validade: String,
    var emissao: String
) : Serializable