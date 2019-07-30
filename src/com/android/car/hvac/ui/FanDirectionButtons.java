/*
 * Copyright (c) 2016, The Android Open Source Project
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
package com.android.car.hvac.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.car.hvac.R;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * A set of buttons that controls the fan direction of the vehicle. Turning on one state will
 * turn off the other states.
 */
public class FanDirectionButtons extends LinearLayout {
	private static final String TAG = "FanDirection";
	
    public static final int FAN_DIRECTION_FACE = 0;
    public static final int FAN_DIRECTION_FACE_FLOOR = 1;
    public static final int FAN_DIRECTION_FLOOR = 2;
    public static final int FAN_DIRECTION_FLOOR_DEFROSTER = 3;

    @IntDef({FAN_DIRECTION_FACE, FAN_DIRECTION_FACE_FLOOR,
            FAN_DIRECTION_FLOOR, FAN_DIRECTION_FLOOR_DEFROSTER})
    public @interface FanDirection {}

    /**
     * A listener that is notified when a fan direction button has been clicked.
     */
    public interface FanDirectionClickListener {
        void onFanDirectionClicked(@FanDirection int direction);
    }

    private static final float UNSELECTED_BUTTON_ALPHA = 0.5f;
    private static final float SELECTED_BUTTON_ALPHA = 1.0f;

	private ImageView directionButton1;
	private ImageView directionButton2;
	private ImageView directionButton3;
	private ImageView directionButton4;
	
    private final Map<ImageView, Pair<Drawable, Drawable>> mFanMap = new HashMap<>();
    private final Map<ImageView, Integer> mControlMap = new HashMap<>();

    private FanDirectionClickListener mListener;

    public FanDirectionButtons(Context context) {
        super(context);
        init();
    }

