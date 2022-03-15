package com.autel.sdksample.evo.mission.widge;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.autel.sdksample.R;


/**
 * Created by A13087 on 2017/10/27.
 */

@SuppressWarnings("DefaultFileTemplate")
public class VisualDialogToast {

    private final Context context;
    private final View mView;
    private TextView mTextview3;
    private TextView mTextview2;
    private TextView mTextview1;
    private TextView mTextViewTitle;
    private Dialog dialog;

    public VisualDialogToast(Context mContext, final OnDialogClickListener listener) {
        this.context = mContext;
        dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        mView = View.inflate(context, R.layout.visual_view_toast, null);
        dialog.setContentView(mView);
        dialog.setCancelable(false);
//        if (context instanceof AutelBaseActivity) {
//            hideNavigationBarWhenDismiss((AutelBaseActivity) context);
//        }
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        mTextViewTitle = (TextView) mView.findViewById(R.id.tv_title_content);
        mTextview1 = (TextView) mView.findViewById(R.id.tv_cancel);
        mTextview2 = (TextView) mView.findViewById(R.id.tv_select_new_target);
        mTextview3 = (TextView) mView.findViewById(R.id.tv_exit_flight_mode);
        mTextview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onClickView1();
                }
                dialog.dismiss();
            }
        });
        mTextview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onClickView2();
                }
                dialog.dismiss();
            }
        });

        mTextview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onClickView3();
                }
                dialog.dismiss();
            }
        });
    }

    public void show() {
        dialog.show();
    }

    public void setCanceledOnTouchOutside(boolean cancelable){
        if (null != dialog) {
            dialog.setCanceledOnTouchOutside(true);
        }
    }

    public void setTextViewTitle(String title) {
        if (mTextViewTitle != null) {
            mTextViewTitle.setText(title);
        }
    }

    public void setTextView1(String title, int visible) {
        if (mTextview1 != null) {
            mTextview1.setText(title);
            mTextview1.setVisibility(visible);
        }
    }

    public void setTextView2(String title, int visible) {
        if (mTextview3 != null) {
            mTextview2.setText(title);
            mTextview2.setVisibility(visible);
        }
    }

    public void setTextView3(String title, int visible) {
        if (mTextview3 != null) {
            mTextview3.setText(title);
            mTextview3.setVisibility(visible);
        }
    }

    public boolean isShowing() {
        if (null != dialog)
            return dialog.isShowing();
        return false;
    }

    public void cancel(){
        if(null != dialog){
            dialog.dismiss();
        }
    }

    public interface OnDialogClickListener {
        //从左到右依次是1、2、3
        void onClickView1();

        void onClickView2();

        void onClickView3();
    }
}
