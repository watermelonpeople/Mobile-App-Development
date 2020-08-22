package edu.neu.madcourse.groupproject.gamefragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.neu.madcourse.groupproject.R;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.MyViewHolder> {

    private List<ModalClass> mList;
    private Context context;
    private OnItemListener mOnItemListener;

    public WorkAdapter(List<ModalClass> list, Context context, OnItemListener onItemListener) {
        this.mList = list;
        this.context = context;
        this.mOnItemListener = onItemListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_view_work, parent, false);

        return new MyViewHolder(view, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkAdapter.MyViewHolder holder, int position) {

        holder.imageView.setImageResource(mList.get(position).getImage());
        holder.textView.setText(mList.get(position).getText());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        TextView textView;
        Button workButton;
        OnItemListener onItemListener;


        public MyViewHolder(View itemView, final OnItemListener onItemListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.workImageId);
            textView = itemView.findViewById(R.id.workTextViewId);
            workButton = itemView.findViewById(R.id.workButton);
            this.onItemListener = onItemListener;

            //"Proper" way to setOnClickListener for the card.
            itemView.setOnClickListener(this);

            //"Improvised" way to setOnClickListener for an item inside the card.
            //I don't think we can do buyButton.setOnClickListener(this) because there is
            //already an onClick method set to itemView.
            workButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemListener.onButtonClick(position);
                        }
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    //Interface for allowing clicks in the fragment.
    public interface OnItemListener {
        void onItemClick(int position);
        void onButtonClick(int position);
    }
}

