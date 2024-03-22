package com.example.crudrealtimeadmin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crudrealtimeadmin.databinding.ActivityUploadBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UploadActivity : AppCompatActivity() {

    private lateinit var binding:ActivityUploadBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            val ownerName = binding.uploadOwnerName.text.toString()
            val vehicleBrand = binding.uploadVehicleBrand.text.toString()
            val vehicleRTO = binding.uploadVehicleRTO.text.toString()
            val vehicleNumber = binding.uploadVehicleNumber.text.toString()

            databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")
            val vehicleData = VehicleData(ownerName, vehicleBrand, vehicleRTO, vehicleNumber)
            databaseReference.child(vehicleNumber).setValue(vehicleData).addOnCanceledListener {
                binding.uploadOwnerName.text.clear()
                binding.uploadVehicleBrand.text.clear()
                binding.uploadVehicleRTO.text.clear()
                binding.uploadVehicleNumber.text.clear()
                startActivity(intent)
            }.addOnFailureListener {
                Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
            }
            showToastAndRedirect("Updated", 750)
        }

        binding.buttonBack.setOnClickListener {
            val intent = Intent(this@UploadActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun showToastAndRedirect(message: String, second:Long) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        Handler().postDelayed({
            startActivity(Intent(this@UploadActivity, MainActivity::class.java))
            finish()
        }, second) // Delay of 1.5 seconds
    }
}