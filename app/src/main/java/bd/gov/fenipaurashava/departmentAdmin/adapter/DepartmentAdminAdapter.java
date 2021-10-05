package bd.gov.fenipaurashava.departmentAdmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.departmentUser.model.Department;


public class DepartmentAdminAdapter extends RecyclerView.Adapter<DepartmentAdminAdapter.ViewHolder> {

    private Context context;
    private List<Department> list = new ArrayList<>();
    String id;

    private RecyclerViewClickListener mListener;


    public DepartmentAdminAdapter(Context context, List<Department> list, RecyclerViewClickListener listener) {
        this.context = context;
        this.list = list;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.department_item_layout,
                        parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Department suj = list.get(position);

        id = suj.getId();
        holder.departmentTV.setText(suj.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView departmentTV;
        private ImageView moreIV, deleteIV, updateIV;

        private RecyclerViewClickListener mListener;
        private LinearLayout mRowContainer;

        public ViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);

            departmentTV = itemView.findViewById(R.id.departmentTV);

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
