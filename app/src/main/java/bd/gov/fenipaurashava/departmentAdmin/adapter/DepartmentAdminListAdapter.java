package bd.gov.fenipaurashava.departmentAdmin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.activity_admin.AdminCurdEmployeeActivity;
import bd.gov.fenipaurashava.departmentUser.model.Department;
import bd.gov.fenipaurashava.modelForEmployeeGET.Designation;


public class DepartmentAdminListAdapter extends RecyclerView.Adapter<DepartmentAdminListAdapter.ViewHolder> {

    private Context context;
    private List<Designation> list = new ArrayList<>();
    String id;

    public DepartmentAdminListAdapter(Context context, List<Designation> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.department_item_layout,
                        parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Designation department = list.get(position);

        id = department.getId();
        holder.departmentTV.setText(department.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, AdminCurdEmployeeActivity.class);
//                intent.putExtra("id",department.getId());
//                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {
        private TextView departmentTV;
        private ImageView moreIV, deleteIV, updateIV;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

          //  departmentTV = itemView.findViewById(R.id.departmentTV);

        }


    }



}
