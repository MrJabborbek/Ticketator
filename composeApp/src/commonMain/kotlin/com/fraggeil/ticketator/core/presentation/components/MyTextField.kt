package com.fraggeil.ticketator.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fraggeil.ticketator.core.domain.PlatformType
import com.fraggeil.ticketator.core.domain.phoneNumberFormatting.PhoneNumberVisualTransformation
import com.fraggeil.ticketator.core.theme.Blue
import com.fraggeil.ticketator.core.theme.BlueContainer
import com.fraggeil.ticketator.core.theme.BlueDark
import com.fraggeil.ticketator.core.theme.LightGray
import com.fraggeil.ticketator.core.theme.TextColor
import com.fraggeil.ticketator.core.theme.White
import io.michaelrocks.libphonenumber.kotlin.PhoneNumberUtil
import io.michaelrocks.libphonenumber.kotlin.metadata.defaultMetadataLoader
import org.koin.compose.koinInject

enum class InputStyle{
    ANY, NUMBER, PHONE_NUMBER, PASSPORT,DESCRIPTION, MONEY, PLASTIC_CARD,
    PLASTIC_CARD_VALID_DATE,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(
    isLoading: Boolean = false,
    modifier: Modifier = Modifier,
    label: String,
    hint: String? = null,
    value: String,
    onValueChange: (String) -> Unit,
    prefix: String? = null,
    keyboardType: KeyboardType? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    trailingIcon: ImageVector? = null,
    isEditable: Boolean = true,
    onClicked: () -> Unit = {},
    inputStyle: InputStyle = InputStyle.ANY,
    suggestions: List<String>? = null,
    maxSuggestions: Int = 3
) {

    fun changeValue(text: String) {
        if (isLoading) return

        when (inputStyle) {
            InputStyle.ANY -> onValueChange(text)
            InputStyle.NUMBER -> {
                if (text.any { !it.isDigit() }) return
                onValueChange(text)
            }

            InputStyle.PHONE_NUMBER -> {
                if (text.length > 13) return// for uzbekistan
                if (!("^\\+?[1-9]\\d{1,14}\$".toRegex().matches(text))) return
                if (text.any { !it.isDigit() && it != '+' }) return
                if (text.indexOfLast { it == '+' } > 0) return
                if (text.length <= 3) {
                    onValueChange("+998")
                    return
                }
                onValueChange(text)
            }

            InputStyle.PASSPORT -> {
                if (text.length > 9) return
                if (!text.getOrElse(0, { 'a' }).isLetter()) return
                if (!text.getOrElse(1, { 'a' }).isLetter()) return
                if (!text.drop(2).all { it.isDigit() }) return

                onValueChange(text)
            }

            InputStyle.DESCRIPTION -> {
                onValueChange(text)
            }

            InputStyle.MONEY -> {
                if (text.any { !it.isDigit() }) return
                onValueChange(text)
            }

            InputStyle.PLASTIC_CARD -> {
                if (text.length > 16) return
                if (text.any { !it.isDigit() }) return
                onValueChange(text)
            }

            InputStyle.PLASTIC_CARD_VALID_DATE -> {
                if (text.length > 4) return
                if (text.any { !it.isDigit() }) return
                if (text.length >= 2) {
                    if (text.substring(0, 2).toInt() > 12) return
                }
                onValueChange(text)
            }
        }
//            onValueChange(text)
    }

    val keyboardTypeLocal by remember {
        if (keyboardType != null) return@remember mutableStateOf(keyboardType)
        val type = when (inputStyle) {
            InputStyle.ANY -> KeyboardType.Text
            InputStyle.NUMBER -> KeyboardType.Number
            InputStyle.PHONE_NUMBER -> KeyboardType.Phone
            InputStyle.PASSPORT -> KeyboardType.Text
            InputStyle.DESCRIPTION -> KeyboardType.Text
            InputStyle.MONEY -> KeyboardType.Number
            InputStyle.PLASTIC_CARD -> KeyboardType.Number
            InputStyle.PLASTIC_CARD_VALID_DATE -> KeyboardType.Number
        }
        mutableStateOf(type)
    }

//    val phoneNumberUtil: PhoneNumberUtil by remember {
//        mutableStateOf(PhoneNumberUtil.createInstance(defaultMetadataLoader()))
//    }
//    val region = remember {
//        try {
//            //Locale.current.region
//            "UZ"
//        } catch (e: Exception) {
//            // as of compose 1.4.3, js fails to get the region so default to US
//            "UZ"
//        }
//    }
    val isAllCaps = remember {
        if (inputStyle == InputStyle.PASSPORT) return@remember mutableStateOf(true)
        mutableStateOf(false)
    }
    var isMenuExpanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isMenuExpanded,
        onExpandedChange = {isMenuExpanded = it}
    ) {
        TextField(
            enabled = isEditable,
            trailingIcon = if (trailingIcon != null){
                {
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = null,
                        tint = BlueDark,
                        modifier = Modifier.size(24.dp)
                    )
                }
            } else null,
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryEditable)
                .then(
                    if (inputStyle == InputStyle.DESCRIPTION) {
                        Modifier.height(150.dp)
                    } else {
                        Modifier
                    }
                )
                .then(
                    if (!isEditable) Modifier
                        .clickable(
                            onClick = {
                                if (!isLoading)
                                    isMenuExpanded = true
                                    onClicked()
                                      },
                            indication = null,
                            interactionSource = MutableInteractionSource()
                        ) else Modifier
                )
                .shimmerLoadingAnimation(
                    isLoadingCompleted = !isLoading,
                    shimmerStyle = ShimmerStyle.Custom,
                ),
            placeholder = { Text(text = hint?.takeIf { !isLoading } ?: "", maxLines = 2, overflow = TextOverflow.Ellipsis) },
            label = { Text(text = label.takeIf { !isLoading } ?: "", maxLines = 2, overflow = TextOverflow.Ellipsis) },
            value = if (isLoading) "" else if (isAllCaps.value) value.uppercase() else value,
            onValueChange = {changeValue(it)},
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = BlueContainer,
                unfocusedContainerColor = LightGray,
                disabledContainerColor = LightGray,
                focusedLabelColor = BlueDark,
                unfocusedLabelColor = BlueDark,
                disabledLabelColor = BlueDark,
                cursorColor = BlueDark,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedTextColor = TextColor,
                unfocusedTextColor = TextColor,
                disabledTextColor = TextColor,
            ),
            textStyle = if (!isEditable) MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.sp
            ) else MaterialTheme.typography.bodyLarge.copy(letterSpacing = 0.sp),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardTypeLocal,
                imeAction = ImeAction.Next
            ),
            keyboardActions = keyboardActions,
            singleLine = when (inputStyle) {
                InputStyle.ANY -> true
                InputStyle.NUMBER -> true
                InputStyle.PHONE_NUMBER -> true
                InputStyle.PASSPORT -> true
                InputStyle.DESCRIPTION -> false
                InputStyle.MONEY -> true
                InputStyle.PLASTIC_CARD -> true
                InputStyle.PLASTIC_CARD_VALID_DATE -> true
            },
            prefix = { if (prefix != null) Text(text = prefix) },
            visualTransformation = when (inputStyle) {
                InputStyle.PHONE_NUMBER -> {
                    when (koinInject<PlatformType>()) {
                        PlatformType.ANDROID, PlatformType.DESKTOP -> PhoneNumberVisualTransformation(
                            PhoneNumberUtil.createInstance(defaultMetadataLoader()),
                            "UZ"
                        )

                        PlatformType.IOS -> VisualTransformation.None // TODO TODO
                    }
                }

                InputStyle.MONEY -> SeparateEach()
                InputStyle.PLASTIC_CARD -> SeparateEach(separateEach = 4, reverse = true)
                InputStyle.PLASTIC_CARD_VALID_DATE -> SeparateEach(
                    separateEach = 2,
                    separator = '/',
                    reverse = true
                )

                else -> VisualTransformation.None
            }
        )
            suggestions?.filter {it.contains(value, true)}?.takeIf { it.isNotEmpty() }?.take(maxSuggestions)?.let { items ->
                val scrollState = rememberScrollState()
                ExposedDropdownMenu(
                    modifier = Modifier.changeScrollStateByMouse(
                        scrollState = scrollState,
                        isVerticalScroll = true
                    ),
                    scrollState = scrollState,
//                    modifier = Modifier.width(IntrinsicSize.Max),
                    matchTextFieldWidth = true,
                    expanded = isMenuExpanded,
                    onDismissRequest = { isMenuExpanded = false },
                    containerColor = Blue
                ) {
                    items.forEach { item ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = item,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Medium,
                                        letterSpacing = 0.sp
                                    ),
                                    color = White,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,

                                )
                            },
                            onClick = {
                                changeValue(item)
                                isMenuExpanded = false
                            },
