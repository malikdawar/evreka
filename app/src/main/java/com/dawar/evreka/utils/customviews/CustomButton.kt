package com.dawar.evreka.utils.customviews

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import com.dawar.evreka.R

class CustomButton :
    androidx.appcompat.widget.AppCompatButton {
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context) : super(context)

    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.MyButtonView)
            typeface = Typeface.createFromAsset(context.assets, "OpenSans-Regular.ttf")
            a.recycle()
            isAllCaps = false
        }
    }
}