package com.example.parks.warm_phone_book;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by qwsdx on 2018-01-04.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    private ArrayList<PersonInfo> personInfos;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView cardView;

        public TextView nameTextView;
        public TextView phoneNumberTextView;

        public LinearLayout test;

        public ViewHolder(View v) {
            super(v);
            //this.cardView = (CardView)v.findViewById(R.id.card_view);
            this.nameTextView = (TextView)v.findViewById(R.id.minNameId);
            this.phoneNumberTextView = (TextView)v.findViewById(R.id.minPhoneNumberId);

            //this.test = (LinearLayout) v.findViewById(R.id.testId);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PersonAdapter(ArrayList<PersonInfo> personInfos, Context context) {
        this.personInfos = personInfos;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PersonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.min_member_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //...
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.nameTextView.setText(personInfos.get(position).getName());
        //holder.nameTextView.setTypeface(Typeface.createFromAsset(getAssets(), "SDMiSaeng.ttf"));
        holder.phoneNumberTextView.setText(personInfos.get(position).getPhoneNumber());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent memberLayoutIntent = new Intent(context, MemberLayoutActivity.class);
                    memberLayoutIntent.putExtra("PersonInfo", (Serializable) personInfos.get(position));
                    //context.startActivity(memberLayoutIntent);

                    //클릭시 다이얼로 전화번호 전송하는 코드 추후에 버튼생성시 넣을것.
                    //ClickCall(personInfoTemp.getPhoneNumber());
                    test(holder);
                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void test(ViewHolder holder) {
        //myView = findViewById(R.id.my_view);

        // get the center for the clipping circle
        int cx = (holder.test.getLeft() + holder.test.getRight()) / 2;
        int cy = (holder.test.getTop() + holder.test.getBottom()) / 2;

        // get the final radius for the clipping circle
        int finalRadius = Math.max(holder.test.getWidth(), holder.test.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(holder.test, cx, cy, 0, finalRadius);

        // make the view visible and start the animation
        holder.test.setVisibility(View.VISIBLE);
        anim.start();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return personInfos.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}