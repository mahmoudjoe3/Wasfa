package com.mahmoudjoe3.wasfaty.repo;


import androidx.lifecycle.LiveData;

import com.google.api.client.json.GenericJson;
import com.google.api.services.language.v1.CloudNaturalLanguage;
import com.google.api.services.language.v1.CloudNaturalLanguageRequest;
import com.google.api.services.language.v1.model.AnalyzeSentimentRequest;
import com.google.api.services.language.v1.model.Document;
import com.mahmoudjoe3.wasfaty.database.InteractionsDao;
import com.mahmoudjoe3.wasfaty.pojo.Interaction;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NLB_Repository {
    //API
    private Thread mThread;
    private CloudNaturalLanguage nLP_Api;
    private final java.util.concurrent.BlockingQueue<CloudNaturalLanguageRequest<? extends GenericJson>> mRequests
            = new ArrayBlockingQueue<>(3);


    @Inject
    public NLB_Repository(CloudNaturalLanguage cloudNaturalLanguage) {
        this.nLP_Api = cloudNaturalLanguage;
    }

    //API
    public void analyzeTextUsingCloudNLPApi(String text) {
        try {
            mRequests.add(nLP_Api
                    .documents()
                    .analyzeSentiment(new AnalyzeSentimentRequest()
                            .setDocument(new Document()
                                    .setContent(text)
                                    .setType("PLAIN_TEXT")
                            )
                    )
            );
            startWorkerThread();
        } catch (IOException e) {
            android.util.Log.e("TAG", "Failed to create analyze request.", e);
        }
    }
    private void startWorkerThread() {
        if (mThread != null) {
            return;
        }
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (mThread == null) {
                        break;
                    }
                    try {
                        // API calls are executed here in this worker thread
                        if(onNLPAPIResponse!=null)
                            onNLPAPIResponse.onResponse(mRequests.take().execute().toPrettyString());
                    } catch (InterruptedException e) {
                        android.util.Log.e("TAG", "Interrupted.", e);
                        break;
                    } catch (IOException e) {
                        android.util.Log.e("TAG", "Failed to execute a request.", e);
                    }
                }
            }
        });
        mThread.start();
    }
    OnNLPAPIResponse onNLPAPIResponse;
    public void setOnNLPAPIResponse(OnNLPAPIResponse onNLPAPIResponse) { this.onNLPAPIResponse = onNLPAPIResponse; }
    public interface OnNLPAPIResponse { void onResponse(String response);}


}



