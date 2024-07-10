package com.example.presentation.util

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.presentation.model.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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