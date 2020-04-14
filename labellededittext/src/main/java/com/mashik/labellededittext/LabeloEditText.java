package com.mashik.labellededittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.mashik.labellededittext.databinding.LayoutCustomEdittextBinding;

import java.util.Arrays;

public class LabeloEditText extends ConstraintLayout {

    LayoutCustomEdittextBinding binding;
    Context context;
    int colorBlack = Color.parseColor("#000000");
    int colorRed = Color.parseColor("#FF5555");

    boolean isErrorEnable;
    CharSequence title = null;
    CharSequence error = null;
    CharSequence text = null;
    CharSequence hint = null;
    int titleColor;
    int errorTextColor;
    int borderColor;
    int borderWidth;
    int maxLine;
    int minLine;
    int maxLength;
    int inputType;
    float textEditTextSize;
    float[] cornerRadii = new float[8];
    Drawable editTextBackGround = null;

    public LabeloEditText(Context context) {
        super(context);
        this.context = context;
        obtainStyledAttributes(context, null, 0);
        init();
    }

    public LabeloEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        obtainStyledAttributes(context, attrs, 0);
        init();
    }

    public LabeloEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        obtainStyledAttributes(context, attrs, defStyleAttr);
        init();
    }

    private void obtainStyledAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Labelo_EditText, defStyleAttr, 0);

            isErrorEnable = typedArray.getBoolean(R.styleable.Labelo_EditText_isErrorEnable, false);

            title = typedArray.getString(R.styleable.Labelo_EditText_title_text);
            error = typedArray.getString(R.styleable.Labelo_EditText_error_text);
            text = typedArray.getString(R.styleable.Labelo_EditText_editText);
            hint = typedArray.getString(R.styleable.Labelo_EditText_editText_hint);

            titleColor = typedArray.getColor(R.styleable.Labelo_EditText_title_text_color, colorBlack);
            errorTextColor = typedArray.getColor(R.styleable.Labelo_EditText_error_text_color, colorRed);
            borderColor = typedArray.getColor(R.styleable.Labelo_EditText_editText_border_color, colorBlack);

            borderWidth = typedArray.getInteger(R.styleable.Labelo_EditText_editText_border_width, 1);
            maxLine = typedArray.getInteger(R.styleable.Labelo_EditText_editText_max_line, 1);
            minLine = typedArray.getInteger(R.styleable.Labelo_EditText_editText_min_line, 1);
            maxLength = typedArray.getInteger(R.styleable.Labelo_EditText_editText_max_length, 99);
            float radius = typedArray.getFloat(R.styleable.Labelo_EditText_editText_corner_radius, 0);
            Arrays.fill(cornerRadii, radius);

            editTextBackGround = typedArray.getDrawable(R.styleable.Labelo_EditText_editText_background);

            inputType = typedArray.getInt(R.styleable.Labelo_EditText_android_inputType, EditorInfo.TYPE_NULL);

            textEditTextSize = typedArray.getDimension(R.styleable.Labelo_EditText_editText_textSize, 16);

            typedArray.recycle();

            return;
        }

        isErrorEnable = false;

        title = null;
        error = null;
        text = null;
        hint = null;

        titleColor = colorBlack;
        errorTextColor = colorRed;
        borderColor = colorBlack;

        borderWidth = 1;
        maxLine = 1;
        minLine = 1;
        maxLength = 99;
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = LayoutCustomEdittextBinding.inflate(inflater, this, true);
        setupView();
    }

    private void setupView() {
        setTitle();
        setEditText();
        setError();
    }

    private void setTitle() {
        binding.labelTitle.setText(title);
        binding.labelTitle.setTextColor(titleColor);
    }

    private void setEditText() {
        binding.editText.setInputType(inputType);//1
        binding.editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});//2
        binding.editText.setMaxLines(maxLine);//3
        binding.editText.setMinLines(minLine);//4
        if(hint != null )
            binding.editText.setHint(hint);//5
        if(text != null)
            binding.editText.setText(text);//6

        GradientDrawable gd = new GradientDrawable();
        gd.setStroke(borderWidth, borderColor);//7,8
        gd.setCornerRadii(cornerRadii);//9
        binding.editText.setBackground(gd);

        binding.editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textEditTextSize);//10

        if (editTextBackGround != null)
            binding.editText.setBackground(editTextBackGround);//11


    }

    private void setError() {
        binding.labelError.setText(error);
        binding.labelError.setTextColor(errorTextColor);
        if(!isErrorEnable)
            binding.labelError.setVisibility(GONE);
        else
            binding.labelError.setVisibility(VISIBLE);
    }

}

