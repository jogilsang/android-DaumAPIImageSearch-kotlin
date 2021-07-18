package com.example.recyclersample.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.recyclersample.R;


/**
 * Created by TedPark on 16. 4. 11..
 */

public class ClearEditText extends AppCompatEditText implements TextWatcher, View.OnTouchListener, View.OnFocusChangeListener {

    private Drawable searchDrawable;
    private Drawable clearDrawable;
    private OnFocusChangeListener onFocusChangeListener;
    private OnTouchListener onTouchListener;
    private TextWatcher textWatcher;

    public ClearEditText(final Context context) {
        super(context);
        init();
    }

    public ClearEditText(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearEditText(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

//    @Override
//    public boolean onKeyPreIme( int keyCode, KeyEvent event ) {
//
//        Log.d("AutoCallService", "keyCode : "+keyCode+"");
//        Log.d("AutoCallService", "event.getKeyCode() : "+event.getKeyCode()+"");
//        switch (event.getAction()) {
//
//            case KeyEvent.ACTION_DOWN :
//
//                if( event.getAction() == KeyEvent.ACTION_DOWN ) {
//                    if( keyCode == KeyEvent.KEYCODE_BACK ) {
//                        this.clearFocus();
//
//                        Log.d("AutoCallService", "onBackPressed()2");
//                    }
//                }
//                break;
//        }
//        return super.onKeyPreIme( keyCode, event );
//    }
    // http://nerobong2.blogspot.kr/2015/12/androidedittext.html

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.onFocusChangeListener = onFocusChangeListener;
    }

    @Override
    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    @Override
    public void addTextChangedListener(TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
    }

    private void init() {

        Drawable tempDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_cancel_black_24dp);
        clearDrawable = DrawableCompat.wrap(tempDrawable);
        DrawableCompat.setTintList(clearDrawable,getHintTextColors());
        clearDrawable.setBounds(0, 0, clearDrawable.getIntrinsicWidth(), clearDrawable.getIntrinsicHeight());

        tempDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_search_black_24dp);
        searchDrawable = DrawableCompat.wrap(tempDrawable);
        DrawableCompat.setTintList(searchDrawable,getHintTextColors());
        searchDrawable.setBounds(0, 0, searchDrawable.getIntrinsicWidth(), searchDrawable.getIntrinsicHeight());

        setClearIconVisible(false);


        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        super.addTextChangedListener(this);
    }


    @Override
    public void onFocusChange(final View view, final boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }

        if (onFocusChangeListener != null) {
            onFocusChangeListener.onFocusChange(view, hasFocus);
        }
    }


    @Override
    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        final int x = (int) motionEvent.getX();
        if (clearDrawable.isVisible() && x > getWidth() - getPaddingRight() - clearDrawable.getIntrinsicWidth()) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                setError(null);
                setText(null);
            }
            return true;
        }

        if (onTouchListener != null) {
            return onTouchListener.onTouch(view, motionEvent);
        } else {
            return false;
        }

    }

    @Override
    public final void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
        if (isFocused()) {
            setClearIconVisible(s.length() > 0);
        }

        if(textWatcher != null){
            textWatcher.onTextChanged(s, start, before, count);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if(textWatcher != null){
            textWatcher.beforeTextChanged(s, start, count, after);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(textWatcher != null){
            textWatcher.afterTextChanged(s);
        }
    }


    private void setClearIconVisible(boolean visible) {
        clearDrawable.setVisible(visible, false);
        setCompoundDrawables(searchDrawable, null,
                visible ? clearDrawable : null, null);
    }

}