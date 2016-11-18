package org.ucomplex.ucomplex.Modules.Events;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.ViewById;
import org.ucomplex.ucomplex.R;

/**
 * ---------------------------------------------------
 * Created by Sermilion on ${DTAE}.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
class EventViewHolder extends RecyclerView.ViewHolder {

   ImageView      eventsImageView;
   TextView       eventTextView;
   TextView       eventTime;
   TextView       eventPersonName;
   RelativeLayout eventDetailsLayout;
   Button         loadMoreEventsButton;

    EventViewHolder(View view){
        super(view);
        eventsImageView      = (ImageView) view.findViewById(R.id.list_events_item_image);
        eventTextView        = (TextView) view.findViewById(R.id.list_events_item_text);
        eventTime            = (TextView) view.findViewById(R.id.list_events_item_date);
        eventPersonName      = (TextView) view.findViewById(R.id.list_events_item_name);
        eventDetailsLayout   = (RelativeLayout) view.findViewById(R.id.event_details_layout);
        loadMoreEventsButton = (Button) view.findViewById(R.id.loadMoreEventsButton);
    }
}
