package com.example.resources.views

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.withStyledAttributes
import com.example.resources.R
import kotlin.math.ceil

class HintView @JvmOverloads constructor(
    context: Context,
    attributeSet:AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
): LinearLayout(context, attributeSet, defStyleAttr, defStyleRes) {
    private val deviceDensity = resources.displayMetrics.density
    private val eightDp = resources.getDimensionPixelSize(R.dimen.eight_dp)
    private val tenSp = resources.getDimension(R.dimen.ten_sp)
    private val twelveSp = resources.getDimension(R.dimen.twelve_sp)
    private val sixteenDp = resources.getDimensionPixelSize(R.dimen.twelve_dp)
    private inline fun <reified T : View> T.commonParams() =
        apply {
            layoutParams = LayoutParams(
                /* width = */ LayoutParams.WRAP_CONTENT,
                /* height = */ LayoutParams.WRAP_CONTENT,
            )
            gravity = Gravity.CENTER
        }
    private val imageView = ImageView(context).commonParams()
    private val textView = TextView(context).commonParams().apply {
        setPadding(
            /* left = */ 0,
            /* top = */ eightDp,
            /* right = */ 0,
            /* bottom = */ 0
            )
        textSize = ceil(
            (if (deviceDensity > 1.75)
                tenSp
            else
                twelveSp)
                    / deviceDensity
        )
        textAlignment = TEXT_ALIGNMENT_CENTER
        setTextColor(resources.getColor(R.color.color_d9d9d9, context.theme))
    }
    val hintText: String
        get() = textView.text.toString()

    init {
        orientation = VERTICAL
        setPadding(
            /* left = */ sixteenDp,
            /* top = */ 0,
            /* right = */ sixteenDp,
            /* bottom = */ 0
        )
        context.withStyledAttributes(attributeSet, R.styleable.HintView) {
            val imageResId = getResourceId(R.styleable.HintView_hintIcon, 0)
            if (imageResId != 0) {
                imageView.setImageResource(imageResId)
            }
            textView.text = getString(R.styleable.HintView_hintText)
        }
        addView(imageView)
        addView(textView)
    }
}