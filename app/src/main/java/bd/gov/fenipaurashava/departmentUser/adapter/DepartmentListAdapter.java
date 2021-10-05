package bd.gov.fenipaurashava.departmentUser.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.activity_user.EmployeeActivity;
import bd.gov.fenipaurashava.departmentUser.model.Department;
import bd.gov.fenipaurashava.modelForEmployeeGET.Designation;

public class DepartmentListAdapter extends RecyclerView.Adapter<DepartmentListAdapter.ViewHolder> {

    private Context context;
    private List<Designation> departmentList;

    public DepartmentListAdapter(Context context, List<Designation> departmentList) {
        this.context = context;
        this.departmentList = departmentList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.department_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Designation department = departmentList.get(position);
        holder.departmentTV.setText(department.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EmployeeActivity.class);
                intent.putExtra("id",department.getId());
                intent.putExtra("departmentName",department.getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return departmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView departmentTV;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            departmentTV = itemView.findViewById(R.id.departmentTV);
        }
    }
}
