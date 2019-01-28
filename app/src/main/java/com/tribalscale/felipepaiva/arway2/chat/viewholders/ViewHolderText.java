package com.tribalscale.felipepaiva.arway2.chat.viewholders;

import android.widget.TextView;

import com.tribalscale.felipepaiva.arway2.R;
import com.tribalscale.felipepaiva.arway2.chat.ChatMessage;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderText extends RecyclerView.ViewHolder {

    TextView textView;

    public ViewHolderText(@NonNull android.view.View itemView) {
        super(itemView);
    }

    public void bindData(final ChatMessage viewModel) {
        textView = itemView.findViewById(R.id.chat_item_text_message);
        textView.setText(viewModel.getMessage());
    }
}
