package com.muriloorias.bibliotecaApp.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.muriloorias.bibliotecaApp.databinding.BookItemBinding;
import com.muriloorias.bibliotecaApp.model.Book;
import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private final ArrayList<Book> bookList;
    private final Context context;

    public BookAdapter(ArrayList<Book> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookItemBinding bookItem;
        bookItem = BookItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new BookViewHolder(bookItem);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.binding.title.setText(bookList.get(position).getTitle());
        holder.binding.description.setText(bookList.get(position).getDescription());
        holder.binding.author.setText(bookList.get(position).getAuthor());
        boolean isAvailable = bookList.get(position).getIs_available();
        holder.binding.isAvailable.setText(isAvailable ? "Disponivel" : "Indisponivel");
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {

        BookItemBinding binding;

        public BookViewHolder(BookItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
