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

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<ChatMessage> chatMessages;
    private View.OnClickListener clickListener;

    ChatAdapter(List<ChatMessage> chatMessages, View.OnClickListener clickListener) {
        this.chatMessages = chatMessages;
        this.clickListener = clickListener;
    }

    public void addMessage(ChatMessage chatMessage) {
        chatMessages.add(chatMessage);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType){
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_text, parent, false);
                return new ViewHolderText(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_text_image, parent, false);
                return new ViewHolderTextImage(view);
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_text_gallery, parent, false);
                return new ViewHolderTextGallery(view);
            case 3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_user_text, parent, false);
                return new ViewHolderUserText(view);
            case 4:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_text_buttons, parent, false);
                return new ViewHolderTextWithButtons(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_text, parent, false);
                return new ViewHolderText(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = chatMessages.get(position).getMessageType();
        switch (viewType){
            case 0:
                ((ViewHolderText) holder).bindData(chatMessages.get(position));
                break;
            case 1:
                ((ViewHolderTextImage) holder).bindData(chatMessages.get(position));
                break;
            case 2:
                ((ViewHolderTextGallery) holder).bindData(chatMessages.get(position));
                break;
            case 3:
                ((ViewHolderUserText) holder).bindData(chatMessages.get(position));
                break;
            case 4:
                ((ViewHolderTextWithButtons) holder).bindData(chatMessages.get(position), clickListener);
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
