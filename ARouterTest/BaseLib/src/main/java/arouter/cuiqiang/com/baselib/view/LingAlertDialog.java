/*
 * Copyright (c) 2017 Mobvoi Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package arouter.cuiqiang.com.baselib.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

import arouter.cuiqiang.com.baselib.R;

public class LingAlertDialog extends Dialog implements View.OnClickListener, NumberPicker.Formatter {
    private TextView mTitleTv;
    private TextView mMessageTv;
    private TextView mLoadingTv;
    private Button mCancelBtn;
    private Button mSubmitBtn;
    private Button mSingleBtn;
    private Button mLoadingSubmitBtn;
    private NumberPicker mNumberPicker;
    private FrameLayout mContentLayout;
    private View mTitleLayout;
    private View mButtonLayout;
    private View mLoadingLayout;
    private ImageView mCloseBtn;

    private Callback mCallback;

    public LingAlertDialog(Context context) {
        this(context, false);
    }

    public LingAlertDialog(Context context, boolean alert) {
        super(context, R.style.LingDialogAnimation);
        init();
    }

    private void init() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().getAttributes().windowAnimations = R.style.LingDialogStyle;
        View v = getLayoutInflater().inflate(R.layout.dialog_ling, null);
        setCancelable(false);
        setContentView(v);

        mTitleLayout = findViewById(R.id.title_layout);
        mContentLayout = (FrameLayout) v.findViewById(R.id.content_layout);
        mButtonLayout = findViewById(R.id.button_layout);
        mLoadingLayout = findViewById(R.id.loading_layout);

        mTitleTv = (TextView) v.findViewById(R.id.title);
        mMessageTv = (TextView) v.findViewById(R.id.message);
        mLoadingTv = (TextView) v.findViewById(R.id.loading_tv);
        mCancelBtn = (Button) v.findViewById(R.id.cancel_btn);
        mSubmitBtn = (Button) v.findViewById(R.id.save_btn);
        mSingleBtn = (Button) v.findViewById(R.id.single_btn);
        mLoadingSubmitBtn = (Button) v.findViewById(R.id.loading_submit_btn);
        mNumberPicker = (NumberPicker) v.findViewById(R.id.picker);
        mCloseBtn = (ImageView) v.findViewById(R.id.close_btn);

        mCancelBtn.setOnClickListener(this);
        mSubmitBtn.setOnClickListener(this);
        mSingleBtn.setOnClickListener(this);
        mLoadingSubmitBtn.setOnClickListener(this);
        mCloseBtn.setOnClickListener(this);
    }

    public void setLoadingView(int msg) {
        setLoadingView(getContext().getString(msg));
    }

    public void setLoadingView(CharSequence loadingMsg) {
        mTitleLayout.setVisibility(View.GONE);
        mContentLayout.setVisibility(View.GONE);
        mNumberPicker.setVisibility(View.GONE);
        mButtonLayout.setVisibility(View.GONE);
        mSingleBtn.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(loadingMsg)) {
            mLoadingTv.setVisibility(View.GONE);
        } else {
            mLoadingTv.setText(loadingMsg);
        }
    }

    public void setCustomView(View view) {
        mMessageTv.setVisibility(View.GONE);
        mContentLayout.addView(view);
    }

    public void setCustomViewNoTitle(View view) {
        mMessageTv.setVisibility(View.GONE);
        mContentLayout.addView(view);
        mTitleLayout.setVisibility(View.GONE);
        int paddingTop = getContext().getResources().getDimensionPixelSize(R.dimen.button_dialog_content_padding_top);
        mContentLayout.setPadding(mContentLayout.getPaddingLeft(), paddingTop, mContentLayout.getPaddingLeft(), 0);
    }

    public void setMessage(int title, int message) {
        setMessage(getContext().getString(title), getContext().getString(message));
    }

    public void setMessage(CharSequence title, CharSequence message) {
        if (TextUtils.isEmpty(title)) {
            setMessage(message);
        } else if (TextUtils.isEmpty(message)) {
            setTitle(title);
        } else {
            mTitleTv.setText(title);
            mMessageTv.setText(message);
        }
    }

    public void setTitle(CharSequence title) {
        if (TextUtils.isEmpty(title)) {
            throw new IllegalArgumentException("Must have a title!");
        }
        // Hide message and just show title.
        mTitleTv.setText(title);
        mContentLayout.setVisibility(View.GONE);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mTitleTv.getLayoutParams();
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mTitleTv.setLayoutParams(params);
    }

    public void setMessage(CharSequence message) {
        if (TextUtils.isEmpty(message)) {
            throw new IllegalArgumentException("Must have a message!");
        }
        // Hide title and just show message.
        mMessageTv.setText(message);
        mTitleLayout.setVisibility(View.GONE);
        int paddingTop = getContext().getResources().getDimensionPixelSize(R.dimen.button_dialog_content_padding_top);
        mContentLayout.setPadding(mContentLayout.getPaddingLeft(), paddingTop, mContentLayout.getPaddingLeft(), 0);
    }

    public void enableNumberPicker(int minValue, int maxValue, int defaultValue) {
        mNumberPicker.setFormatter(this);
        mNumberPicker.setMaxValue(maxValue);
        mNumberPicker.setMinValue(minValue);
        mNumberPicker.setValue(defaultValue);
        mNumberPicker.getChildAt(0).setFocusable(false);
        mNumberPicker.setVisibility(View.VISIBLE);
        setNumberPickerTextColor(mNumberPicker, getContext().getResources().getColor(R.color.primary_text));
    }

    public int getPickerValue() {
        return mNumberPicker.getValue();
    }

    public void setButtonText(String cancelButton, String submitButton) {
        if (TextUtils.isEmpty(cancelButton)) {
            setButtonText(submitButton);
        } else if (TextUtils.isEmpty(submitButton)) {
            setButtonText(cancelButton);
        } else {
            mButtonLayout.setVisibility(View.VISIBLE);
            mCancelBtn.setText(cancelButton);
            mSubmitBtn.setText(submitButton);
            mSingleBtn.setVisibility(View.GONE);
        }
    }

    public void setButtonText(String text) {
        if (TextUtils.isEmpty(text)) {
            throw new IllegalArgumentException("Must have a button!");
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mTitleTv.getLayoutParams();
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mTitleTv.setLayoutParams(params);
        mMessageTv.setGravity(Gravity.CENTER);
        mButtonLayout.setVisibility(View.GONE);
        mSingleBtn.setVisibility(View.VISIBLE);
        mSingleBtn.setText(text);
    }

    public void setLoadingSubmitText(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mTitleTv.getLayoutParams();
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mTitleTv.setLayoutParams(params);
        mButtonLayout.setVisibility(View.GONE);
        mLoadingSubmitBtn.setVisibility(View.VISIBLE);
        mLoadingSubmitBtn.setText(text);
    }

    private static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color) {
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = numberPicker.getChildAt(i);
            if (child instanceof EditText) {
                try {
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText) child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                } catch (NoSuchFieldException e) {
                } catch (IllegalAccessException e) {
                } catch (IllegalArgumentException e) {
                }
            }
        }
        return false;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public void showCloseBtn() {
        mCloseBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cancel_btn) {
            if (mCallback != null) {
                mCallback.onCancel();
            } else {
                dismiss();
            }
        } else if (id == R.id.save_btn) {
            if (mCallback != null) {
                mCallback.onSubmit();
            } else {
                dismiss();
            }
        } else if (id == R.id.single_btn || id == R.id.loading_submit_btn) {
            if (mCallback != null) {
                mCallback.onSubmit();
            } else {
                dismiss();
            }
        } else if(id == R.id.close_btn) {
            if (mCallback != null) {
                mCallback.onCancel();
            } else {
                dismiss();
            }
        }
    }

    @Override
    public String format(int value) {
        return String.valueOf(value);
    }

    public interface Callback {
        void onCancel();

        void onSubmit();
    }
}