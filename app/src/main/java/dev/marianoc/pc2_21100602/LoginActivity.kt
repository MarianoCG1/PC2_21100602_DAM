package dev.marianoc.pc2_21100602

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val etDni: EditText = findViewById(R.id.etDni)
        val etClave: EditText = findViewById(R.id.etClave)
        val btnLogin: Button = findViewById(R.id.btnIngresar)
        val btnRegistrar: Button = findViewById(R.id.btnRegistrar)
        var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()



        btnRegistrar.setOnClickListener()
        {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        btnLogin.setOnClickListener {
            val dni = etDni.text.toString().trim()
            val clave = etClave.text.toString().trim()

            if (dni.isEmpty() || clave.isEmpty()) {
                Snackbar.make(findViewById(android.R.id.content), "Completa todos los campos", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            firestore.collection("pc2_moviles")
                .whereEqualTo("DNI", dni)
                .whereEqualTo("Clave", clave)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        Snackbar.make(findViewById(android.R.id.content), "ACCESO PERMITIDO", Snackbar.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "EL USUARIO Y/O CLAVE NO EXISTE EN EL SISTEMA", Snackbar.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Snackbar.make(findViewById(android.R.id.content), "Error al iniciar sesión", Snackbar.LENGTH_SHORT).show()
                }
        }



    }


}