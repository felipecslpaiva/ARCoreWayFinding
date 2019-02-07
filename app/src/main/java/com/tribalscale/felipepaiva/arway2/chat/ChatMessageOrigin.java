package com.tribalscale.felipepaiva.arway2.chat;

import java.lang.annotation.Retention;

import androidx.annotation.IntDef;

import static com.tribalscale.felipepaiva.arway2.chat.ChatMessageOrigin.LOCAL;
import static com.tribalscale.felipepaiva.arway2.chat.ChatMessageOrigin.REMOTE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@IntDef({LOCAL, REMOTE})
public @interface ChatMessageOrigin {
    int LOCAL = 0;
    int REMOTE= 1;
}