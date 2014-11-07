package org.aliensource.symptommanagement.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment as part of the content frame in the {@link MainActivity}
 * Created by ttruong on 06-Nov-14.
 */
public abstract class AbstractFragment extends Fragment {

    protected static final String ARG_LAYOUT = "layout";

    public AbstractFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int layout = getArguments().getInt(ARG_LAYOUT);
        return inflater.inflate(layout, container, false);
    }
}
