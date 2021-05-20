package bd.gov.fenipaurashava.adapterForAPI;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.activity_admin.AdminEmployeeActivity;
import bd.gov.fenipaurashava.activity_user.PublicHearingDetailsActivity;
import bd.gov.fenipaurashava.activity_user.SendMessageActivity;
import bd.gov.fenipaurashava.common.Common;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelForPublicHearingDeletePOST.SetPublicHearingDeleteResponse;
import bd.gov.fenipaurashava.modelPublicHeadingGET.Datum;
import bd.gov.fenipaurashava.webApi.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicHeadingAdapter extends RecyclerView.Adapter<PublicHeadingAdapter.ViewHolder> {

    private Context context;
    private List<Datum> publicHearingList = new ArrayList<>();

    private ApiInterface apiService;

    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;
    public static final String USER_ID = "USER_ID";
    public static final String EMPLOYEE_ID = "EMPLOYEE_ID";

    public PublicHeadingAdapter(Context context, List<Datum> publicHearingList) {
        this.context = context;
        this.publicHearingList = publicHearingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.public_hearing_item_layout,
                        parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Datum info = publicHearingList.get(position);
        holder.nameTV.setText(info.getName());
        holder.mobileTV.setText(info.getMobileNo());
        holder.addressTV.setText(info.getAddress());
        holder.titleTV.setText(info.getSubject());
        holder.descriptionTV.setText(info.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PublicHearingDetailsActivity.class);
                intent.putExtra("data", info);
                context.startActivity(intent);
            }
        });


        holder.sendMessageIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SendMessageActivity.class);
                intent.putExtra("info", info);
                context.startActivity(intent);
            }
        });

        holder.deleteIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setMessage("Delete this item?");
                dialog.setPositiveButton("Yes" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData(info.getId());
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

                Intent intent = new Intent(context, AdminEmployeeActivity.class);
                intent.putExtra("allInfo", info);
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

            apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);

            apiService.setPublicHearingDeleteResponse(Common.APP_KEY, id, user_id).enqueue(new Callback<SetPublicHearingDeleteResponse>() {
                @Override
                public void onResponse(Call<SetPublicHearingDeleteResponse> call, Response<SetPublicHearingDeleteResponse> response) {

                    if (response.code() == 200) {
                        Toast.makeText(context, "" + response.body().getMessage()+"", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "problem", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<SetPublicHearingDeleteResponse> call, Throwable t) {

                    // Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
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
        return publicHearingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV,mobileTV,addressTV,titleTV,descriptionTV;
        ImageView sendMessageIV,deleteIV,referringIV;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.nameTV);
            mobileTV = itemView.findViewById(R.id.mobileTV);
            addressTV = itemView.findViewById(R.id.addresTV);
            titleTV = itemView.findViewById(R.id.titleTV);
            descriptionTV = itemView.findViewById(R.id.descriptionTV);
            sendMessageIV = itemView.findViewById(R.id.sendMessageIV);
            deleteIV = itemView.findViewById(R.id.deleteIV);
            referringIV = itemView.findViewById(R.id.referringIV);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
