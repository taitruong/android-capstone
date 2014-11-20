package org.aliensource.symptommanagement.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Fragment as part of the content frame in the {@link MainActivity}
 * Created by ttruong on 06-Nov-14.
 */
public abstract class AbstractFragment<T extends View> extends Fragment {

    protected static final String ARG_LAYOUT = "layout";

    protected T fragmentView;

    public AbstractFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int layout = getArguments().getInt(ARG_LAYOUT);
        fragmentView = (T) inflater.inflate(layout, container, false);
        ButterKnife.inject(this, fragmentView);
        return fragmentView;
    }
}
