package com.example.gabriellucas.vcce.ui


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.gabriellucas.vcce.R
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.fragment_valida_qrcode.*


class ValidarQrFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_valida_qrcode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btLer.setOnClickListener {
            IntentIntegrator.forSupportFragment(this).initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.e("ALO", "ALO")
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            if(result.contents != null) {
                Toast.makeText(context, "SCAN: " + result.contents, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "SCAN cancelado!", Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}