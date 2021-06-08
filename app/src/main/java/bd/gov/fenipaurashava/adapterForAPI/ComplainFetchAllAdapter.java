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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.activity_admin.AdminEmployeeActivityForComplainRef;
import bd.gov.fenipaurashava.activity_admin.ComplainSubjectEditorActivity;
import bd.gov.fenipaurashava.activity_admin.SendMessageForComplainActivity;
import bd.gov.fenipaurashava.common.Common;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelComplainDeletePOST.ComplainDeleteResponse;
import bd.gov.fenipaurashava.modelForComplainFechAllGET.Datum;
import bd.gov.fenipaurashava.webApi.RetrofitClient;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComplainFetchAllAdapter extends RecyclerView.Adapter<ComplainFetchAllAdapter.ViewHolder> {

    private Context context;
    List<Datum> list = new ArrayList<>();

    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;
    public static final String USER_ID = "USER_ID";
    public static final String EMPLOYEE_ID = "EMPLOYEE_ID";

    private ApiInterface apiService;

    public ComplainFetchAllAdapter(Context context, List<Datum> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.complain_fetch_all_item,
                        parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Datum data = list.get(position);

        holder.nameOneTV.setText(data.getComplainantName());
        holder.nameTwoTV.setText(data.getDefendantName());
        holder.addressOneTV.setText(data.getComplainantAddress());
        holder.addressTwoTV.setText(data.getDefendantAddress());
        holder.mobileNumberTV.setText(data.getMobileNo());
        holder.complainDetailsTV.setText(data.getComplain());

        String photo = "http://fenimayor.digiins.gov.bd/district_app/public/complain/" + data.getPicture();

        Picasso.get().load(photo).placeholder(R.drawable.placeholder).into(holder.imageViewIV);

        holder.sendMessageIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SendMessageForComplainActivity.class);
                intent.putExtra("info", data);
                context.startActivity(intent);
            }
        });

        holder.deleteIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setMessage("Delete this item?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
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

                Intent intent = new Intent(context, AdminEmployeeActivityForComplainRef.class);
                intent.putExtra("allInfo", data);
                context.startActivity(intent);
            }
        });
    }


    private void deleteData(final int id) {

        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String user_id = sharedpreferences.getString(USER_ID, "");
        String employee_id = sharedpreferences.getString(EMPLOYEE_ID, "");

        int use_id = Integer.parseInt(user_id);
        int em_id = Integer.parseInt(employee_id);

        if (use_id == Common.ADMIN_USER_ID) {
            apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Deleting...");
            progressDialog.show();

            apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);

            apiService.setComplainDeleteResponse(Common.APP_KEY, id, user_id).enqueue(new Callback<ComplainDeleteResponse>() {
                @Override
                public void onResponse(Call<ComplainDeleteResponse> call, Response<ComplainDeleteResponse> response) {

                    if (response.code() == 200) {
                        Toast.makeText(context, "" + response.body().getMessage() + "", Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(context, "problem", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ComplainDeleteResponse> call, Throwable t) {

                    // Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });

            progressDialog.dismiss();
        } else {
            Toast.makeText(context, "Your are not authorize person", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView sendMessageIV, deleteIV, referringIV, imageViewIV;
        private TextView nameOneTV, nameTwoTV, addressOneTV, addressTwoTV,
                complainDetailsTV, mobileNumberTV, typeOfComplainTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameOneTV = itemView.findViewById(R.id.nameOneTV);
            nameTwoTV = itemView.findViewById(R.id.nameTwoTV);
            addressOneTV = itemView.findViewById(R.id.addressOneTV);
            addressTwoTV = itemView.findViewById(R.id.addressTwoTV);
            complainDetailsTV = itemView.findViewById(R.id.complainDetailsTV);
            mobileNumberTV = itemView.findViewById(R.id.mobileNumberTV);
            //  typeOfComplainTV = itemView.findViewById(R.id.typeOfComplainTV);


            sendMessageIV = itemView.findViewById(R.id.sendMessageIV);
            deleteIV = itemView.findViewById(R.id.deleteIV);
            referringIV = itemView.findViewById(R.id.referringIV);
            imageViewIV = itemView.findViewById(R.id.imageViewIV);

        }
    }
}
