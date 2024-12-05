package com.muriloorias.bibliotecaApp.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.snackbar.Snackbar;
import com.muriloorias.bibliotecaApp.R;
import com.muriloorias.bibliotecaApp.databinding.FragmentCreateCommentBinding;
import com.muriloorias.bibliotecaApp.model.Comments;
import com.muriloorias.bibliotecaApp.retrofit.RetrofitClient;
import com.muriloorias.bibliotecaApp.services.ApiComments;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateCommentFragment extends Fragment {

    private FragmentCreateCommentBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateCommentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        View root = binding.getRoot();
        binding.checkButton.setOnClickListener(v->{
            String text = binding.text.getText().toString();
            int selectedId = binding.criticizeRadioGroup.getCheckedRadioButtonId();
            if (selectedId == -1){
                Snackbar snackbar = Snackbar.make(root, "por favor, selecione se é uma critica ou não", Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(Color.parseColor("#FF0000"));
                snackbar.show();
            }
            boolean isCriticize = selectedId == R.id.radioYes;
            if(text.isEmpty()){
                Snackbar snackbar = Snackbar.make(root, "por favor, insira um comentario", Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(Color.parseColor("#FF0000"));
                snackbar.show();
            }
            Comments comments = new Comments(text, isCriticize);
            ApiComments apiComments = RetrofitClient.getRetrofitInstance().create(ApiComments.class);
            apiComments.postComment(comments).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()){
                        Snackbar snackbar = Snackbar.make(root, "comentario criado com sucesso", Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(Color.parseColor("#67CB57"));
                        snackbar.show();
                        CommentsFragment commentsFragment = new CommentsFragment();
                        requireActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, commentsFragment)
                                .commit();
                    } else {
                        Snackbar snackbar = Snackbar.make(root, "erro ao criar comentario, tente mais tarde", Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(Color.parseColor("#FF0000"));
                        snackbar.show();
                        CommentsFragment commentsFragment = new CommentsFragment();
                        requireActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, commentsFragment)

                                .commit();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Snackbar snackbar = Snackbar.make(root, "erro fatal no servidor", Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.parseColor("#FF0000"));
                    snackbar.show();
                    CommentsFragment commentsFragment = new CommentsFragment();
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, commentsFragment)
                            .commit();
                }
            });
        });
    }
}