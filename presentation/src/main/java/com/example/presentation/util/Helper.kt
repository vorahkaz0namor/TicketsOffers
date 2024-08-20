package com.example.presentation.util

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.resources.R
import com.example.presentation.model.UiState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.FieldPosition
import java.text.NumberFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

/**
 * Russian locale
 */
private val ruLocale = Locale("ru", "RU")

/**
 * Date representation
 */
internal val dateRepresentation = { value: OffsetDateTime ->
    value.format(
        DateTimeFormatter.ofPattern(
            /* pattern = */ "dd MMM",
            /* locale = */ ruLocale
        )
    )
}

/**
 * Day of week representation
 */
internal val dayOfWeekRepresentation = { value: OffsetDateTime ->
    value.dayOfWeek.getDisplayName(
        /* style = */ TextStyle.SHORT,
        /* locale = */ ruLocale
    )
}

/**
 * Price formatting
 */
internal val formatPrice = { price: Int ->
    NumberFormat
        .getInstance(ruLocale)
        .run {
            isGroupingUsed = true
            format(price)
        }
}

/**
 * Sets offer image
 */
@DrawableRes
internal val offerImage: (Int) -> Int = { id ->
    when (id) {
        1 -> R.drawable.im_die_antwoord
        2 -> R.drawable.im_sockrat_and_lera
        3 -> R.drawable.im_lampabickt
        else -> R.drawable.ic_takeoff_24
    }
}

/**
 * Get ticketOffer color
 */
internal fun ticketOfferColor(id: Int): Int =
    when (id) {
        1 -> R.color.color_ff5e5e
        10 -> R.color.color_2261bc
        else -> R.color.white
    }

/**
 *  Adjusts resize of the fragment when it overlaps by the other objects
 */
internal fun Activity.layoutSizeAdjust(rootView: View) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    } else {
        window?.setDecorFitsSystemWindows(false)
        rootView.onApplyWindowInsets(WindowInsets.CONSUMED)
    }
}

/**
 * Hide virtual keyboard from screen
 */
internal fun hideKeyboard(view: View) {
    val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

/**
 * Launches a Coroutine when Fragment is at least at state STARTED
 */
internal fun Fragment.viewScopeWithRepeat(
    block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            block()
        }
    }
}

internal fun UiState.logState(from: String) {
    Log.d("UI STATE", "FROM $from => $javaClass")
}