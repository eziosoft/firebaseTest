package com.example.firebasetest.utils

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.Constraints
import androidx.fragment.app.Fragment
import com.example.firebasetest.R

fun Fragment.showToast( message: String) {
    Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showAlert(alertType: AlertType, title: Int, description: Int, onDismissed: () -> Unit) {
    Dialog(requireContext(), android.R.style.Theme_Material_Dialog)
        .apply {
            setContentView(R.layout.dialog_info)

            window?.apply {
                setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
                setLayout(Constraints.LayoutParams.MATCH_PARENT, Constraints.LayoutParams.WRAP_CONTENT)
                setBackgroundDrawableResource(android.R.color.transparent)
            }

            findViewById<ImageView>(R.id.alertTypeIcon).setImageResource(
                when (alertType) {
                    AlertType.ERROR -> R.drawable.ic_error
                    AlertType.WARNING -> R.drawable.ic_warning
                    AlertType.INFORMATION -> R.drawable.ic_information
                    AlertType.SUCCESS -> R.drawable.ic_success
                }
            )

            findViewById<TextView>(R.id.alertTitle).text = requireContext().getText(title)
            findViewById<TextView>(R.id.alertDescription).text = requireContext().getText(description)
            findViewById<ImageView>(R.id.alertCloseIcon).setOnClickListener { dismiss() }

            setOnDismissListener { onDismissed() }
        }
        .show()

}


fun Fragment.showAlert(alertType: AlertType, title: String, description: String, onDismissed: () -> Unit) {
    Dialog(requireContext(), android.R.style.Theme_Material_Dialog)
        .apply {
            setContentView(R.layout.dialog_info)

            window?.apply {
                setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
                setLayout(Constraints.LayoutParams.MATCH_PARENT, Constraints.LayoutParams.WRAP_CONTENT)
                setBackgroundDrawableResource(android.R.color.transparent)
            }

            findViewById<ImageView>(R.id.alertTypeIcon).setImageResource(
                when (alertType) {
                    AlertType.ERROR -> R.drawable.ic_error
                    AlertType.WARNING -> R.drawable.ic_warning
                    AlertType.INFORMATION -> R.drawable.ic_information
                    AlertType.SUCCESS -> R.drawable.ic_success
                }
            )

            findViewById<TextView>(R.id.alertTitle).text = title
            findViewById<TextView>(R.id.alertDescription).text = description
            findViewById<ImageView>(R.id.alertCloseIcon).setOnClickListener { dismiss() }

            setOnDismissListener { onDismissed() }
        }
        .show()

}

enum class AlertType {
    ERROR,
    INFORMATION,
    WARNING,
    SUCCESS
}
