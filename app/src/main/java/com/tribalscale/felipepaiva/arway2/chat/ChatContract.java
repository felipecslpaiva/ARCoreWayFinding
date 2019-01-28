package com.tribalscale.felipepaiva.arway2.chat;

public interface ChatContract {
    interface view{
        void setAdapter(ChatAdapter chatAdapter);
        void navigateToArScene();
    }

    interface presenter{
        void initDialogFlow();
    }
}
