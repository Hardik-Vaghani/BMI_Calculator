package com.hardik.bmicalculator

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.hardik.bmicalculator.databinding.ActivitySecondBinding
import kotlin.properties.Delegates

class SecondActivity : AppCompatActivity() {
    lateinit var binding: ActivitySecondBinding
    var processCM = 0
    var totalHeightCmToMeter = 0.0
    var processInch = 0
    var totalHeightInchToMeter = 0.0
    var processKG = 0
    var totalWeightKG = 0.0
    var processPound = 0
    var totalWeightPoundToKG = 0.0
    var processAge = 0
    private var gender = "Male"

    @SuppressLint("SetTextI18n", "SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //height
        binding.seekBarCM.max = 381
        binding.seekBarCM.min = 0
        binding.seekBarCM.progress = 0
        binding.seekBarInch.max = 150
        binding.seekBarInch.min = 0
        binding.seekBarInch.progress = 0
        binding.tvHeight.text = "${resources.getString(R.string.height)} 0 CM"

        //weight
        binding.seekBarKG.max = 650
        binding.seekBarKG.min = 0
        binding.seekBarKG.progress = 0
        binding.seekBarPound.max = 1433
        binding.seekBarPound.min = 0
        binding.seekBarPound.progress = 0
        binding.tvWeight.text = "${resources.getString(R.string.height)} 0 KG"

        //Age
        binding.seekBarAge.min = 0
        binding.seekBarAge.max = 120
        binding.seekBarAge.progress = 0
        binding.tvAge.text = "${resources.getString(R.string.age)} 0 Years"

        binding.tvGender.text = "${resources.getString(R.string.gender)} Male"

        binding.ivAddHeight.setOnClickListener {
            if (binding.seekBarCM.isVisible)
                binding.seekBarCM.progress++
            if (binding.seekBarInch.isVisible)
                binding.seekBarInch.progress++
        }
        binding.ivMinusHeight.setOnClickListener {
            if (binding.seekBarCM.isVisible)
                binding.seekBarCM.progress--
            if (binding.seekBarInch.isVisible)
                binding.seekBarInch.progress--
        }
        binding.ivAddWeight.setOnClickListener {
            if (binding.seekBarKG.isVisible)
                binding.seekBarKG.progress++
            if (binding.seekBarPound.isVisible)
                binding.seekBarPound.progress++
        }
        binding.ivMinusWeight.setOnClickListener {
            if (binding.seekBarKG.isVisible)
                binding.seekBarKG.progress--
            if (binding.seekBarPound.isVisible)
                binding.seekBarPound.progress--
        }

        binding.llMale.setOnClickListener {
            binding.llMale.background =
                ContextCompat.getDrawable(it.context, R.drawable.background_box)
            binding.llFemale.background = null
            gender = "Male"
        }
        binding.llFemale.setOnClickListener {
            binding.llMale.background = null
            binding.llFemale.background =
                ContextCompat.getDrawable(it.context, R.drawable.background_box)
            gender = "Female"
        }
        binding.ivAddAge.setOnClickListener {
            binding.seekBarAge.progress++
        }
        binding.ivMinusAge.setOnClickListener {
            binding.seekBarAge.progress--
        }
        binding.tvHeightCM.setOnClickListener {
            binding.tvHeightCM.background =
                ContextCompat.getDrawable(it.context, R.drawable.background_box)
            binding.tvHeightInch.background = null
            binding.seekBarCM.visibility = View.VISIBLE
            binding.seekBarInch.visibility = View.GONE
            binding.tvHeight.text = "${resources.getString(R.string.height)} $processCM CM"
        }
        binding.tvHeightInch.setOnClickListener {
            binding.tvHeightInch.background =
                ContextCompat.getDrawable(it.context, R.drawable.background_box)
            binding.tvHeightCM.background = null
            binding.seekBarInch.visibility = View.VISIBLE
            binding.seekBarCM.visibility = View.GONE
            binding.tvHeight.text = "${resources.getString(R.string.height)} $processInch Inch"
        }

        binding.tvWeightKg.setOnClickListener {
            binding.tvWeightKg.background =
                ContextCompat.getDrawable(it.context, R.drawable.background_box)
            binding.tvWeightPound.background = null
            binding.seekBarKG.visibility = View.VISIBLE
            binding.seekBarPound.visibility = View.GONE
            binding.tvWeight.text = "${resources.getString(R.string.weight)} $processKG KG"
        }
        binding.tvWeightPound.setOnClickListener {
            binding.tvWeightPound.background =
                ContextCompat.getDrawable(it.context, R.drawable.background_box)
            binding.tvWeightKg.background = null
            binding.seekBarPound.visibility = View.VISIBLE
            binding.seekBarKG.visibility = View.GONE
            binding.tvWeight.text = "${resources.getString(R.string.weight)} $processPound Pound"
        }

        binding.seekBarCM.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                processCM = p1
                totalHeightCmToMeter = cmToMeter(p1.toDouble())
                binding.tvHeight.text = "${resources.getString(R.string.height)} $p1 CM"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
        binding.seekBarInch.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                processInch = p1
                totalHeightInchToMeter = inchToMeter(p1.toDouble())
                binding.tvHeight.text = "${resources.getString(R.string.height)} $p1 Inch"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        binding.seekBarKG.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                processKG = p1
                totalWeightKG = p1.toDouble()
                binding.tvWeight.text = "${resources.getString(R.string.weight)} $p1 KG"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
        binding.seekBarPound.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                processPound = p1
                totalWeightPoundToKG = poundToKg(p1.toDouble())
                binding.tvWeight.text = "${resources.getString(R.string.weight)} $p1 Pound"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        binding.seekBarAge.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                processAge = p1
                binding.tvAge.text = "${resources.getString(R.string.age)} $p1 Years"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        binding.tvCalculate.setOnClickListener {
            try {
                val bmi =
                    if (binding.seekBarCM.isVisible && binding.seekBarKG.isVisible) {
                        calculateBMI(
                            binding.seekBarAge.progress,
                            gender,
                            totalHeightCmToMeter,
                            totalWeightKG
                        )
                    } else if (binding.seekBarCM.isVisible && binding.seekBarPound.isVisible) {
                        calculateBMI(
                            binding.seekBarAge.progress,
                            gender,
                            totalHeightCmToMeter,
                            totalWeightPoundToKG
                        )
                    } else if (binding.seekBarInch.isVisible && binding.seekBarKG.isVisible) {
                        calculateBMI(
                            binding.seekBarAge.progress,
                            gender,
                            totalHeightInchToMeter,
                            totalWeightKG
                        )
                    } else {
                        calculateBMI(
                            binding.seekBarAge.progress,
                            gender,
                            totalHeightInchToMeter,
                            totalWeightPoundToKG
                        )
                    }
                binding.tvResult.text = String.format("Your BMI is: %.2f", bmi)
                binding.tvHealthy.text = String.format("Considered: %s", healthMessage(bmi))
                println("Adjusted BMI: $bmi")
            } catch (e: IllegalArgumentException) {
                println("Error: ${e.message}")
            }
        }
    }

