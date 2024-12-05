package com.muriloorias.bibliotecaApp.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import com.muriloorias.bibliotecaApp.R;
import com.muriloorias.bibliotecaApp.adpter.CommentsAdapter;
import com.muriloorias.bibliotecaApp.databinding.FragmentCommentsBinding;
import com.muriloorias.bibliotecaApp.model.Comments;
import com.muriloorias.bibliotecaApp.retrofit.RetrofitClient;
import com.muriloorias.bibliotecaApp.services.ApiComments;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CommentsFragment extends Fragment {
    private FragmentCommentsBinding binding;
    private CommentsAdapter commentsAdapter;
    private ArrayList<Comments> commentsList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCommentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Context context = getContext();
        ApiComments api = RetrofitClient.getRetrofitInstance().create(ApiComments.class);
        api.getComments().enqueue(new Callback<ArrayList<Comments>>() {
            @Override
            public void onResponse(Call<ArrayList<Comments>> call, Response<ArrayList<Comments>> response) {
                ArrayList<Comments> comments = response.body();
                if (comments != null) {
                    for (Comments comments1 : comments) {
                        String text = comments1.getText();
                        Boolean criticize = comments1.getCriticize();
                        commentsList.add(
                                new Comments(text, criticize)
                        );
                    }
                } else {
                    Snackbar snackbar = Snackbar.make(root, "erro ao carregar comentarios", Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.parseColor("#FF0000"));
                    snackbar.show();
                }
                RecyclerView recyclerViewComments = binding.recyclerViewComments;
                recyclerViewComments.setLayoutManager(new LinearLayoutManager(context));
                recyclerViewComments.setHasFixedSize(true);
                commentsAdapter = new CommentsAdapter(commentsList, context);
                recyclerViewComments.setAdapter(commentsAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Comments>> call, Throwable t) {
                Snackbar snackbar = Snackbar.make(root, "erro fatal no servidor", Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(Color.parseColor("#FF0000"));
                snackbar.show();
            }
        });

        binding.addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fazer a tela para adição de comentario e fazer a troca quando o botão for clicado
                CreateCommentFragment createCommentFragment = new CreateCommentFragment();
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, createCommentFragment)
                        .addToBackStack("voltar")
                        .commit();
            }
        });

        return root;
    }
}