package com.example.gabriellucas.vcce.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.gabriellucas.vcce.R
import kotlinx.android.synthetic.main.activity_alunos.*

class ECCEActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alunos)

        navView.setupWithNavController(
            Navigation.findNavController(this, R.id.fragmentContent)
        )
        navView.setOnNavigationItemSelectedListener {
            NavigationUI.onNavDestinationSelected(it,
                Navigation.findNavController(this, R.id.fragmentContent))
        }
    }
}