    // Function to convert centimeters to meters
    fun cmToMeter(centimeters: Double): Double {
        return centimeters / 100.0
    }

    // Function to convert inches to meters
    fun inchToMeter(inches: Double): Double {
        return inches * 0.0254
    }

    // Function to convert pounds to kilograms
    fun poundToKg(pounds: Double): Double {
        return pounds * 0.453592
        //return (pounds / 2.205)
    }

    // Function to convert centimeters to inches
    fun cmToInch(centimeters: Double): Double {
        return centimeters / 2.54
    }

    // Function to convert inches to centimeters
    fun inchToCm(inches: Double): Double {
        return inches * 2.54
    }

    // Function to convert kilograms to pounds
    fun kgToPound(kilograms: Double): Double {
        return (kilograms * 2.205)
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

    private fun healthMessage(bmi: Double): Any? {
        if (bmi < 18.5) {
            return if (bmi <= 17) {
                binding.scrollViewMain.setBackgroundColor(resources.getColor(R.color.colorYellow))
                resources.getString(R.string.srtUnderWt)
            } else {
                binding.scrollViewMain.setBackgroundColor(resources.getColor(R.color.colorBlue))
                resources.getString(R.string.srtUnderWtCare)
            }
        }
        if (bmi in 18.5..24.9) {
            binding.scrollViewMain.setBackgroundColor(resources.getColor(R.color.colorGreen))
            return resources.getString(R.string.srtHealthy)
        }

        if (bmi > 25) {
            return if (bmi >= 26) {
                binding.scrollViewMain.setBackgroundColor(resources.getColor(R.color.colorRed))
                resources.getString(R.string.srtOverWt)
            } else {
                binding.scrollViewMain.setBackgroundColor(resources.getColor(R.color.colorBlue))
                resources.getString(R.string.srtOverWtCare)
            }
        }
        binding.scrollViewMain.setBackgroundColor(resources.getColor(R.color.colorRed))
        return resources.getString(R.string.srtObesityWt)

    }
}