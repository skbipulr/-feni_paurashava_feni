package bd.gov.fenipaurashava.adapterForAPI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.modelForWrokScheduleFetchGET.Datum;

public class WorkScheduleFetchAdminAdapter extends RecyclerView.Adapter<WorkScheduleFetchAdminAdapter.ViewHolder> {

    private Context context;
    private List<Datum> scheduleList = new ArrayList<>();
    private RecyclerViewCurdClickListener mListener;

    public WorkScheduleFetchAdminAdapter(Context context, List<Datum> scheduleList, RecyclerViewCurdClickListener listener) {
        this.context = context;
        this.scheduleList = scheduleList;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_work_item,
                        parent,false);
        return new ViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       Datum data =  scheduleList.get(position);
       holder.subjectTitleTV.setText(data.getSubject());
     //  holder.scheduleDateTV.setText(data.getScheduleDate());
       holder.placeTV.setText(data.getPlace());

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

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, WorkScheduleDetailsActivity.class);
//                intent.putExtra("data", data);
//                context.startActivity(intent);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView subjectTitleTV,placeTV,scheduleDateTV;
        private RelativeLayout mRowContainer;
        private RecyclerViewCurdClickListener mListener;

        public ViewHolder(@NonNull View itemView, RecyclerViewCurdClickListener listener) {
            super(itemView);

            subjectTitleTV = itemView.findViewById(R.id.subjectTitleTV);
            placeTV = itemView.findViewById(R.id.placeTV);
            scheduleDateTV = itemView.findViewById(R.id.scheduleDateTV);
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
