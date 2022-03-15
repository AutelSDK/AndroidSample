package com.autel.sdksample.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autel.sdk.product.BaseProduct;
import com.autel.sdksample.R;
import com.autel.sdksample.TestApplication;


public abstract class BaseActivity<T> extends AppCompatActivity {
    protected final String TAG = getClass().getSimpleName();
    private TextView log_output;
    protected T mController;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String text = (String) msg.obj;
            if (null != log_output) {
                log_output.setText(text);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseProduct product = getCurrentProduct();
        if (null != product) {
            mController = initController(product);
        }
        if (null == mController) {
            setContentView(R.layout.ac_connect_exception);
            return;
        }
        setContentView(R.layout.ac_base);
        ((LinearLayout) findViewById(R.id.base_layout)).addView(View.inflate(this, getCustomViewResId(), null));
        initUi();
        log_output = (TextView) findViewById(R.id.log_output);
        log_output.setMovementMethod(ScrollingMovementMethod.getInstance());
        requestFocus(log_output);
    }

    protected BaseProduct getCurrentProduct() {
        return ((TestApplication) getApplicationContext()).getCurrentProduct();
    }

    protected abstract T initController(BaseProduct product);

    protected abstract int getCustomViewResId();

    protected abstract void initUi();

    protected void logOut(String log) {
        Log.v(TAG, log);
        Message msg = handler.obtainMessage();
        msg.obj = log;
        handler.sendMessage(msg);
    }

    private void requestFocus(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    protected boolean isEmpty(String value) {
        return null == value || "".equals(value);
    }
}
