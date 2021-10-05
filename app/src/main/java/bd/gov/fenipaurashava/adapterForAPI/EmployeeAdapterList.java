package bd.gov.fenipaurashava.adapterForAPI;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.activity_user.ProfileActivity;
import bd.gov.fenipaurashava.modelForEmployeeGET.Datum;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployeeAdapterList extends RecyclerView.Adapter<EmployeeAdapterList.ViewHolder> {

    private static final int REQUEST_CALL = 1;
    private Context context;
    private List<Datum> employeeList = new ArrayList<>();

    public EmployeeAdapterList(Context context, List<Datum> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_item_layout,
                        parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Datum employee = employeeList.get(position);
        Picasso.get().load("http://fenimayor.digiins.gov.bd/district_app/public/employee/" + employee.getPhoto()).placeholder(R.drawable.default_icon)
                .into(holder.profileIV);
        holder.nameTV.setText(employee.getName());
        holder.phoneNumberTV.setText(employee.getMobile());
        holder.mailTV.setText(String.valueOf(employee.getEmail()));
        holder.designationTV.setText(employee.getDesignationName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("employee", employee);
                context.startActivity(intent);
            }
        });

        holder.callLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callButton(employee.getMobile());
            }
        });

    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profileIV;
        TextView nameTV, phoneNumberTV, designationTV, mailTV;
        LinearLayout callLL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileIV = itemView.findViewById(R.id.employeeImageIV);
            nameTV = itemView.findViewById(R.id.nameTV);
            phoneNumberTV = itemView.findViewById(R.id.phoneNumberTV);
            designationTV = itemView.findViewById(R.id.designationTV);
            mailTV = itemView.findViewById(R.id.emailTV);
            callLL = itemView.findViewById(R.id.callLL);
        }
    }

    private void callButton(String mobileNumber) {
        if (mobileNumber.length() > 0) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dail = "tel:" + mobileNumber;
                context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dail)));
            }
        }
    }
}
