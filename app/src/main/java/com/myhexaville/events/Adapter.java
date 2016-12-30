/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.myhexaville.events;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class Adapter extends RecyclerView.Adapter<Holder> {
    private Context mContext;
    private boolean mHasShowedAnimation;
    private boolean mHasStartedCountDown;
    private int[] mCircles = new int[]{
            R.drawable.circle_orange,
            R.drawable.circle_red,
            R.drawable.circle_teal,
            R.drawable.circle_blue,
            R.drawable.circle_yellow
    };

    public Adapter(Context c) {
        mContext = c;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = DataBindingUtil
                .inflate(LayoutInflater
                        .from(mContext), R.layout.list_item, parent, false)
                .getRoot();
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        holder.mBinding.number.setText(String.valueOf(position + 1));
        holder.mBinding.circle.setBackgroundResource(mCircles[(int) (Math.random() * mCircles.length)]);

        if (!mHasShowedAnimation) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    holder.mBinding.root.startAnimation(getSlideDownAnimation());
                }
            }, position * 50);

            if (!mHasStartedCountDown) {
                mHasStartedCountDown = true;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mHasShowedAnimation = true;
                    }
                }, 400);
            }
        }

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    private Animation getSlideDownAnimation() {
        return AnimationUtils.loadAnimation(mContext, R.anim.slide_down_and_scale);
    }
}
