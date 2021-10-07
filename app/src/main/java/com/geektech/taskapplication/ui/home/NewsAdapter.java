package com.geektech.taskapplication.ui.home;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.taskapplication.R;
import com.geektech.taskapplication.databinding.ListNewsBinding;
import com.geektech.taskapplication.ui.models.News;
import com.google.android.material.timepicker.TimeFormat;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<News> list = new ArrayList<>();
    private OnItemClickListener listener;

    ListNewsBinding binding;

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news,parent,false);
        //return new ViewHolder(view);
        return new ViewHolder(ListNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull @NotNull NewsAdapter.ViewHolder holder, int position) {
        holder.onBind(list.get(position));

        if (position % 2 == 0) {
            holder.itemView.setBackgroundResource(R.color.teal_200);
        } else {
            holder.itemView.setBackgroundResource(R.color.white);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);

    }

    public void addItem(News news) {
        list.add(0, news);
        //notifyDataSetChanged();
        notifyItemInserted(0);


    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public News getItem(int position) {
        return list.get(position);
    }

    public void updateItem(News news, int position) {
        list.set(position, news);
        notifyItemChanged(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull ListNewsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void onBind(News news) {
            String time = new SimpleDateFormat("HH:mm:ss").format(news.getTime());
            binding.textTitle.setText(news.getTitle());
            binding.timeTitle.setText(time);

            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongClick(getAdapterPosition());
                    return true;
                }
            });

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition());
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }
}
