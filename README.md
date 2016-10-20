# CardsRecyclerView
## Cards Recycler View
CardsRecyclerView is very easy to use Android ReycleView to create custom card items.
With this Class you can create in very easy way Recycler view for Android Applications.

![alt text](https://github.com/meni432/CardRecyclerView/blob/master/cardrecycle.gif "Example GIF")

**Gradle soon!**

--

##Setup And usage
*without Gradle*
#### Step 1: Add to your Gradle dependencies

```
    compile 'com.android.support:appcompat-v7:24.2.1'
    compile 'com.android.support:recyclerview-v7:24.2.0'
    compile 'com.android.support:cardview-v7:24.0.+'
    compile 'jp.wasabeef:recyclerview-animators:2.2.3'
```

#### Step 2: Add relevant classes to your project

### Step 3: In your layout add RecyclerView Componnent
```xml
    <com.menisamet.cardrecyclerview.CardsRecyclerView
        android:id="@+id/recycle_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentLeft="true"
        android:layout_alignParentStart="true"
        android:layout_below="@+id/textView"
        android:background="@android:color/transparent">
    </com.menisamet.cardrecyclerview.CardsRecyclerView>
```
this is standart Recycler View

#### Step 4: This is a sample code for using with this Class
```Java
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
```

Notice that there is a build in function for remove and insert new object (As you can see in the sample upward).

#### Test Cards Sample
*Person implements CardElement*
```Java
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
```

Meni Samet.

meni432@gmail.com
