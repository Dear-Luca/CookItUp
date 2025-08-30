package com.example.cookitup.utils

/**
 * Utility object for safe type conversions that prevent crashes
 */
object SafeConversions {

    /**
     * Safely converts String to Long, returning a default value if conversion fails
     */
    fun String?.toLongSafe(defaultValue: Long = 0L): Long {
        return try {
            this?.toLong() ?: defaultValue
        } catch (e: NumberFormatException) {
            defaultValue
        }
    }

    /**
     * Safely converts String to Double, returning a default value if conversion fails
     */
    fun String?.toDoubleSafe(defaultValue: Double = 0.0): Double {
        return try {
            this?.toDouble() ?: defaultValue
        } catch (e: NumberFormatException) {
            defaultValue
        }
    }

    /**
     * Safely converts String to Int, returning a default value if conversion fails
     */
    fun String?.toIntSafe(defaultValue: Int = 0): Int {
        return try {
            this?.toInt() ?: defaultValue
        } catch (e: NumberFormatException) {
            defaultValue
        }
    }
}
