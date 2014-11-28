package org.aliensource.symptommanagement.android.doctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.aliensource.symptommanagement.android.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ttruong on 28-Nov-14.
 */
public class MedicationAdapter extends ArrayAdapter<String> {

    private LayoutInflater mInflater;
    private String[] data;
    private List<MedicationItemViewHolder> holders;
    private DoctorMedicationFragment doctorMedicationFragment;

    public MedicationAdapter(Context context, int resource, String[] data, DoctorMedicationFragment doctorMedicationFragment) {
        super(context, resource, data);
        mInflater = LayoutInflater.from(context);
        this.data = data;
        holders = new ArrayList<MedicationItemViewHolder>(data.length);
        this.doctorMedicationFragment = doctorMedicationFragment;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MedicationItemViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.fragment_doctor_medication_list_item, null);

            holder = new MedicationItemViewHolder();
            holders.add(position, holder);
            holder.textView = (TextView) convertView.findViewById(android.R.id.text1);
            holder.textView.setText(data[position]);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.doctorMedicationCheckBox);
            holder.position = position;
            //store holder in tag
            convertView.setTag(holder);
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    holder.checkBox.setChecked(!holder.checkBox.isChecked());
                    doctorMedicationFragment.updateSelection(holder.position);
                }
            });
        }
        return convertView;
    }

    public boolean invertCheckBox(int position) {
        MedicationItemViewHolder holder = holders.get(position);
        holder.checkBox.setChecked(!holder.checkBox.isChecked());
        return holder.checkBox.isChecked();
    }

}