//                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                        if (item != items.last()) HorizontalDivider(color = White)
                    }

                }
            } ?: kotlin.run {
                isMenuExpanded = false
            }

    }
}

private class SeparateEach(private val separateEach: Int = 3, private val separator: Char = ' ', private val reverse: Boolean = false) : VisualTransformation { //TODO optimize
    private var lastOriginalText: String? = null
    private var lastFormattedText: String? = null
    private var lastOffsetMapping: OffsetMapping? = null

    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text
        // Check if we already calculated this transformation
        if (originalText == lastOriginalText) {
            return TransformedText(
                text = AnnotatedString(lastFormattedText ?: originalText),
                offsetMapping = lastOffsetMapping ?: OffsetMapping.Identity
            )
        }

        // Perform the transformation
        val formattedText = if (reverse) {
            originalText
                .chunked(separateEach)
                .joinToString(separator.toString()) { it }
        } else{
            originalText
                .reversed()
                .chunked(separateEach)
                .joinToString(separator.toString()) { it }
                .reversed()
        }

        val offsetMapping = SeparateEachSeparatorOffsetMapping(formattedText, separator)

        // Cache the results
        lastOriginalText = originalText
        lastFormattedText = formattedText
        lastOffsetMapping = offsetMapping

        return TransformedText(
            text = AnnotatedString(formattedText),
            offsetMapping = offsetMapping
        )
    }
    private class SeparateEachSeparatorOffsetMapping(
        private val transformed: String,
        private val separator: Char,
    ) : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            var spacesCount = 0
            var originalCharsCount = 0

            for (i in transformed.indices) {
                if (transformed[i] == separator) spacesCount++
                else originalCharsCount++

                if (originalCharsCount == offset) return i + 1
            }

            return transformed.length
        }

        override fun transformedToOriginal(offset: Int): Int {
            var originalCharsCount = 0

            for (i in transformed.indices) {
                if (transformed[i] != separator) originalCharsCount++

                if (i == offset) return originalCharsCount
            }
            return originalCharsCount
        }
    }
}
