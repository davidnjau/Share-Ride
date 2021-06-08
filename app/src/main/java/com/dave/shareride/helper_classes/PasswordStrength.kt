package com.dave.shareride.helper_classes

import android.graphics.Color
import com.dave.shareride.R


enum class PasswordStrength(var msg: Int, var color: Int) {

    WEAK(R.string.weak, Color.parseColor("#61ad85")),
    MEDIUM(R.string.medium, Color.parseColor("#4d8a6a")),
    STRONG(R.string.strong, Color.parseColor("#3a674f")),
    VERY_STRONG(R.string.very_strong, Color.parseColor("#264535"));

    companion object {
        private const val MIN_LENGTH = 8
        private const val MAX_LENGTH = 15
        fun calculate(password: String): PasswordStrength {
            var score = 0
            // boolean indicating if password has an upper case
            var upper = false
            // boolean indicating if password has a lower case
            var lower = false
            // boolean indicating if password has at least one digit
            var digit = false
            // boolean indicating if password has a leat one special char
            var specialChar = false
            for (element in password) {
                val c = element
                if (!specialChar && !Character.isLetterOrDigit(c)) {
                    score++
                    specialChar = true
                } else {
                    if (!digit && Character.isDigit(c)) {
                        score++
                        digit = true
                    } else {
                        if (!upper || !lower) {
                            if (Character.isUpperCase(c)) {
                                upper = true
                            } else {
                                lower = true
                            }
                            if (upper && lower) {
                                score++
                            }
                        }
                    }
                }
            }
            val length = password.length
            if (length > MAX_LENGTH) {
                score++
            } else if (length < MIN_LENGTH) {
                score = 0
            }
            when (score) {
                0 -> return WEAK
                1 -> return MEDIUM
                2 -> return STRONG
                3 -> return VERY_STRONG
                else -> {
                }
            }
            return VERY_STRONG
        }
    }

}