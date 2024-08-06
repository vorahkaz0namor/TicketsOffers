package com.example.resources.views

import android.content.Context
import android.text.Layout.Alignment
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.withStyledAttributes
import com.example.resources.R
import kotlin.math.absoluteValue
import kotlin.math.ceil

class HintView @JvmOverloads constructor(
    context: Context,
    attributeSet:AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
): LinearLayout(context, attributeSet, defStyleAttr, defStyleRes) {
    private val eightDpExtracted = resources.getDimensionPixelSize(R.dimen.eight_dp)
    private val sixteenDpExtracted = resources.getDimensionPixelSize(R.dimen.sixteen_dp)
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
            /* top = */ eightDpExtracted,
            /* right = */ 0,
            /* bottom = */ 0
            )
        textSize = ceil(resources.getDimension(R.dimen.fourteen_sp) / resources.displayMetrics.density)
        textAlignment = View.TEXT_ALIGNMENT_GRAVITY
        setTextColor(resources.getColor(R.color.color_d9d9d9, context.theme))
    }

    init {
        orientation = VERTICAL
        setPadding(
            /* left = */ sixteenDpExtracted,
            /* top = */ 0,
            /* right = */ sixteenDpExtracted,
            /* bottom = */ 0
        )
        context.withStyledAttributes(attributeSet, R.styleable.HintView) {
            val imageResId = getResourceId(R.styleable.HintView_srcCompat, 0)
            if (imageResId != 0) {
                imageView.setImageResource(imageResId)
            }
            textView.text = getString(R.styleable.HintView_android_text)
        }
        addView(imageView)
        addView(textView)
    }
}