    public FanDirectionButtons(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FanDirectionButtons(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setFanDirectionClickListener(FanDirectionClickListener listener) {
        mListener = listener;
    }

    private void init() {
        inflate(getContext(), R.layout.fan_direction, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Resources res = getResources();

        setOrientation(HORIZONTAL);

		directionButton1 = (ImageView) findViewById(R.id.direction_1);
        directionButton2 = (ImageView) findViewById(R.id.direction_2);
        directionButton3 = (ImageView) findViewById(R.id.direction_3);
        directionButton4 = (ImageView) findViewById(R.id.direction_4);

        Drawable directionOn1 = res.getDrawable(R.drawable.ic_fan_direction_1_on);
        Drawable directionOn2 = res.getDrawable(R.drawable.ic_fan_direction_2_on);
        Drawable directionOn3 = res.getDrawable(R.drawable.ic_fan_direction_3_on);
        Drawable directionOn4 = res.getDrawable(R.drawable.ic_fan_direction_4_on);

        Drawable directionOff1 = res.getDrawable(R.drawable.ic_fan_direction_1_off);
        Drawable directionOff2 = res.getDrawable(R.drawable.ic_fan_direction_2_off);
        Drawable directionOff3 = res.getDrawable(R.drawable.ic_fan_direction_3_off);
        Drawable directionOff4 = res.getDrawable(R.drawable.ic_fan_direction_4_off);

        mFanMap.put(directionButton1, new Pair(directionOn1, directionOff1));
        mFanMap.put(directionButton2, new Pair(directionOn2, directionOff2));
        mFanMap.put(directionButton3, new Pair(directionOn3, directionOff3));
        mFanMap.put(directionButton4, new Pair(directionOn4, directionOff4));

        mControlMap.put(directionButton1, FAN_DIRECTION_FACE);
        mControlMap.put(directionButton2, FAN_DIRECTION_FACE_FLOOR);
        mControlMap.put(directionButton3, FAN_DIRECTION_FLOOR);
        mControlMap.put(directionButton4, FAN_DIRECTION_FLOOR_DEFROSTER);

        for (ImageView v : mFanMap.keySet()) {
            v.setOnClickListener(mFanDirectionClickListener);
        }
    }
    
    public void setFanPosition(int direction){	
		switch(direction){
			case FAN_DIRECTION_FACE:
				directionButton1.setImageDrawable(mFanMap.get(directionButton1).first);
				directionButton1.setAlpha(SELECTED_BUTTON_ALPHA);
				directionButton2.setImageDrawable(mFanMap.get(directionButton2).second);
				directionButton2.setAlpha(UNSELECTED_BUTTON_ALPHA);
				directionButton3.setImageDrawable(mFanMap.get(directionButton3).second);
				directionButton3.setAlpha(UNSELECTED_BUTTON_ALPHA);
				directionButton4.setImageDrawable(mFanMap.get(directionButton4).second);
				directionButton4.setAlpha(UNSELECTED_BUTTON_ALPHA);
				break;
			case FAN_DIRECTION_FACE_FLOOR:
				directionButton1.setImageDrawable(mFanMap.get(directionButton1).second);
				directionButton1.setAlpha(UNSELECTED_BUTTON_ALPHA);
				directionButton2.setImageDrawable(mFanMap.get(directionButton2).first);
				directionButton2.setAlpha(SELECTED_BUTTON_ALPHA);
				directionButton3.setImageDrawable(mFanMap.get(directionButton3).second);
				directionButton3.setAlpha(UNSELECTED_BUTTON_ALPHA);
				directionButton4.setImageDrawable(mFanMap.get(directionButton4).second);
				directionButton4.setAlpha(UNSELECTED_BUTTON_ALPHA);
				break;
			case FAN_DIRECTION_FLOOR:
				directionButton1.setImageDrawable(mFanMap.get(directionButton1).second);
				directionButton1.setAlpha(UNSELECTED_BUTTON_ALPHA);
				directionButton2.setImageDrawable(mFanMap.get(directionButton2).second);
				directionButton2.setAlpha(UNSELECTED_BUTTON_ALPHA);
				directionButton3.setImageDrawable(mFanMap.get(directionButton3).first);
				directionButton3.setAlpha(SELECTED_BUTTON_ALPHA);
				directionButton4.setImageDrawable(mFanMap.get(directionButton4).second);
				directionButton4.setAlpha(UNSELECTED_BUTTON_ALPHA);
				break;
			case FAN_DIRECTION_FLOOR_DEFROSTER:
				directionButton1.setImageDrawable(mFanMap.get(directionButton1).second);
				directionButton1.setAlpha(UNSELECTED_BUTTON_ALPHA);
				directionButton2.setImageDrawable(mFanMap.get(directionButton2).second);
				directionButton2.setAlpha(UNSELECTED_BUTTON_ALPHA);
				directionButton3.setImageDrawable(mFanMap.get(directionButton3).second);
				directionButton3.setAlpha(UNSELECTED_BUTTON_ALPHA);
				directionButton4.setImageDrawable(mFanMap.get(directionButton4).first);
				directionButton4.setAlpha(SELECTED_BUTTON_ALPHA);
				break;
			default:
				resetFanToOff();
		}
	}	

    private final OnClickListener mFanDirectionClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            resetFanToOff();
            v.setAlpha(SELECTED_BUTTON_ALPHA);
            if (mFanMap.containsKey(v)) {
                ((ImageView) v).setImageDrawable(mFanMap.get(v).first);
                v.setAlpha(SELECTED_BUTTON_ALPHA);
                @FanDirection int direction = mControlMap.get(v);
                mListener.onFanDirectionClicked(direction);
            }
        }
    };

    private void resetFanToOff() {
        for (Map.Entry<ImageView, Pair<Drawable, Drawable>> entry : mFanMap.entrySet()) {
            ImageView button = entry.getKey();
            button.setImageDrawable(entry.getValue().second);
            button.setAlpha(UNSELECTED_BUTTON_ALPHA);
        }
    }
}
