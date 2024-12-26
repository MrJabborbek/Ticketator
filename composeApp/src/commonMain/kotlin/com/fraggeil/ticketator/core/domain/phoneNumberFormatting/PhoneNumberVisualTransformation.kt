package com.fraggeil.ticketator.core.domain.phoneNumberFormatting

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.intl.Locale
import com.fraggeil.ticketator.core.domain.Platform
import com.fraggeil.ticketator.core.domain.PlatformType
import io.michaelrocks.libphonenumber.kotlin.PhoneNumberUtil
import io.michaelrocks.libphonenumber.kotlin.metadata.defaultMetadataLoader

const val WILD: Char = 'N'
const val WAIT: Char = ';'
const val PAUSE: Char = ','

class PhoneNumberVisualTransformation(
    phoneNumberUtil: PhoneNumberUtil,
    countryCode: String = Locale.current.region
) : VisualTransformation {

    private val phoneNumberFormatter = phoneNumberUtil.getAsYouTypeFormatter(countryCode)

    override fun filter(text: AnnotatedString): TransformedText {
        val transformation = reformat(text, text.length)

        return TransformedText(
            AnnotatedString(transformation.formatted.orEmpty()),
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return transformation.originalToTransformed[offset.coerceIn(transformation.originalToTransformed.indices)]
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return transformation.transformedToOriginal[offset.coerceIn(transformation.transformedToOriginal.indices)]
                }
            }
        )
    }

    private fun isNonSeparator(c: Char): Boolean {
        return (c in '0'..'9') || c == '*' || c == '#' || c == '+' || c == WILD || c == WAIT || c == PAUSE
    }

    private fun reformat(s: CharSequence, cursor: Int): Transformation {
        phoneNumberFormatter.clear()

        val curIndex = cursor - 1
        var formatted: String? = null
        var lastNonSeparator = 0.toChar()
        var hasCursor = false

        s.forEachIndexed { index, char ->
            if (isNonSeparator(char)) {
                if (lastNonSeparator.code != 0) {
                    formatted = getFormattedNumber(lastNonSeparator, hasCursor)
                    hasCursor = false
                }
                lastNonSeparator = char
            }
            if (index == curIndex) {
                hasCursor = true
            }
        }

        if (lastNonSeparator.code != 0) {
            formatted = getFormattedNumber(lastNonSeparator, hasCursor)
        }
        val originalToTransformed = mutableListOf<Int>()
        val transformedToOriginal = mutableListOf<Int>()
        var specialCharsCount = 0
        formatted?.forEachIndexed { index, char ->
            if (!isNonSeparator(char)) {
                specialCharsCount++
                transformedToOriginal.add(index - specialCharsCount)
            } else {
                originalToTransformed.add(index)
                transformedToOriginal.add(index - specialCharsCount)
            }
        }
        originalToTransformed.add(originalToTransformed.maxOrNull()?.plus(1) ?: 0)
        transformedToOriginal.add(transformedToOriginal.maxOrNull()?.plus(1) ?: 0)

        return Transformation(formatted, originalToTransformed, transformedToOriginal)
    }

    private fun getFormattedNumber(lastNonSeparator: Char, hasCursor: Boolean): String? {
        return if (hasCursor) {
            phoneNumberFormatter.inputDigitAndRememberPosition(lastNonSeparator)
        } else {
            phoneNumberFormatter.inputDigit(lastNonSeparator)
        }
    }

    private data class Transformation(
        val formatted: String?,
        val originalToTransformed: List<Int>,
        val transformedToOriginal: List<Int>
    )
}

fun String.formatPhoneNumber(isSecret: Boolean = false): String {
    try {
        if (Platform() == PlatformType.IOS){
            return this //TODO TODO TODO
        }

    val phoneNumberUtil: PhoneNumberUtil = PhoneNumberUtil.createInstance(defaultMetadataLoader())
    val region = try {
            Locale.current.region
            "UZ"
        } catch (e: Exception) {
            // as of compose 1.4.3, js fails to get the region so default to US
            "UZ"
        }
    val phoneNumber = phoneNumberUtil.parse(this, region)
    val result =  phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
    return if (!isSecret) result else{
        var result2 = ""
        result.mapIndexed{ index, c ->
            if (index > 6 && index < result.length - 2 && c != ' ') {
                result2 += "*"
            }else result2 += c
        }
        result2
    }

    }catch (e: Exception){
        return this
    }
}