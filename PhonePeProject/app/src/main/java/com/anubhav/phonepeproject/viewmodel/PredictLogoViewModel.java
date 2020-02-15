package com.anubhav.phonepeproject.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anubhav.phonepeproject.model.LogoDetails;
import com.anubhav.phonepeproject.network.ApiClient;
import com.anubhav.phonepeproject.network.DataCallback;
import com.anubhav.phonepeproject.network.repository.LogoDetailsApiInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PredictLogoViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<DataCallback<List<LogoDetails>>> logoDetailsObservables = new MutableLiveData<>();
    private List<LogoDetails> logoDetails;
    private List<Character> suggestedList = new ArrayList<>();
    private List<Character> answerList = new ArrayList<>();
    private int currentLogoIndex = 0;
    private int currentScore = 0;
    private int totalScore = 0;

    public MutableLiveData<DataCallback<List<LogoDetails>>> getLogoDetailsObservables() {
        return logoDetailsObservables;
    }

    public List<LogoDetails> getLogoDetails() {
        return logoDetails;
    }

    public void setLogoDetails(List<LogoDetails> logoDetails) {
        this.logoDetails = logoDetails;
    }

    public List<Character> getSuggestedList() {
        return suggestedList;
    }

    public void setSuggestedList(List<Character> suggestedList) {
        this.suggestedList = suggestedList;
    }

    public List<Character> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Character> answerList) {
        this.answerList = answerList;
    }

    public int getCurrentLogoIndex() {
        return currentLogoIndex;
    }

    public void setCurrentLogoIndex(int currentLogoIndex) {
        this.currentLogoIndex = currentLogoIndex;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void fetchLogoDetails(Context context) {
        
        compositeDisposable.add(Single.fromCallable(() -> {
            String json = null;
            try {
                InputStream is = context.getAssets().open("logo.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            List<LogoDetails> data = new Gson().fromJson(json, new TypeToken<List<LogoDetails>>(){}.getType());
            return data;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(logoDetails -> logoDetailsObservables.setValue(new DataCallback<>(logoDetails)),
                throwable -> logoDetailsObservables.setValue(new DataCallback<List<LogoDetails>>(throwable.getMessage()))));
        
//        LogoDetailsApiInterface logoDetailsApiInterface = ApiClient.getApiClient().getClient().create(LogoDetailsApiInterface.class);
//        logoDetailsApiInterface.getLogoDetails().enqueue(new Callback<List<LogoDetails>>() {
//            @Override
//            public void onResponse(Call<List<LogoDetails>> call, Response<List<LogoDetails>> response) {
//                if(response.isSuccessful()) {
//                    logoDetailsObservables.setValue(new DataCallback<>(response.body()));
//                } else {
//                    if(response.errorBody() != null) {
//                        logoDetailsObservables.setValue(new DataCallback<>(response.errorBody().toString()));
//                    } else {
//                        logoDetailsObservables.setValue(new DataCallback<>("null error body"));
//                    }
//                }
//                logoDetailsObservables.setValue(new DataCallback<>(response.body()));
//            }
//
//            @Override
//            public void onFailure(Call<List<LogoDetails>> call, Throwable t) {
//                logoDetailsObservables.setValue(new DataCallback<>(t.getMessage()));
//            }
//        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
