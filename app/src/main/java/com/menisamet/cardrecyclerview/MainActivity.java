package com.menisamet.cardrecyclerview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "TAG_" + MainActivity.class.getCanonicalName();

    CardsRecyclerView mRecyclerView;
    CardsRecyclerView.RecycleViewCardAdapter mRAdapter;
    List<CardsRecyclerView.CardElement> list;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = getTestCards(100);

        mRecyclerView = (CardsRecyclerView)findViewById(R.id.recycle_view);
        mRecyclerView.setNumLinesAndOrientation(5, CardsRecyclerView.HORIZONTAL);
        mRAdapter = new CardsRecyclerView.RecycleViewCardAdapter(list);
        mRAdapter.setImageGlobalRecurse(R.drawable.minus_circle);
        mRAdapter.setCardGlobalColor(CardsRecyclerView.RANDOM_COLOR);
        mRecyclerView.setAdapter(mRAdapter);
        mRecyclerView.setCardClickListener(new CardsRecyclerView.CardClickListener() {
            @Override
            public void onCardClick(View view, int position) {
                list.remove(position);
                mRAdapter.notifyItemRemoved(position);
            }
        });
        mRecyclerView.setCardLongClickListener(new CardsRecyclerView.CardLongClickListener() {
            @Override
            public void onCardLongCLick(View view, int position) {
                Log.d(TAG, "long click event");
            }
        });


    }

    public List<CardsRecyclerView.CardElement> getTestCards(int numberOfCards){
        List<CardsRecyclerView.CardElement> list = new ArrayList<>();
        for (int i = 0; i < numberOfCards; i++){
            StringBuilder stringBuilder = new StringBuilder();
            int rand = (int)(Math.random()*10);
            for (int j = 0; j < rand; j++){
                stringBuilder.append(' ');
            }
            stringBuilder.append("card " + i);
            for (int j = 0; j < rand; j++){
                stringBuilder.append(' ');
            }
            list.add(new Person(stringBuilder.toString()));
        }
        return list;
    }

    class Person implements CardsRecyclerView.CardElement {

        String name;

        public Person(String name) {
            this.name = name;
        }

        @Override
        public int getColor() {
            return 0;
        }

        @Override
        public String getText() {
            return name;
        }

        @Override
        public int getImageViewRecurse() {
            return 0;
        }
    }
}