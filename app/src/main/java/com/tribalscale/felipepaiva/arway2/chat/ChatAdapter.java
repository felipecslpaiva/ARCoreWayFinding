package com.tribalscale.felipepaiva.arway2.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tribalscale.felipepaiva.arway2.R;
import com.tribalscale.felipepaiva.arway2.chat.viewholders.ViewHolderText;
import com.tribalscale.felipepaiva.arway2.chat.viewholders.ViewHolderTextGallery;
import com.tribalscale.felipepaiva.arway2.chat.viewholders.ViewHolderTextImage;
import com.tribalscale.felipepaiva.arway2.chat.viewholders.ViewHolderTextWithButtons;
import com.tribalscale.felipepaiva.arway2.chat.viewholders.ViewHolderUserText;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.tribalscale.felipepaiva.arway2.chat.ChatViewTypes.BOT_TEXT_BUTTONS;
import static com.tribalscale.felipepaiva.arway2.chat.ChatViewTypes.BOT_TEXT_GALLERY;
import static com.tribalscale.felipepaiva.arway2.chat.ChatViewTypes.BOT_TEXT_SIMPLE;
import static com.tribalscale.felipepaiva.arway2.chat.ChatViewTypes.BOT_TEXT_WITH_IMAGE;
import static com.tribalscale.felipepaiva.arway2.chat.ChatViewTypes.USER_MESSAGE_TEXT;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<ChatMessage> chatMessages;
    private View.OnClickListener clickListener;

    ChatAdapter(List<ChatMessage> chatMessages, View.OnClickListener clickListener) {
        this.chatMessages = chatMessages;
        this.clickListener = clickListener;
    }

    void addMessage(ChatMessage chatMessage) {
        chatMessages.add(chatMessage);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType){
            //BOT MESSAGE TYPES
            //Simple Text
            case BOT_TEXT_SIMPLE:
                view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.chat_item_text, parent, false);
                return new ViewHolderText(view);
            //Text with image
            case BOT_TEXT_WITH_IMAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.chat_item_text_image, parent, false);
                return new ViewHolderTextImage(view);
            //Text Gallery
            case BOT_TEXT_GALLERY:
                view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.chat_item_text_gallery, parent, false);
                return new ViewHolderTextGallery(view);
            //Text with buttons
            case BOT_TEXT_BUTTONS:
                view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.chat_item_text_buttons, parent, false);
                return new ViewHolderTextWithButtons(view);
            //User Message types
            //Text
            case USER_MESSAGE_TEXT:
                view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.chat_item_user_text, parent, false);
                return new ViewHolderUserText(view);
            //Default simple text message
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.chat_item_text, parent, false);
                return new ViewHolderText(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = chatMessages.get(position).getMessageType();
        switch (viewType){
            case BOT_TEXT_SIMPLE:
                ((ViewHolderText) holder).bindData(chatMessages.get(position));
                break;
            case BOT_TEXT_WITH_IMAGE:
                ((ViewHolderTextImage) holder).bindData(chatMessages.get(position));
                break;
            case BOT_TEXT_GALLERY:
                ((ViewHolderTextGallery) holder).bindData(chatMessages.get(position));
                break;
            case BOT_TEXT_BUTTONS:
                ((ViewHolderTextWithButtons) holder).bindData(chatMessages.get(position), clickListener);
                break;
            case USER_MESSAGE_TEXT:
                ((ViewHolderUserText) holder).bindData(chatMessages.get(position));
                break;
            default:
                ((ViewHolderText) holder).bindData(chatMessages.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }


    @Override
    public int getItemViewType(int position) {
        return chatMessages.get(position).getMessageType();
    }
}
