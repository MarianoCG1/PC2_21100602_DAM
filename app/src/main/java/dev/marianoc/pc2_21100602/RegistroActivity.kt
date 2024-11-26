package dev.marianoc.pc2_21100602

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnRegistro: Button = findViewById(R.id.btnRegistro)
        val etDniRegistro: EditText = findViewById(R.id.etDniRegistro)
        val etNombre: EditText = findViewById(R.id.etNombre)
        val etClaveRegistro: EditText = findViewById(R.id.etClaveRegistro)
        val etClaveRegistro2: EditText = findViewById(R.id.etClaveRegistro2)
        var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

        btnRegistro.setOnClickListener {
            val dni = etDniRegistro.text.toString().trim()
            val nombre = etNombre.text.toString().trim()
            val clave = etClaveRegistro.text.toString().trim()
            val clave2 = etClaveRegistro2.text.toString().trim()


            if (dni.isEmpty() || dni.length != 8 || !dni.matches("\\d+".toRegex())) {
                Snackbar.make(findViewById(android.R.id.content), "El DNI debe tener 8 dígitos numéricos", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (nombre.isEmpty() || nombre.length > 50 || !nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$".toRegex())) {
                Snackbar.make(findViewById(android.R.id.content), "El nombre debe tener máximo 50 caracteres y solo letras", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (clave.isEmpty() || clave.length < 4) {
                Snackbar.make(findViewById(android.R.id.content), "La clave debe tener al menos 4 caracteres", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (clave != clave2) {
                Snackbar.make(findViewById(android.R.id.content), "Las claves no coinciden", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val usuario = hashMapOf(
                "DNI" to dni,
                "Nombre" to nombre,
                "Clave" to clave
            )

            firestore.collection("pc2_moviles")
                .add(usuario)
                .addOnSuccessListener {
                    Snackbar.make(findViewById(android.R.id.content), "Usuario registrado correctamente", Snackbar.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    Snackbar.make(findViewById(android.R.id.content), "Error al registrar usuario", Snackbar.LENGTH_SHORT).show()
                }
        }
        }
    }











