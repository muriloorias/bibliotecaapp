package com.muriloorias.bibliotecaApp.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.muriloorias.bibliotecaApp.R;
import com.muriloorias.bibliotecaApp.adpter.BookAdapter;
import com.muriloorias.bibliotecaApp.databinding.ActivityHomeBinding;
import com.muriloorias.bibliotecaApp.fragment.CommentsFragment;
import com.muriloorias.bibliotecaApp.model.Book;
import com.muriloorias.bibliotecaApp.services.ApiBook;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private BookAdapter bookAdapter;
    private final ArrayList<Book> bookList = new ArrayList<>();
    private final Context context = HomeActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //configuração do retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://bilioteca-api.onrender.com/")
                .build();
        ApiBook api = retrofit.create(ApiBook.class);
        api.getBooks().enqueue(new Callback<ArrayList<Book>>() {
            @Override
            public void onResponse(Call<ArrayList<Book>> call, Response<ArrayList<Book>> response) {
                if(response.isSuccessful()){
                    ArrayList<Book> book = response.body();
                    if (book != null){
                        for (Book book1: book){
                            String title = book1.getTitle();
                            String author = book1.getAuthor();
                            String description = book1.getDescription();
                            Boolean is_available = book1.getIs_available();
                            bookList.add(
                                    new Book(title, author, description, is_available)
                            );
                        }
                    }
                    else {
                        Snackbar snackbar = Snackbar.make(view, "erro ao carregar comentarios", Snackbar.LENGTH_SHORT );
                        snackbar.setBackgroundTint(Color.parseColor("#FF0000"));
                        snackbar.show();
                    }
                    RecyclerView recyclerViewBook = binding.recyclerViewBooks;
                    recyclerViewBook.setLayoutManager(new LinearLayoutManager(context));
                    recyclerViewBook.setHasFixedSize(true);
                    bookAdapter = new BookAdapter(bookList, context);
                    recyclerViewBook.setAdapter(bookAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Book>> call, Throwable t) {
                Snackbar snackbar = Snackbar.make(view, "erro fatal no servidor", Snackbar.LENGTH_SHORT );
                snackbar.setBackgroundTint(Color.parseColor("#FF0000"));
                snackbar.show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Configuração da BottomNavigationView
        binding.bottomBar.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                showHomeScreen();
            } else if (item.getItemId() == R.id.comments) {
                showCommentsScreen();
            }
            return true;
        });
    }

    private void showHomeScreen() {
        // Mostra o RecyclerView e esconde o FragmentContainer
        binding.recyclerViewBooks.setVisibility(View.VISIBLE);
        binding.fragmentContainer.setVisibility(View.GONE);
        getSupportFragmentManager().popBackStack();
    }

    private void showCommentsScreen() {
        // Esconde o RecyclerView e mostra o FragmentContainer
        binding.recyclerViewBooks.setVisibility(View.GONE);
        binding.fragmentContainer.setVisibility(View.VISIBLE);

        // Substitui o fragmento pelo CommentsFragment
        switchScreen(new CommentsFragment());
    }

    private void switchScreen(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

}

