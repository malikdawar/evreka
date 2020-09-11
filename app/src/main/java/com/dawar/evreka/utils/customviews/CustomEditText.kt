package com.dawar.evreka.utils.customviews

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.util.AttributeSet
import com.dawar.evreka.R
/**
 * @author Malik Dawar, malikdawar332@gmail.com
 */
class CustomEditText :
    androidx.appcompat.widget.AppCompatEditText {
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context) : super(context)

    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val a: TypedArray = context!!.obtainStyledAttributes(attrs, R.styleable.MyTextView)
            typeface = Typeface.createFromAsset(context.assets, "OpenSans-Regular.ttf")
            a.recycle()
            maxLines = 1
            isLongClickable = false
            textSize = 14.0f
        }
    }
}