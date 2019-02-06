package com.tribalscale.felipepaiva.arway2.chat;

import android.os.AsyncTask;
import android.util.Log;

import com.google.cloud.dialogflow.v2beta1.DetectIntentRequest;
import com.google.cloud.dialogflow.v2beta1.DetectIntentResponse;
import com.google.cloud.dialogflow.v2beta1.QueryInput;
import com.google.cloud.dialogflow.v2beta1.SessionName;
import com.google.cloud.dialogflow.v2beta1.SessionsClient;


public class RequestJavaV2Task extends AsyncTask<Void, Void, DetectIntentResponse> {

    private static String TAG = RequestJavaV2Task.class.getSimpleName();
    android.content.Context context;
    private SessionName session;
    private SessionsClient sessionsClient;
    private QueryInput queryInput;
    private ChatContract.presenter presenter;

    public RequestJavaV2Task(android.content.Context context, SessionName session, SessionsClient sessionClient, QueryInput queryInput, ChatPressenter presenter) {
        this.context = context;
        this.session = session;
        this.sessionsClient = sessionClient;
        this.queryInput = queryInput;
        this.presenter = presenter;
    }

    @Override
    protected DetectIntentResponse doInBackground(Void... voids) {
        try{
            DetectIntentRequest detectIntentRequest =
                    DetectIntentRequest.newBuilder()
                            .setSession(session.toString())
                            .setQueryInput(queryInput)
                            .build();
            return sessionsClient.detectIntent(detectIntentRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(DetectIntentResponse response) {
        String botReply = response.getQueryResult().getFulfillmentText();
        Log.d(TAG, "V2 Bot Reply: " + botReply);
        presenter.updateConversationWithMessage(response);
//        ((ChatActivity) activity).callbackV2(response);
    }
}
