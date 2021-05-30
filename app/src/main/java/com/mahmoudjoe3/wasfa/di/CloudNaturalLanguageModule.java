package com.mahmoudjoe3.wasfa.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.language.v1.CloudNaturalLanguage;
import com.google.api.services.language.v1.CloudNaturalLanguageScopes;
import com.mahmoudjoe3.wasfa.R;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class CloudNaturalLanguageModule {
    private static final String PREFS = "AccessTokenLoader";
    private static final String PREF_ACCESS_TOKEN = "access_token";


    @Provides
    @Singleton //get application and Provides GoogleCredential
    public static GoogleCredential setAccessToken(Application application) {
        CredentialTask task = new CredentialTask(application);
        Thread thread = new Thread(task);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String token = task.getApiToken();
        return new GoogleCredential()
                .setAccessToken(token)
                .createScoped(CloudNaturalLanguageScopes.all());
    }
    //do background task
    public static class CredentialTask implements Runnable {
        private volatile String  value;
        Application application;

        public CredentialTask(Application application) {
            this.application = application;
        }

        @Override
        public void run() {
            value = getToken(application);
        }

        public String getApiToken() {
            return value;
        }
        private static String getToken(Application application){
            final SharedPreferences prefs =
                    application.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
            String currentToken = prefs.getString(PREF_ACCESS_TOKEN, null);

            // Check if the current token is still valid for a while
            if (currentToken != null) {
                final GoogleCredential credential = new GoogleCredential()
                        .setAccessToken(currentToken)
                        .createScoped(CloudNaturalLanguageScopes.all());
                final Long seconds = credential.getExpiresInSeconds();
                if (seconds != null && seconds > 3600) {
                    return currentToken;
                }
            }


            final InputStream stream = application.getResources().openRawResource(R.raw.wasfa_314217_95e60b3ab7a0);
            try {
                final GoogleCredential credential = GoogleCredential.fromStream(stream)
                        .createScoped(CloudNaturalLanguageScopes.all());
                credential.refreshToken();
                final String accessToken = credential.getAccessToken();
                prefs.edit().putString(PREF_ACCESS_TOKEN, accessToken).apply();
                return accessToken;
            } catch (IOException e) {
                Log.e("TAG", "Failed to obtain access token.", e);
            }
            return null;
        }

    }


    @Provides
    @Singleton //get GoogleCredential and Provides CloudNaturalLanguage
    public static CloudNaturalLanguage getCloudNaturalLanguage(final GoogleCredential credential) {

        return new CloudNaturalLanguage.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) throws IOException {
                credential.initialize(request);
            }
        }).build();
    }



}
