package com.example.note

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import BaseDatos.data.DB.BDLibro
import BaseDatos.data.entity.nombre
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GuardarActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_guardar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val t2 = findViewById<EditText>(R.id.t2) // Nombre
        val t3 = findViewById<EditText>(R.id.t3) // Asunto
        val t4 = findViewById<EditText>(R.id.t4) // Monto
        val b1 = findViewById<Button>(R.id.b1) // boton de guardar
        val b2 = findViewById<Button>(R.id.b2) // boton de salida

        // Instancia de la base de datos
        val db = BDLibro.getDatabase(this)
        val dao = db.enDao()

        // Variable para almacenar el último nombre guardado
        var ultimoNombreGuardado = ""
        // Cargar el último nombre guardado al iniciar la actividad
        lifecycleScope.launch {
            ultimoNombreGuardado = withContext(Dispatchers.IO) {
                dao.obtenerUltimoNombre() ?: ""
            }
            // Establecer el último nombre en el EditText si existe
            if (ultimoNombreGuardado.isNotEmpty()) {
                t2.setText(ultimoNombreGuardado)
            }
        }
        t2.setOnClickListener {
            if (t2.text.isNotEmpty()){
                t2.text.clear()
            }
        }

        b1.setOnClickListener {

            val nombreTexto = t2.text.toString()
            val asuntoTexto = t3.text.toString()
            val montoTexto = t4.text.toString()

            if (nombreTexto.isNotEmpty() && asuntoTexto.isNotEmpty() && montoTexto.isNotEmpty()) {

                val montoInt = montoTexto.toInt()

                val nuevoRegistro = nombre(
                    Nombre = nombreTexto,
                    asunto = asuntoTexto,
                    Monto = montoInt
                )

                // Guardar en Room
                lifecycleScope.launch {
                    dao.insert(nuevoRegistro)
                    // Actualizar el último nombre guardado
                    ultimoNombreGuardado = nombreTexto
                }

                Toast.makeText(this@GuardarActivity, "Registro guardado ", Toast.LENGTH_SHORT).show()

                // Limpiar
                //t2.text.clear()
                t3.text.clear()
                t4.text.clear()
            } else {
                Toast.makeText(this@GuardarActivity, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        b2.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("salir")
                .setMessage("¿seguro que quieres salir?")
                .setPositiveButton("Aceptar") { _, _ ->
                    startActivity(Intent(this, inicioActivity::class.java))
                    finish()  // sirve para finalisar
                }
                .setNegativeButton("cancelar", null)
                .show()
        }
    }
}