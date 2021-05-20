package bd.gov.fenipaurashava.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import bd.gov.fenipaurashava.ComplainDivisionsSpinnerItem3;
import bd.gov.fenipaurashava.R;

import java.util.ArrayList;

public class ComplainDivisionsSpinnerAdapter3 extends ArrayAdapter<ComplainDivisionsSpinnerItem3> {

    public ComplainDivisionsSpinnerAdapter3(@NonNull Context context, ArrayList<ComplainDivisionsSpinnerItem3> items) {
        super(context, 0,items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.custom_spinner_item_layout, parent, false);
        }

        TextView itemTxt = convertView.findViewById(R.id.itemTxt);
        bd.gov.fenipaurashava.ComplainDivisionsSpinnerItem3 item = getItem(position);
        if (item != null) {
            itemTxt.setText(item.getItem());
        }
        return convertView;
    }
}
