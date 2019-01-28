package com.tribalscale.felipepaiva.arway2.chat;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;

import ai.api.AIServiceContext;
import ai.api.AIServiceContextBuilder;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.model.AIRequest;

public class ChatPressenter implements ChatContract.presenter {

    private AIServiceContext aiServiceContext;
    private AIRequest aiRequest;
    private AIConfiguration configuration;
    private AIDataService dataService;
    private String uuid = "e1430af1f88742febf21f4e1272a26bd";
    private ChatAdapter chatAdapter;
    private ChatContract.view chatViewContract;

    ChatPressenter(Context context, ChatContract.view view) {
        initAndroidLib(context);
        initJavaApi(context);
        this.chatViewContract = view;
    }

    private void initJavaApi(Context context) {
        try {
//            context.getResources().openRawResource(R.raw)
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void initAndroidLib(Context context) {
        //This can be provided in dagger scope
        configuration = new AIConfiguration(
                "99dc365d0cf1480d831b040a51775ef3",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        dataService = new AIDataService(context, configuration);
        aiServiceContext = AIServiceContextBuilder.buildFromSessionId(uuid);
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
}
