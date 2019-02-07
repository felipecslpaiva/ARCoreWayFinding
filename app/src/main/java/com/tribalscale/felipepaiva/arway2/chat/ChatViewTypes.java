package com.tribalscale.felipepaiva.arway2.chat;

import java.lang.annotation.Retention;

import androidx.annotation.IntDef;

import static com.tribalscale.felipepaiva.arway2.chat.ChatViewTypes.BOT_TEXT_BUTTONS;
import static com.tribalscale.felipepaiva.arway2.chat.ChatViewTypes.BOT_TEXT_GALLERY;
import static com.tribalscale.felipepaiva.arway2.chat.ChatViewTypes.BOT_TEXT_SIMPLE;
import static com.tribalscale.felipepaiva.arway2.chat.ChatViewTypes.BOT_TEXT_WITH_IMAGE;
import static com.tribalscale.felipepaiva.arway2.chat.ChatViewTypes.USER_MESSAGE_TEXT;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@IntDef({BOT_TEXT_SIMPLE, BOT_TEXT_WITH_IMAGE, BOT_TEXT_GALLERY, BOT_TEXT_BUTTONS, USER_MESSAGE_TEXT})
public @interface ChatViewTypes {
    int BOT_TEXT_SIMPLE = 0;
    int BOT_TEXT_WITH_IMAGE = 1;
    int BOT_TEXT_GALLERY = 2;
    int USER_MESSAGE_TEXT = 3;
    int BOT_TEXT_BUTTONS = 4;
}