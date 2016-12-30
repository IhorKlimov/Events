package com.myhexaville.events;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.myhexaville.events.databinding.ActivityEventsBinding;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static java.lang.Math.PI;

public class EventsActivity extends AppCompatActivity {
    private static final String LOG_TAG = "EventsActivity";
    private ActivityEventsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_events);

        loadImages();

        animateImages();

//        setupList();

        animateList();
    }

    private void setupList() {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        mBinding.list.setLayoutManager(layoutManager);
//        mBinding.list.setAdapter(new Adapter(this));
    }

    private void loadImages() {
        Glide.with(this)
                .load("http://pictures.ozy.com/pictures/900x900/2/9/8/92298_darthvaderf.jpg")
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.circle_diff)
                .into(new BitmapImageViewTarget(mBinding.profilePic) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        mBinding.profilePic.setImageDrawable(circularBitmapDrawable);
                    }
                });

        Glide.with(this)
                .load("http://cdn.movieweb.com/img.news.tops/NExMf86CqvZ8AD_1_b.jpg")
                .placeholder(R.color.placeholder)
                .into(mBinding.top1);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        setPointsPosition();
    }

    private void animateImages() {
        mBinding.profilePic
                .animate()
                .scaleX(1f)
                .scaleY(1f)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(400)
                .start();

        mBinding.top1.startAnimation(getSlideDownAnimation());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.profilePoints
                        .animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setInterpolator(new AccelerateDecelerateInterpolator())
                        .setDuration(400)
                        .start();
            }
        }, 300);
    }

    private void setPointsPosition() {
        int right = mBinding.profilePic.getRight();
        int left = mBinding.profilePic.getLeft();

        int centerX = (right - left) / 2 + left;
        int centerY = (int) (mBinding.profilePic.getHeight() / 2 + mBinding.profilePic.getY());
        int radius = right - centerX;

        double x = centerX + radius * Math.cos(PI / 4);
        double y = centerY - radius * Math.sin(PI / 4);

        mBinding.profilePoints.setX((float) x - mBinding.profilePoints.getHeight() / 2);
        mBinding.profilePoints.setY((float) y - mBinding.profilePoints.getHeight() / 2);
    }

    private void animateList() {
        final Animation slideDown = getSlideDownAnimationWithScale();
        mBinding.rowOne.setVisibility(INVISIBLE);
        mBinding.rowTwo.setVisibility(INVISIBLE);
        mBinding.rowThree.setVisibility(INVISIBLE);
        mBinding.rowFour.setVisibility(INVISIBLE);

        mBinding.secondTitle.startAnimation(slideDown);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.rowOne.startAnimation(getSlideDownAnimationWithScale());
                mBinding.rowOne.setVisibility(VISIBLE);
            }
        }, 25);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.rowTwo.startAnimation(getSlideDownAnimationWithScale());
                mBinding.rowTwo.setVisibility(VISIBLE);
            }
        }, 75);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.rowThree.startAnimation(getSlideDownAnimationWithScale());
                mBinding.rowThree.setVisibility(VISIBLE);
            }
        }, 125);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.rowFour.startAnimation(getSlideDownAnimationWithScale());
                mBinding.rowFour.setVisibility(VISIBLE);
            }
        }, 175);
    }

    private Animation getSlideDownAnimationWithScale() {
        return AnimationUtils.loadAnimation(EventsActivity.this, R.anim.slide_down_and_scale);
    }

    public Animation getSlideDownAnimation() {
        return AnimationUtils.loadAnimation(EventsActivity.this, R.anim.slide_down);
    }
}
