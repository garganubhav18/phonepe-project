package com.anubhav.phonepeproject.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.anubhav.phonepeproject.R;
import com.anubhav.phonepeproject.model.LogoDetails;
import com.anubhav.phonepeproject.network.DataCallback;
import com.anubhav.phonepeproject.viewmodel.PredictLogoViewModel;
import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements LogoAnswerAdapter.AnswerAdapterListener, LogoSuggestionAdapter.SuggestionAdapterListener {


    private ImageView imageView;
    private RecyclerView gridAnswerView;
    private RecyclerView gridSuggestionView;
    private PredictLogoViewModel predictLogoViewModel;

    private String correctAnswer;
    private char[] alphabets = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        predictLogoViewModel = ViewModelProviders.of(this).get(PredictLogoViewModel.class);
        initObservables();

        imageView = findViewById(R.id.img_logo);
        gridAnswerView = findViewById(R.id.grid_answer_view);
        gridAnswerView.setLayoutManager(new GridLayoutManager(this, 4));
        gridSuggestionView = findViewById(R.id.gird_suggestion_view);
        gridSuggestionView.setLayoutManager(new GridLayoutManager(this, 5));

        if(predictLogoViewModel.getLogoDetails() == null) {
            predictLogoViewModel.fetchLogoDetails(this);
        }
    }

    private void initObservables() {
        predictLogoViewModel.getLogoDetailsObservables().observe(this, listDataCallback -> {
            if(listDataCallback == null){
                return;
            }
            if(listDataCallback.getStatusCode() == DataCallback.SUCCESS){
                predictLogoViewModel.setLogoDetails(listDataCallback.getResponseData());
                setUpGame();
            } else if(listDataCallback.getStatusCode() == DataCallback.FAILURE){

            }
        });
    }

    private void setUpGame() {
        if(predictLogoViewModel.getLogoDetails() != null &&
                predictLogoViewModel.getLogoDetails().size() > predictLogoViewModel.getCurrentLogoIndex()) {
            LogoDetails logoDetails = predictLogoViewModel.getLogoDetails().get(predictLogoViewModel.getCurrentLogoIndex());

            Glide.with(this).load(logoDetails.getImgUrl()).into(imageView);

            correctAnswer = logoDetails.getName();
            if(predictLogoViewModel.getSuggestedList().isEmpty()) {
                List<Character> suggestedList = predictLogoViewModel.getSuggestedList();
                for(int i = 0; i<correctAnswer.length(); i++) {
                    suggestedList.add(correctAnswer.charAt(i));
                }

                Random random = new Random();
                for (int i= correctAnswer.length(); i<2 * correctAnswer.length(); i++){
                    suggestedList.add(alphabets[random.nextInt(alphabets.length)]);
                }
                Collections.shuffle(suggestedList);
            }

            if(predictLogoViewModel.getAnswerList().isEmpty()) {
                List<Character> answerList = predictLogoViewModel.getAnswerList();
                for (int i= 0; i<correctAnswer.length(); i++) {
                    answerList.add(' ');
                }
            }

            if(gridAnswerView.getAdapter() == null) {
                gridAnswerView.setAdapter(new LogoAnswerAdapter(this, this));
            } else {
                gridAnswerView.getAdapter().notifyDataSetChanged();
            }

            if(gridSuggestionView.getAdapter() == null){
                gridSuggestionView.setAdapter(new LogoSuggestionAdapter(this, this));
            } else {
                gridSuggestionView.getAdapter().notifyDataSetChanged();
            }
        } else {
            Log.d("MainActivity","total score = " + predictLogoViewModel.getTotalScore());
        }
    }

    @Override
    public List<Character> getAnswerList() {
        return predictLogoViewModel.getAnswerList();
    }

    @Override
    public List<Character> getSuggestionList() {
        return predictLogoViewModel.getSuggestedList();
    }

    @Override
    public void onItemClick(String c, int position) {
        if(correctAnswer.contains(c)) {
            List<Character> answerList = predictLogoViewModel.getAnswerList();
            for(int i= 0; i<correctAnswer.length(); i++) {
                if(correctAnswer.charAt(i) == c.charAt(0) && answerList.get(i) != c.charAt(0)) {
                    answerList.remove(i);
                    answerList.add(i, c.charAt(0));
                    predictLogoViewModel.setCurrentScore(predictLogoViewModel.getCurrentScore() + 1);
                    List<Character> suggestionList = predictLogoViewModel.getSuggestedList();
                    suggestionList.remove(position);
                    break;
                }
            }
            int count = 0;
            for (int i=0; i<answerList.size(); i++) {
                if(answerList.get(i) == ' '){
                    break;
                }
                count++;
            }
            if(count == correctAnswer.length()) {
                predictLogoViewModel.setTotalScore(predictLogoViewModel.getTotalScore() + predictLogoViewModel.getCurrentScore());
                predictLogoViewModel.setCurrentScore(0);
                predictLogoViewModel.getAnswerList().clear();
                predictLogoViewModel.getSuggestedList().clear();
                predictLogoViewModel.setCurrentLogoIndex(predictLogoViewModel.getCurrentLogoIndex() + 1);
                setUpGame();
            } else {
                if(gridAnswerView.getAdapter() != null) {
                    gridAnswerView.getAdapter().notifyDataSetChanged();
                }
                if(gridSuggestionView.getAdapter() != null) {
                    gridSuggestionView.getAdapter().notifyDataSetChanged();
                }
            }
        } else {
            predictLogoViewModel.setCurrentScore(predictLogoViewModel.getCurrentScore() - 1);
        }
    }
}
