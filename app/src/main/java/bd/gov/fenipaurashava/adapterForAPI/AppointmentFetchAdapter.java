package bd.gov.fenipaurashava.adapterForAPI;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.activity_admin.AdminEmployeeForAppoinmentActivity;
import bd.gov.fenipaurashava.activity_admin.SendMessageForAppintmentActivity;
import bd.gov.fenipaurashava.common.Common;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelForAppointmentDeletePOST.AppointmentDeleteResponse;
import bd.gov.fenipaurashava.modelForAppointmentFetchGET.Datum;
import bd.gov.fenipaurashava.webApi.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentFetchAdapter extends RecyclerView.Adapter<AppointmentFetchAdapter.ViewHolder> {

    private Context context;
    private List<Datum> appointmentList = new ArrayList<>();
    private ApiInterface apiService;


    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;
    public static final String USER_ID = "USER_ID";
    public static final String EMPLOYEE_ID = "EMPLOYEE_ID";

    public AppointmentFetchAdapter(Context context, List<Datum> appointmentList) {
        this.context = context;
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fetch_appointment_item_layout,
                        parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Datum data = appointmentList.get(position);

        holder.nameTV.setText(data.getName());
        holder.mobileNoTV.setText(data.getMobileNo());
        holder.addressTV.setText(data.getAddress());
        holder.dateTV.setText(data.getAppointmentDate());
        holder.descriptionTV.setText(data.getDescription());
        holder.subjectTV.setText(data.getSubjectName());


        holder.sendMessageIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SendMessageForAppintmentActivity.class);

                intent.putExtra("info", data);
                context.startActivity(intent);
            }
        });

        holder.deleteIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setMessage("Are you delete this item?");
                dialog.setPositiveButton("Yes" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData(data.getId());
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        holder.referringIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AdminEmployeeForAppoinmentActivity.class);
                intent.putExtra("allInfo", data);
                context.startActivity(intent);
            }
        });

    }

    private void deleteData( final int id) {

        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String user_id = sharedpreferences.getString(USER_ID,"");
        String employee_id = sharedpreferences.getString(EMPLOYEE_ID,"");

        int use_id = Integer.parseInt(user_id);
        int em_id = Integer.parseInt(employee_id);
        if (use_id == Common.ADMIN_USER_ID){

            apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Deleting...");
            progressDialog.show();
            //readMode();
            //delete operation

            apiService.appointmentDeleteResponse(Common.APP_KEY, id, user_id).enqueue(new Callback<AppointmentDeleteResponse>() {
                @Override
                public void onResponse(Call<AppointmentDeleteResponse> call, Response<AppointmentDeleteResponse> response) {

                    if (response.code() == 200) {
                        Toast.makeText(context, "" + response.body().getMessage()+"", Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();
                    }else {
                        Toast.makeText(context, "problem", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<AppointmentDeleteResponse> call, Throwable t) {
                    Log.d("error: ",t.getMessage());
                    progressDialog.dismiss();
                }
            });

            progressDialog.dismiss();

        }else {
            Toast.makeText(context, "Your are not authorize person", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView sendMessageIV,deleteIV,referringIV;
        private TextView nameTV,mobileNoTV,addressTV,dateTV,descriptionTV,subjectTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.nameTV);
            mobileNoTV = itemView.findViewById(R.id.mobileNoTV);
            addressTV = itemView.findViewById(R.id.addressTV);
            dateTV = itemView.findViewById(R.id.dateTV);
            descriptionTV = itemView.findViewById(R.id.descriptionTV);
            subjectTV = itemView.findViewById(R.id.subjectTV);

            sendMessageIV = itemView.findViewById(R.id.sendMessageIV);
            deleteIV = itemView.findViewById(R.id.deleteIV);
            referringIV = itemView.findViewById(R.id.referringIV);
        }
    }
}
