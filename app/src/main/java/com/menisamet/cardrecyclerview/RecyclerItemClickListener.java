package com.menisamet.cardrecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;
    private OnItemLongCLickListener mLongListener;

    @Nullable
    private View childView;

    private int childViewPosition;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public interface OnItemLongCLickListener {
        public void onItemClick(View view, int position);
    }

    GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context, OnItemClickListener listener){
        this(context);
        mListener = listener;
    }

    public RecyclerItemClickListener(Context context) {
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                mListener.onItemClick(childView, childViewPosition);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                mLongListener.onItemClick(childView, childViewPosition);
            }
        });
        mGestureDetector.setIsLongpressEnabled(true);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        this.childView = childView;
        this.childViewPosition = view.getChildAdapterPosition(childView);
        return (childView != null && mListener != null && mGestureDetector.onTouchEvent(e));
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public void setOnClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public void setOnLongClickListener(OnItemLongCLickListener mLongListener) {
        this.mLongListener = mLongListener;
    }
}