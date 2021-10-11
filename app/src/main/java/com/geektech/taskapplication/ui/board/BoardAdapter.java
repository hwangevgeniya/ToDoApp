package com.geektech.taskapplication.ui.board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.taskapplication.R;

import org.jetbrains.annotations.NotNull;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private onClickBtns onClickBtns;
    private String[] titles = new String[]{"Fast", "Free", "Powerful", "Secure"};
    private int[] images = new int[]{R.drawable.fast_png, R.drawable.free_png, R.drawable.powerful_png, R.drawable.secure};


    public void setOnClickBtns(BoardAdapter.onClickBtns onClickBtns) {
        this.onClickBtns = onClickBtns;
    }
    //private OnItemClickListener listener;

    @Override
    public BoardAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_board, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemCount() {
        return titles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitle;
        private ImageView imageView;
        private Button btnStart;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            imageView = itemView.findViewById(R.id.imageView);
//            btnSkip = itemView.findViewById(R.id.btnSkip);
            btnStart = itemView.findViewById(R.id.btnStart);
        }

        public void onBind(int position) {
            textTitle.setText(titles[position]);
            imageView.setImageResource(images[position]);

            if (position == images.length - 1) {
                btnStart.setVisibility(View.VISIBLE);

                btnStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textTitle.setText("HOHOHOHO");
                        onClickBtns.startFragment();
                    }
                });

//                btnSkip.setVisibility(View.INVISIBLE);
            } else {
                btnStart.setVisibility(View.INVISIBLE);
//                btnSkip.setVisibility(View.VISIBLE);

            }

        }
    }

    public interface onClickBtns {
        void startFragment();
    }

}
