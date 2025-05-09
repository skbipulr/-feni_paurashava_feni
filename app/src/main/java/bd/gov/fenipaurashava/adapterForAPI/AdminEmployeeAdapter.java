package bd.gov.fenipaurashava.adapterForAPI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.common.Common;
import bd.gov.fenipaurashava.modelForEmployeeGET.Datum;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminEmployeeAdapter extends RecyclerView.Adapter<AdminEmployeeAdapter.ViewHolder> {

    private Context context;
    private List<Datum> employeeList = new ArrayList<>();
    private OnEmployeeItemClickListener listener;

    public AdminEmployeeAdapter(Context context, List<Datum> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_item_layout,
                        parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       Datum employee =  employeeList.get(position);
        String image = "http://fenimayor.digiins.gov.bd/district_app/public/employee/"+employee.getPhoto();

        Picasso.get().load(Common.IMAGE_BASE_URL + employee.getPhoto()).placeholder(R.drawable.default_icon)
                .into(holder.profileIV);
       holder.nameTV.setText(employee.getName());
       holder.phoneNumberTV.setText(employee.getMobile());
       holder.mailTV.setText(String.valueOf(employee.getEmail()));
       holder.designationTV.setText(employee.getDesignationName());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (listener != null) {
//                    listener.onEmployeeItemClick(employee);
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profileIV;
        TextView nameTV,phoneNumberTV,designationTV,mailTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileIV = itemView.findViewById(R.id.employeeImageIV);
            nameTV = itemView.findViewById(R.id.nameTV);
            phoneNumberTV = itemView.findViewById(R.id.phoneNumberTV);
            designationTV = itemView.findViewById(R.id.designationTV);
            mailTV = itemView.findViewById(R.id.emailTV);
        }
    }

    public interface OnEmployeeItemClickListener {
        void onEmployeeItemClick(Datum employee);
    }
}
