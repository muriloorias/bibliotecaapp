package com.muriloorias.bibliotecaApp.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.muriloorias.bibliotecaApp.databinding.CommentItemBinding;
import com.muriloorias.bibliotecaApp.model.Comments;
import java.util.ArrayList;


public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    private final ArrayList<Comments> commentsList;
    private final Context context;

    public CommentsAdapter(ArrayList<Comments> commentsList, Context context) {
        this.commentsList = commentsList;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CommentItemBinding commentItem;
        commentItem = CommentItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new CommentsViewHolder(commentItem);
    }



    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        holder.binding.text.setText(commentsList.get(position).getText());
        boolean criticize = commentsList.get(position).getCriticize();
        holder.binding.cricize.setText(criticize ? "critica" : "elogio");
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public static class CommentsViewHolder extends RecyclerView.ViewHolder{
        CommentItemBinding binding;

        public CommentsViewHolder(CommentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
