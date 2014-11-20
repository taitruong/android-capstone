package org.aliensource.symptommanagement.android.checkin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.aliensource.symptommanagement.android.R;

/**
 * Created by ttruong on 20-Nov-14.
 */
public class TabbedContentFragment extends Fragment {

    public static final String ARG_SECTION_NUMBER = "section_number";

    public TabbedContentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabbed_content,
                container, false);
        TextView dummyTextView = (TextView) rootView
                .findViewById(R.id.section_label);
        dummyTextView.setText(Integer.toString(getArguments().getInt(
                ARG_SECTION_NUMBER)));
        return rootView;
    }
}
