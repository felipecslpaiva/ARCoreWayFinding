package com.tribalscale.felipepaiva.arway2.chat;

import android.content.Context;
import android.view.View;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2beta1.DetectIntentResponse;
import com.google.cloud.dialogflow.v2beta1.QueryInput;
import com.google.cloud.dialogflow.v2beta1.SessionName;
import com.google.cloud.dialogflow.v2beta1.SessionsClient;
import com.google.cloud.dialogflow.v2beta1.SessionsSettings;
import com.google.cloud.dialogflow.v2beta1.TextInput;
import com.tribalscale.felipepaiva.arway2.BuildConfig;
import com.tribalscale.felipepaiva.arway2.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

import ai.api.AIServiceContext;
import ai.api.AIServiceContextBuilder;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.model.AIRequest;

public class ChatPressenter implements ChatContract.presenter {

    private String uuid  = UUID.randomUUID().toString();
    private ChatAdapter chatAdapter;
    private Context context;
    private ChatContract.view chatViewContract;

    SessionsClient sessionsClient;
    SessionName sessionName;

    // Android client
    private AIDataService aiDataService;
    private AIServiceContext customAIServiceContext;
    private AIRequest aiRequest;

    ChatPressenter(Context context, ChatContract.view view) {
        this.context = context;
        this.chatViewContract = view;
        initAndroidLib();
        initChatBotV2();
    }

    private void initChatBotV2() {
        try {
            InputStream stream = context.getResources().openRawResource(R.raw.dialog_flow_credentials);
            GoogleCredentials credentials = GoogleCredentials.fromStream(stream);
            String projectId = ((ServiceAccountCredentials)credentials).getProjectId();

            SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
            SessionsSettings sessionsSettings = settingsBuilder
                    .setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
            sessionsClient = SessionsClient.create(sessionsSettings);
            sessionName = SessionName.of(projectId, uuid);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void initAndroidLib() {
        //This can be provided in dagger scope
        final AIConfiguration config = new AIConfiguration(BuildConfig.ClientAccessToken,
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        aiDataService = new AIDataService(context, config);
        customAIServiceContext = AIServiceContextBuilder.buildFromSessionId(uuid);// helps to create new session whenever app restarts
        aiRequest = new AIRequest();
    }

    @Override
    public void initDialogFlow() {

        ChatMessage chatMessageType1 = new ChatMessage();
        chatMessageType1.setMessage("Welcome to the Dubai Mall, How can i help you today?");
        chatMessageType1.setMessageType(0);
        chatMessageType1.setMessageOrigin(0);

        ChatMessage chatMessageType2 = new ChatMessage();
        chatMessageType2.setMessage("I found this offer for you.");
        chatMessageType2.setMessageType(1);
        chatMessageType2.setMessageOrigin(0);
        chatMessageType2.setImageurl("https://png.pngtree.com/element_pic/00/16/07/19578de0fdacc43.jpg");

        ChatMessage chatMessageType3 = new ChatMessage();
        chatMessageType3.setMessage("test3");
        chatMessageType3.setMessageType(2);
        chatMessageType3.setMessageOrigin(0);

        ChatMessage chatMessageType4 = new ChatMessage();
        chatMessageType4.setMessage("Should i direct you for the store?");
        chatMessageType4.setMessageType(4);
        chatMessageType4.setMessageOrigin(0);

        ArrayList<ChatMessage> chatMessages = new ArrayList<ChatMessage>();
        chatMessages.add(chatMessageType1);
        chatMessages.add(chatMessageType2);
        chatMessages.add(chatMessageType3);
        chatMessages.add(chatMessageType4);

        chatAdapter = new ChatAdapter(chatMessages, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatViewContract.navigateToArScene();
            }
        });
        chatViewContract.setAdapter(chatAdapter);
    }

    @Override
    public SessionName getSession() {
        return sessionName;
    }

    @Override
    public SessionsClient getSessionClient() {
        return sessionsClient;
    }

    public void updateConversationWithMessage(DetectIntentResponse response) {
        ChatMessage chatMessage = new ChatMessage();
        if(response.getQueryResult().getFulfillmentText().contains("Facebook")){
            chatMessage.buildBotMessageForText(response.getQueryResult().getFulfillmentText());
            chatAdapter.addMessage(chatMessage);

            ChatMessage chatMessageType2 = new ChatMessage();
            chatMessageType2.setMessageType(1);
            chatMessageType2.setMessageOrigin(0);
            chatMessageType2.setImageurl("https://png.pngtree.com/element_pic/00/16/07/19578de0fdacc43.jpg");
            chatAdapter.addMessage(chatMessageType2);

            ChatMessage chatMessageType4 = new ChatMessage();
            chatMessageType4.setMessage("Should i direct you for the store?");
            chatMessageType4.setMessageType(4);
            chatMessageType4.setMessageOrigin(0);
            chatAdapter.addMessage(chatMessageType4);
        }else{
            chatMessage.buildBotMessageForText(response.getQueryResult().getFulfillmentText());
            chatAdapter.addMessage(chatMessage);
        }
        chatViewContract.clearEditText();
    }

    private void updateConversationWithMessage(String text) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.buildUserMessageForText(text);
        chatAdapter.addMessage(chatMessage);
        chatViewContract.clearEditText();
    }

    @Override
    public void sendMessage(String text) {
        updateConversationWithMessage(text);
        QueryInput queryInput = QueryInput.newBuilder().setText(
                TextInput.newBuilder()
                        .setText(text)
                        .setLanguageCode("en-US"))
                .build();
        new RequestJavaV2Task(context,
                getSession(),
                getSessionClient(), queryInput, this).execute();
    }

    @Override
    public void sendWelcomeMessage() {
        ChatMessage chatMessageType1 = new ChatMessage();
        chatMessageType1.setMessage("Welcome to the Dubai Mall, How can i help you today?");
        chatMessageType1.setMessageType(0);
        chatMessageType1.setMessageOrigin(0);

        ArrayList<ChatMessage> chatMessages = new ArrayList<ChatMessage>();
        chatMessages.add(chatMessageType1);
        chatAdapter = new ChatAdapter(chatMessages, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatViewContract.navigateToArScene();
            }
        });
        chatViewContract.setAdapter(chatAdapter);
    }
}
