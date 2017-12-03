package com.followit.yigitozkavci.follow_it.adapters;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.followit.yigitozkavci.follow_it.R;
import com.followit.yigitozkavci.follow_it.models.Subscription;
import com.followit.yigitozkavci.follow_it.models.SubscriptionData;

import java.util.List;

/**
 * Created by yigitozkavci on 2.12.2017.
 */

public class SubscriptionDataList extends ArrayAdapter<String> {
    private final Activity context;
    private List<SubscriptionData> subscriptionData;

    public SubscriptionDataList(Activity context, List<SubscriptionData> subscriptionData) {
        super(context, R.layout.subscription_data_list_item_view, new String[subscriptionData.size()]);
        this.context = context;
        this.subscriptionData = subscriptionData;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.subscription_list_item_view, null, true);

        SubscriptionData subscriptionDatum = subscriptionData.get(position);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        txtTitle.setText(subscriptionDatum.getData());
        //txtTitle.setTextColor(subscriptionDatum.getIsNew() ? Color.RED : Color.BLACK);
        return rowView;
    }

    public SubscriptionData getItemAtPos(int position) {
        return subscriptionData.get(position);
    }
}
