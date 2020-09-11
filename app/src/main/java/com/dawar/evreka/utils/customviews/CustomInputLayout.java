package com.dawar.evreka.utils.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import com.dawar.evreka.R;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Field;
/**
 * @author Malik Dawar, malikdawar332@gmail.com
 */
public class CustomInputLayout extends TextInputLayout {

    @Override
    public void setErrorTextAppearance(int resId) {
        super.setErrorTextAppearance(resId);
    }

    public CustomInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFont();
    }

    private void initFont() {
        EditText editText = getEditText();
        if (editText != null) {
            Typeface typeface = Typeface.createFromAsset(
                    getContext().getAssets(), "OpenSans-Regular.ttf");
            editText.setTypeface(typeface);
            setErrorEnabled(true);

            try {
                final Field cthf = TextInputLayout.class.getDeclaredField("mCollapsingTextHelper");
                cthf.setAccessible(true);
                final Object cth = cthf.get(this);
                final Field tpf = cth.getClass().getDeclaredField("mTextPaint");
                tpf.setAccessible(true);
                ((TextPaint) tpf.get(cth)).setTypeface(typeface);

            } catch (Exception ignored) {
                Log.d("sdf", ignored.getMessage());
            }
        }
    }
}