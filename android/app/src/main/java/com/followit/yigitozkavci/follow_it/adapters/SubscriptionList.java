package com.followit.yigitozkavci.follow_it.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.followit.yigitozkavci.follow_it.R;
import com.followit.yigitozkavci.follow_it.models.Subscription;

import java.util.ArrayList;

/**
 * Created by yigitozkavci on 30.11.2017.
 */

public class SubscriptionList extends ArrayAdapter<String> {
    private final Activity context;
    private ArrayList<Subscription> subscriptions;

    public SubscriptionList(Activity context, ArrayList<Subscription> subscriptions) {
        super(context, R.layout.subscription_list_item_view, new String[subscriptions.size()]);
        this.context = context;
        this.subscriptions = subscriptions;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.subscription_list_item_view, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);

        txtTitle.setText(subscriptions.get(position).getUsername());

        imageView.setImageResource(subscriptions.get(position).getChannel().getImageId());
        return rowView;
    }
}