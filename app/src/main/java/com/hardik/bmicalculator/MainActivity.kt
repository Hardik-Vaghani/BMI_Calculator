package com.hardik.bmicalculator

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import com.hardik.bmicalculator.databinding.ActivityMainBinding
import kotlin.math.log
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.simpleName
    lateinit var binding: ActivityMainBinding
    var age by Delegates.notNull<Int>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Making the activity fullscreen
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Setting a background color to the activity
        window.decorView.setBackgroundColor(resources.getColor(R.color.colorRed))

        // Alternatively, you can use the color directly without defining it in resources
        // window.decorView.setBackgroundColor(Color.parseColor("#FF0000"))


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.seekBarAge.min = 0
        binding.seekBarAge.max = 150
        age = 16
        binding.heightFeetPicker.minValue = 1
        binding.heightFeetPicker.maxValue = 15
        binding.heightFeetPicker.value = 5
        binding.heightInchPicker.minValue = 0
        binding.heightInchPicker.maxValue = 11
        binding.heightInchPicker.value = 8
        binding.weightKgPicker.minValue = 2
        binding.weightKgPicker.maxValue = 150
        binding.weightKgPicker.value = 60
        binding.weightGmPicker.minValue = 0
        binding.weightGmPicker.maxValue = 999
        binding.weightGmPicker.value = 200

        calculateBmi()

        binding.seekBarAge.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.tvAge.text = "${resources.getString(R.string.age)}$p1"
                age = p1
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
        binding.heightFeetPicker.setOnValueChangedListener { numberPicker, i, i2 ->
            calculateBmi()
        }
        binding.heightInchPicker.setOnValueChangedListener { numberPicker, i, i2 ->
            calculateBmi()
        }
        binding.weightKgPicker.setOnValueChangedListener { numberPicker, i, i2 ->
            calculateBmi()
        }
        binding.weightGmPicker.setOnValueChangedListener { numberPicker, i, i2 ->
            calculateBmi()
        }
    }

    private fun calculateBmi() {
        val heightFeet = binding.heightFeetPicker.value
        val heightInch = binding.heightInchPicker.value
        val totalHeightInch = (heightFeet * 12) + heightInch
        val totalHeightCM = totalHeightInch * 2.54
        val totalMiter = totalHeightCM / 100

        val weightKg = binding.weightKgPicker.value
        val weightGm = binding.weightGmPicker.value

        val totalWeightKg = weightKg.toDouble() + (weightGm.toDouble() / 1000)
//        Log.d(TAG, "calculateBmi: $totalWeightKg")

//        val bmi = totalWeightKg / (totalMiter * totalMiter)

//        binding.tvResult.text = String.format("Your BMI is: %.2f", bmi)
//        binding.tvHealthy.text = String.format("Considered: %s", healthMessage(bmi))

        try {
            val bmi = calculateBMI(3, "female", totalMiter, totalWeightKg)
            binding.tvResult.text = String.format("Your BMI is: %.2f", bmi)
            binding.tvHealthy.text = String.format("Considered: %s", healthMessage(bmi))
            println("Adjusted BMI: $bmi")
        } catch (e: IllegalArgumentException) {
            println("Error: ${e.message}")
        }

    }

    private fun healthMessage(bmi: Double): Any? {
        if (bmi < 18.5) {
            return if (bmi <= 17) {
                binding.clMain.setBackgroundColor(resources.getColor(R.color.colorYellow))
                resources.getString(R.string.srtUnderWt)
            } else {
                binding.clMain.setBackgroundColor(resources.getColor(R.color.colorBlue))
                resources.getString(R.string.srtUnderWtCare)
            }
        }
        if (bmi in 18.5..24.9) {
            binding.clMain.setBackgroundColor(resources.getColor(R.color.colorGreen))
            return resources.getString(R.string.srtHealthy)
        }

        if (bmi > 25) {
            return if (bmi >= 26) {
                binding.clMain.setBackgroundColor(resources.getColor(R.color.colorRed))
                resources.getString(R.string.srtOverWt)
            } else {
                binding.clMain.setBackgroundColor(resources.getColor(R.color.colorBlue))
                resources.getString(R.string.srtOverWtCare)
            }
        }
        binding.clMain.setBackgroundColor(resources.getColor(R.color.colorRed))
        return resources.getString(R.string.srtObesityWt)

    }

    private fun calculateBMI(age: Int, gender: String, height: Double, weight: Double): Double {
        if (age <= 0 || height <= 0.0 || weight <= 0.0) {
            throw IllegalArgumentException("Invalid input values. Age, height, and weight must be positive.")
        }

        val bmi: Double = weight / (height * height)

        // Adjust BMI based on age and gender (example adjustments, you may need to refine these based on actual guidelines)
        val adjustedBMI = when {
            age < 5 -> bmi + 2.0 // Adjust for very young children
            age in 5..12 -> bmi + 1.0 // Adjust for children
            age in 13..17 -> bmi + 0.5 // Adjust for teenagers
            age in 18..24 -> bmi - 0.5 // Adjust for young adults
            age >= 65 -> bmi + 1.0 // Adjust for older adults
            else -> bmi // Default for adults
        }

        // Further adjustments based on gender (example adjustments, you may need to refine these based on actual guidelines)
        val finalBMI = when {
            gender.equals("female", ignoreCase = true) -> adjustedBMI - 0.5
            gender.equals("male", ignoreCase = true) -> adjustedBMI
            else -> adjustedBMI
        }

        return finalBMI
    }


    private fun healthMessage1(bmi: Double): Any? {
        return when {
            bmi < 18.5 -> {
                if (bmi <= 17) {
                    binding.clMain.setBackgroundColor(resources.getColor(R.color.colorYellow))
                    resources.getString(R.string.srtUnderWt)
                } else {
                    binding.clMain.setBackgroundColor(resources.getColor(R.color.colorBlue))
                    resources.getString(R.string.srtUnderWtCare)
                }
            }

            bmi in 18.5..24.9 -> {
                binding.clMain.setBackgroundColor(resources.getColor(R.color.colorGreen))
                return resources.getString(R.string.srtHealthy)
            }

            else -> {
                if (bmi >= 26) {
                    binding.clMain.setBackgroundColor(resources.getColor(R.color.colorRed))
                    resources.getString(R.string.srtOverWt)
                } else {
                    binding.clMain.setBackgroundColor(resources.getColor(R.color.colorBlue))
                    resources.getString(R.string.srtOverWtCare)
                }
            }
        }
    }

}