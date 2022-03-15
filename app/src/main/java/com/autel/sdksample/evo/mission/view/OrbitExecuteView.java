package com.autel.sdksample.evo.mission.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.autel.common.remotecontroller.RemoteControllerCommandStickMode;
import com.autel.sdksample.R;
import com.autel.sdksample.evo.mission.util.AutelConfigManager;
import com.autel.sdksample.evo.mission.widge.AutelSegmentedGroup;

/**
 * Created by A15387 on 2017/11/15.
 */

public class OrbitExecuteView extends FrameLayout {

    private LayoutInflater inflater;
    private FrameLayout titleContainer;
    private FrameLayout bottomContainer;
    private ViewGroup contentContainer;
    private OrbitExecuteViewListener orbitExecuteViewListener;
    private GestureDetector gestureDetector;
    private TextView up_tv;
    private TextView down_tv;
    private TextView left_tv;
    private TextView right_tv;
    private View left_img;
    private View right_img;
    private RemoteControllerCommandStickMode remoteControllerCommandStickMode = RemoteControllerCommandStickMode.USA;
    private AutelSegmentedGroup orbit_rb;

    public OrbitExecuteView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public OrbitExecuteView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public OrbitExecuteView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        inflater = LayoutInflater.from(context);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_mission_container,null);
        titleContainer = (FrameLayout) view.findViewById(R.id.title_container);
        bottomContainer = (FrameLayout) view.findViewById(R.id.bottom_container);
        contentContainer = (ViewGroup)view.findViewById(R.id.content_container);
        this.addView(view);
        showOrbitExecuteView();
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if ((e2.getX() - e1.getX()) > 70 && (e2.getY() - e1.getY()) < 200 && (e1.getY() - e2.getY()) < 200) {
                    //右滑
                    //OrbitExecuteView.this.animate().translationX(OrbitExecuteView.this.getWidth()).start();
                    if(orbitExecuteViewListener != null){
                        orbitExecuteViewListener.slideHide();
                        orbitExecuteViewListener.hideClick();
                    }
                }
                return true;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        gestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    public void showOrbitExecuteView(){
        clearView();
        View titleView = inflater.inflate(R.layout.common_mission_title_with_hide,null);
        TextView commonTitle = (TextView) titleView.findViewById(R.id.common_title);
        commonTitle.setText(R.string.mission_orbit);
        TextView commonHide = (TextView)titleView.findViewById(R.id.common_hide);
        commonHide.setText(R.string.mission_hide);
        commonHide.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orbitExecuteViewListener != null){
                    orbitExecuteViewListener.hideClick();
                }
            }
        });
        View contentView = inflater.inflate(R.layout.oribit_execute_content,null);
        up_tv = (TextView)contentView.findViewById(R.id.up_tv);
        down_tv = (TextView)contentView.findViewById(R.id.down_tv);
        left_tv = (TextView)contentView.findViewById(R.id.left_tv);
        right_tv = (TextView)contentView.findViewById(R.id.right_tv);
        left_img = contentView.findViewById(R.id.left_img);
        right_img = contentView.findViewById(R.id.right_img);

        orbit_rb = (AutelSegmentedGroup)contentView.findViewById(R.id.orbit_rb);
        orbit_rb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_left_btn){
                    showLeftStick(OrbitExecuteView.this.remoteControllerCommandStickMode);
                }else if(checkedId == R.id.rb_right_btn){
                    showRightStick(OrbitExecuteView.this.remoteControllerCommandStickMode);
                }
            }
        });
        orbit_rb.check(R.id.rb_left_btn);
        View pauseBtn = contentView.findViewById(R.id.pause_btn);
        pauseBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orbitExecuteViewListener != null){
                    orbitExecuteViewListener.pauseClick();
                }
            }
        });
        View bottomView = inflater.inflate(R.layout.common_mission_bottom,null);
        bottomView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orbitExecuteViewListener != null){
                    orbitExecuteViewListener.exitClick();
                }
            }
        });
        TextView bottomTitle = (TextView) bottomView.findViewById(R.id.bottom_title);
        bottomTitle.setText(R.string.mission_exit);
        bottomTitle.setTextColor(AutelConfigManager.instance().getAppContext().getResources().getColor(R.color.red));
        titleContainer.addView(titleView);
        contentContainer.addView(contentView);
        bottomContainer.addView(bottomView);
    }

    private void clearView() {
        titleContainer.removeAllViews();
        contentContainer.removeAllViews();
        bottomContainer.removeAllViews();
    }

    public interface OrbitExecuteViewListener{
        void hideClick();
        void exitClick();
        void slideHide();
        void pauseClick();
    }

    public void setOrbitExecuteViewListener(OrbitExecuteViewListener orbitExecuteViewListener){
        this.orbitExecuteViewListener = orbitExecuteViewListener;
    }

    public void showRemoteControllerCommandStickMode(RemoteControllerCommandStickMode remoteControllerCommandStickMode){
        if(remoteControllerCommandStickMode == RemoteControllerCommandStickMode.UNKNOWN){
            return;
        }
        this.remoteControllerCommandStickMode = remoteControllerCommandStickMode;
        showLeftStick(remoteControllerCommandStickMode);
        showRightStick(remoteControllerCommandStickMode);
    }

    private void showRightStick(RemoteControllerCommandStickMode remoteControllerCommandStickMode) {
        switch (remoteControllerCommandStickMode){
            case USA:
                updateUSAStick(false);
                break;
            case JAPAN:
                updateJapanStick(false);
                break;
            case CHINA:
                updateChinaStick(false);
                break;
        }
    }

    private void showLeftStick(RemoteControllerCommandStickMode remoteControllerCommandStickMode) {
        switch (remoteControllerCommandStickMode){
            case USA:
                updateUSAStick(true);
                break;
            case JAPAN:
                updateJapanStick(true);
                break;
            case CHINA:
                updateChinaStick(true);
                break;
        }
    }

    private void updateUSAStick(boolean isLeft){
        if(isLeft){
            up_tv.setText(R.string.orbit_altitude_increase);
            down_tv.setText(R.string.orbit_altitude_decrease);
            left_tv.setText(R.string.orbit_rotate_left);
            right_tv.setText(R.string.orbit_rotate_right);
            left_img.setVisibility(View.GONE);
            right_img.setVisibility(View.GONE);
        }else{
            up_tv.setText(R.string.orbit_radius_increase);
            down_tv.setText(R.string.orbit_radius_decrease);
            left_tv.setText(R.string.orbit_ccw);
            right_tv.setText(R.string.orbit_cw);
            left_img.setVisibility(View.VISIBLE);
            right_img.setVisibility(View.VISIBLE);
        }
    }

    private void updateJapanStick(boolean isLeft){
        if(isLeft){
            up_tv.setText(R.string.orbit_radius_increase);
            down_tv.setText(R.string.orbit_radius_decrease);
            left_tv.setText(R.string.orbit_rotate_left);
            right_tv.setText(R.string.orbit_rotate_right);
            left_img.setVisibility(View.GONE);
            right_img.setVisibility(View.GONE);
        }else{
            up_tv.setText(R.string.orbit_altitude_increase);
            down_tv.setText(R.string.orbit_altitude_decrease);
            left_tv.setText(R.string.orbit_ccw);
            right_tv.setText(R.string.orbit_cw);
            left_img.setVisibility(VISIBLE);
            right_img.setVisibility(VISIBLE);
        }
    }

    private void updateChinaStick(boolean isLeft){
        if(isLeft){
            up_tv.setText(R.string.orbit_radius_increase);
            down_tv.setText(R.string.orbit_radius_decrease);
            left_tv.setText(R.string.orbit_ccw);
            right_tv.setText(R.string.orbit_cw);
            left_img.setVisibility(VISIBLE);
            right_img.setVisibility(VISIBLE);
        }else{
            up_tv.setText(R.string.orbit_altitude_increase);
            down_tv.setText(R.string.orbit_altitude_decrease);
            left_tv.setText(R.string.orbit_rotate_left);
            right_tv.setText(R.string.orbit_rotate_right);
            left_img.setVisibility(VISIBLE);
            right_img.setVisibility(VISIBLE);
        }
    }


}
