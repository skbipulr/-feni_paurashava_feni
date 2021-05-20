package bd.gov.fenipaurashava.adapterForAPI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.activity_user.WorkScheduleDetailsActivity;
import bd.gov.fenipaurashava.modelForWrokScheduleFetchGET.Datum;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkScheduleFetchAdapter extends RecyclerView.Adapter<WorkScheduleFetchAdapter.ViewHolder> {

    private Context context;
    private List<Datum> scheduleList = new ArrayList<>();

    public WorkScheduleFetchAdapter(Context context, List<Datum> scheduleList) {
        this.context = context;
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_work_item,
                        parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       Datum data =  scheduleList.get(position);
       holder.subjectTitleTV.setText(data.getSubject());
       holder.scheduleDateTV.setText(data.getScheduleDate());
       holder.placeTV.setText(data.getPlace());

       holder.eventImageTV.setImageResource(data.getImage());

        String dtStart = data.getScheduleDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date date = format.parse(dtStart);

            DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy  hh:mm a");
            String strDate = dateFormat.format(date);
            holder.scheduleDateTV.setText(strDate);
            //  System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WorkScheduleDetailsActivity.class);
                intent.putExtra("data", data);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView subjectTitleTV,placeTV,scheduleDateTV;
        ImageView eventImageTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            subjectTitleTV = itemView.findViewById(R.id.subjectTitleTV);
            placeTV = itemView.findViewById(R.id.placeTV);
            scheduleDateTV = itemView.findViewById(R.id.scheduleDateTV);
            eventImageTV = itemView.findViewById(R.id.eventImageTV);
        }
    }
}
