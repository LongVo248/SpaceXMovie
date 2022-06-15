package cf.vandit.movie_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.retrofit.dto.MovieEvaluateLoad;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context mContext;
    private List<MovieEvaluateLoad> mData;


    public CommentAdapter(Context mContext, List<MovieEvaluateLoad> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.row_evaluate, parent, false);
        return new CommentViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {

        Glide.with(mContext).load(mData.get(position).getAccountInfo().getAvatar()).into(holder.img_user);
        holder.tv_name.setText(mData.get(position).getAccountInfo().getFirstname() + " " + mData.get(position).getAccountInfo().getLastname());
        holder.tv_content.setText(mData.get(position).getEvaluateContent());
        holder.ratingBarLoad.setRating((float) mData.get(position).getEvaluateRate());
        holder.tv_date.setText(timestampToString(mData.get(position).getEvaluateTime()));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        ImageView img_user;
        RatingBar ratingBarLoad;
        TextView tv_name, tv_content, tv_date;

        public CommentViewHolder(View itemView) {
            super(itemView);
            img_user = itemView.findViewById(R.id.comment_user_img);
            tv_name = itemView.findViewById(R.id.comment_username);
            tv_content = itemView.findViewById(R.id.comment_content);
            tv_date = itemView.findViewById(R.id.tv_time);
            ratingBarLoad = itemView.findViewById(R.id.ratingBarLoad);
        }
    }


    private String timestampToString(long time) {
        Date date = new Date(time);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String a = df.format(date);
        return a;
    }


}
