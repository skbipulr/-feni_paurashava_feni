package bd.gov.fenipaurashava.adapterForAPI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelForAppointmentSubjectFetchAllGET.Datum;

import java.util.ArrayList;
import java.util.List;

public class AppointmentFetchAllAdapter extends RecyclerView.Adapter<AppointmentFetchAllAdapter.ViewHolder> {

    private Context context;
    private List<Datum> list = new ArrayList<>();
    int id;

    private RecyclerViewClickListener mListener;

    private ApiInterface apiService;

    public AppointmentFetchAllAdapter(Context context, List<Datum> list, RecyclerViewClickListener listener) {
        this.context = context;
        this.list = list;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_appointment_item_show_row,
                        parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Datum suj = list.get(position);

        id = suj.getId();
        holder.titleTV.setText(suj.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleTV;
        private ImageView moreIV, deleteIV, updateIV;

        private RecyclerViewClickListener mListener;
        private RelativeLayout mRowContainer;

        public ViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.nameTitleTV);

            mRowContainer = itemView.findViewById(R.id.row_container);
            mListener = listener;
            mRowContainer.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.row_container) {
                mListener.onRowClick(mRowContainer, getAdapterPosition());
            }
        }
    }

    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);
    }

}
