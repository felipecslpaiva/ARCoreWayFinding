package com.tribalscale.felipepaiva.arway2.chat.viewholders;

import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tribalscale.felipepaiva.arway2.R;
import com.tribalscale.felipepaiva.arway2.chat.ChatMessage;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderTextImage extends RecyclerView.ViewHolder {

    TextView textView;
    ImageView imageView;

    public ViewHolderTextImage(@NonNull android.view.View itemView) {
        super(itemView);
    }

    public void bindData(final ChatMessage viewModel) {
        textView = itemView.findViewById(R.id.chat_item_text_message_message);
        imageView = itemView.findViewById(R.id.chat_item_text_message_gallery);
        textView.setText(viewModel.getMessage());
        Picasso.get().load(viewModel.getImageurl()).into(imageView);
    }
}