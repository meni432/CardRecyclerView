package com.menisamet.cardrecyclerview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * Created by meni on 20/10/16.
 */

public class CardsRecyclerView extends RecyclerView {

    public static final int DEFAULT_DURATION = 400;
    public static final boolean DEFAULT_FIRST_ONLY = false;
    public static final int RANDOM_COLOR = -2;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int DEFAULT_COLOR = Color.GRAY;
    public static final int DEFAULT_NUMBER_LINES = 3;
    public static final int DEFAULT_ORIENTATION = HORIZONTAL;

    private boolean mFirstOnly = DEFAULT_FIRST_ONLY;
    private int mDuration = DEFAULT_DURATION;

    private Context mContext;

    CardClickListener mCardClickListener;
    CardLongClickListener mCardLongClickListener;

    private Random mRandom = new Random();

    public CardsRecyclerView(Context context) {
        super(context);
        initial(context);
    }

    public CardsRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initial(context);
    }

    public CardsRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initial(context);
    }

    public void initial(Context context) {

        if (context == null){
            throw new NullPointerException("Context must be set in CardRecycleView");
        }

        this.mContext = context;
        updateLayoutManager(DEFAULT_NUMBER_LINES, DEFAULT_ORIENTATION);
        RecyclerItemClickListener listener = new RecyclerItemClickListener(mContext);
        listener.setOnClickListener(new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mCardClickListener.onCardClick(view, position);
            }
        });
        listener.setOnLongClickListener(new RecyclerItemClickListener.OnItemLongCLickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mCardLongClickListener.onCardLongCLick(view, position);
            }
        });
        super.addOnItemTouchListener(listener);
    }

    private void updateLayoutManager(int numberOfLines, int orientation){
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(numberOfLines, orientation);
        super.setLayoutManager(gridLayoutManager);
    }

    public void setNumLinesAndOrientation(int numberOfLInes, int orientation){
        updateLayoutManager(numberOfLInes, orientation);
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public void setFirstOnly(boolean firstOnly) {
        this.mFirstOnly = firstOnly;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(adapter);
        scaleInAnimationAdapter.setDuration(mDuration);
        scaleInAnimationAdapter.setFirstOnly(mFirstOnly);
        super.setAdapter(scaleInAnimationAdapter);
    }

    public void setCardClickListener(CardClickListener cardClickListener) {
        this.mCardClickListener = cardClickListener;
    }

    public void setCardLongClickListener(CardLongClickListener cardLongClickListener) {
        this.mCardLongClickListener = cardLongClickListener;
    }

    public static class RecycleViewCardAdapter<T extends CardElement> extends RecyclerView.Adapter<RecycleViewCardAdapter.CardViewHolder> {

        private List<T> elementList;


        /**
         * set card icon for all cards.
         * if card have other icon it will override the global
         */
        private int imageGlobalRecurse;


        /**
         * set card color for all cards.
         * if card have other color it will override the global
         */
        private int cardGlobalColor;

        public RecycleViewCardAdapter(List<T> elementList) {
            this.elementList = elementList;
        }

        public void setImageGlobalRecurse(int imageGlobalRecurse) {
            this.imageGlobalRecurse = imageGlobalRecurse;
        }

        public void setCardGlobalColor(int cardGlobalColor) {
            this.cardGlobalColor = cardGlobalColor;
        }

        @Override
        public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
            CardViewHolder viewHolder = new CardViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(CardViewHolder holder, int position) {
            CardElement element = elementList.get(position);
            holder.mTextView.setText(element.getText());
            // set bg color
            if (element.getColor() != 0) {
                holder.mCardView.setCardBackgroundColor(element.getColor());
            } else if (cardGlobalColor == RANDOM_COLOR) {
                holder.mCardView.setCardBackgroundColor(getRandomHSVColor());
            } else if (cardGlobalColor != 0) {
                holder.mCardView.setCardBackgroundColor(cardGlobalColor);
            } else {
                holder.mCardView.setCardBackgroundColor(DEFAULT_COLOR);
            }

            // set icon image
            if (element.getImageViewRecurse() != 0) {
                holder.mImageView.setImageResource(element.getImageViewRecurse());
            } else if (imageGlobalRecurse != 0) {
                holder.mImageView.setImageResource(imageGlobalRecurse);
            }

            holder.mImageView.setColorFilter(Color.WHITE);

        }

        @Override
        public int getItemCount() {
            return elementList.size();
        }

        public static class CardViewHolder extends RecyclerView.ViewHolder {
            protected TextView mTextView;
            protected CardView mCardView;
            protected ImageView mImageView;

            public CardViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.item_text);
                mCardView = (CardView) itemView.findViewById(R.id.card_view);
                mImageView = (ImageView) itemView.findViewById(R.id.image_view);
            }
        }
    }

    public static int getRandomHSVColor() {
        int[] colorSet = {0xFFF44336, 0xFFE91E63, 0xFF673AB7, 0xFF3F51B5, 0xFF2196F3, 0xFF03A9F4, 0xFF009688, 0xFFFF5722, 0xFF795548};
        int random = (int)(Math.random()*colorSet.length);
        return colorSet[random];
    }

    public interface CardClickListener {
        public void onCardClick(View view, int position);
    }

    public interface CardLongClickListener{
        public void onCardLongCLick(View view, int position);
    }

    public interface CardElement {
        /**
         * @return background color for card
         */
        public int getColor();

        /**
         * @return text to show in card
         */
        public String getText();

        /**
         * @return image icon recurse for card
         */
        public int getImageViewRecurse();
    }

}