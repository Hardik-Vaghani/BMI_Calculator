package com.hardik.bmicalculator

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hardik.bmicalculator.databinding.ActivityThirdBinding
import com.hardik.bmicalculator.databinding.ItmeBottomSheetResultLayoutBinding

class ThirdActivity : AppCompatActivity() {
    val TAG = ThirdActivity::class.java.simpleName
    lateinit var binding: ActivityThirdBinding

    private var gender = "Male"
    var processCM = 30
    var totalHeightCmToMeter = 1.7
    var totalKG = 60
    var totalAge = 26

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //height
        binding.seekBarHeight.max = 381
        binding.seekBarHeight.min = 1
        binding.seekBarHeight.progress = 170
        binding.tvHeight.text = "170 CM"

        //weight
        binding.tvWeight.text = "$totalKG KG"

        //age
        binding.tvAge.text = "$totalAge Age"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
        binding.llMale.setOnClickListener {
            gender = "Male"
            binding.llMale.setBackgroundResource(R.drawable.background_box1)
            binding.ivMale.setImageResource(R.drawable.icon_male_white)
            binding.tvMale.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
            binding.llFemale.setBackgroundResource(R.drawable.background_box2)
            binding.ivFemale.setImageResource(R.drawable.icon_female_primary)
            binding.tvFemale.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorPrimary
                )
            )

        }
        binding.llFemale.setOnClickListener {
            gender = "Female"
            binding.llFemale.setBackgroundResource(R.drawable.background_box1)
            binding.ivFemale.setImageResource(R.drawable.icon_female_white)
            binding.tvFemale.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.white
                )
            )

            binding.llMale.setBackgroundResource(R.drawable.background_box2)
            binding.ivMale.setImageResource(R.drawable.icon_male_primary)
            binding.tvMale.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorPrimary
                )
            )

        }

        binding.ivHeightIncrease.setOnClickListener { binding.seekBarHeight.progress++ }
        binding.ivHeightDecrease.setOnClickListener { binding.seekBarHeight.progress-- }
        binding.seekBarHeight.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                processCM = p1
                totalHeightCmToMeter = cmToMeter(p1.toDouble())
                binding.tvHeight.text = "$p1 CM"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })

        binding.ivWeightIncrease.setOnClickListener {
            binding.tvWeight.text = "${if (totalKG < 150) ++totalKG else totalKG} KG"
        }
        binding.ivWeightDecrease.setOnClickListener {
            binding.tvWeight.text = "${if (totalKG > 2) --totalKG else totalKG} KG"
        }

        binding.ivAgeIncrease.setOnClickListener {
            binding.tvAge.text = "${if (totalAge < 120) ++totalAge else totalAge} Age"
        }
        binding.ivAgeDecrease.setOnClickListener {
            binding.tvAge.text = "${if (totalAge > 1) --totalAge else totalAge} Age"
        }

        binding.tvSubmit.setOnClickListener {
//            Log.d(TAG, "onResume: BMI: $totalAge, $gender, $totalHeightCmToMeter, $totalKG, $processCM ")
//            Log.d(TAG, "onResume: BMI: ${calculateBMI(totalAge, gender, totalHeightCmToMeter, totalKG.toDouble())}\nYour health result: ${healthMessage(calculateBMI(totalAge, gender, totalHeightCmToMeter, totalKG.toDouble()))}")
            onSubmitClick()
        }

    }

    fun cmToMeter(centimeters: Double): Double {
        return centimeters / 100.0
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

    private fun healthMessage(bmi: Double, view: View): Any? {
        if (bmi < 18.5) {
            return if (bmi <= 17) {
                view.setBackgroundColor(resources.getColor(R.color.colorYellow))
                resources.getString(R.string.srtUnderWt)
            } else {
                view.setBackgroundColor(resources.getColor(R.color.colorBlue))
                resources.getString(R.string.srtUnderWtCare)
            }
        }
        if (bmi in 18.5..24.9) {
            view.setBackgroundColor(resources.getColor(R.color.colorGreen))
            return resources.getString(R.string.srtHealthy)
        }

        if (bmi > 25) {
            return if (bmi >= 26) {
                view.setBackgroundColor(resources.getColor(R.color.colorRed))
                resources.getString(R.string.srtOverWt)
            } else {
                view.setBackgroundColor(resources.getColor(R.color.colorBlue))
                resources.getString(R.string.srtOverWtCare)
            }
        }
        view.setBackgroundColor(resources.getColor(R.color.colorRed))
        return resources.getString(R.string.srtObesityWt)

    }

    private fun onSubmitClick() {

        val dialogView = layoutInflater.inflate(R.layout.itme_bottom_sheet_result_layout, null)
        val bsBinding = ItmeBottomSheetResultLayoutBinding.bind(dialogView)
        val bmi = calculateBMI(totalAge,gender,totalHeightCmToMeter,totalKG.toDouble())
        bsBinding.tvResult.text = String.format("Your BMI is: %.2f", bmi)
        bsBinding.tvHealthy.text = String.format("Considered: %s", healthMessage(bmi,bsBinding.clResult))
        val dialog = BottomSheetDialog(this)
        dialog.window?.attributes?.also {
//            width = resources.displayMetrics.widthPixels - (48 * resources.displayMetrics.density).toInt() // Adjust width
//            width = WindowManager.LayoutParams.WRAP_CONTENT
//            height = WindowManager.LayoutParams.WRAP_CONTENT // Adjust height as needed
//            gravity = Gravity.CENTER // Adjust the gravity as needed

            // Set margins and padding
//            horizontalMargin = 16 * resources.displayMetrics.density
//            verticalMargin = 16 * resources.displayMetrics.density

            // Set padding
//            dialogView.setPadding(
//                24 * resources.displayMetrics.density.toInt(),
//                24 * resources.displayMetrics.density.toInt(),
//                24 * resources.displayMetrics.density.toInt(),
//                24 * resources.displayMetrics.density.toInt()
//            )
        }
        dialog.setContentView(bsBinding.root)
//        dialog.window?.setBackgroundDrawableResource(android.R.drawable.screen_background_light_transparent) // Set your background drawable here
        dialog.show()
    }
}