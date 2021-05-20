package bd.gov.fenipaurashava.adapterForAPI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.modelForEmployeeGET.Datum;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminCurdEmployeeAdapter extends RecyclerView.Adapter<AdminCurdEmployeeAdapter.ViewHolder> {

    private Context context;
    private List<Datum> employeeList = new ArrayList<>();
    private RecyclerViewCurdClickListener mListener;


    public AdminCurdEmployeeAdapter(Context context, List<Datum> employeeList, RecyclerViewCurdClickListener listener) {
        this.context = context;
        this.employeeList = employeeList;
        this.mListener = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_curd_employee_item_layout,
                        parent,false);
        return new ViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       Datum employee =  employeeList.get(position);
        String image = "http://apis.digiins.gov.bd/district_app/public/employee/"+employee.getPicture();

        Picasso.get().load("http://apis.digiins.gov.bd/district_app/public/employee/"+employee.getPicture()).placeholder(R.drawable.default_icon)
                .into(holder.profileIV);
       holder.nameTV.setText(employee.getName());
       holder.phoneNumberTV.setText(employee.getMobileNo());
       holder.mailTV.setText(employee.getEmail());
       holder.designationTV.setText(employee.getDesignation());



    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CircleImageView profileIV;
        TextView nameTV,phoneNumberTV,designationTV,mailTV;

        private RecyclerViewCurdClickListener mListener;
        private RelativeLayout mRowContainer;

        public ViewHolder(@NonNull View itemView, RecyclerViewCurdClickListener listener) {
            super(itemView);

            profileIV = itemView.findViewById(R.id.employeeImageIV);
            nameTV = itemView.findViewById(R.id.nameTV);
            phoneNumberTV = itemView.findViewById(R.id.phoneNumberTV);
            designationTV = itemView.findViewById(R.id.designationTV);
            mailTV = itemView.findViewById(R.id.emailTV);

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


    public interface RecyclerViewCurdClickListener {
        void onRowClick(View view, int position);
    }

}
