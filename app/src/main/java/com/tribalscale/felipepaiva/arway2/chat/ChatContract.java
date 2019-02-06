package com.tribalscale.felipepaiva.arway2.chat;

import com.google.cloud.dialogflow.v2beta1.DetectIntentResponse;
import com.google.cloud.dialogflow.v2beta1.SessionName;
import com.google.cloud.dialogflow.v2beta1.SessionsClient;

public interface ChatContract {
    interface view{
        void setAdapter(ChatAdapter chatAdapter);
        void navigateToArScene();
        void clearEditText();
    }

    interface presenter{
        void initDialogFlow();
        SessionName getSession();
        SessionsClient getSessionClient();
        void updateConversationWithMessage(DetectIntentResponse response);

        void sendMessage(String text);
    }
}
