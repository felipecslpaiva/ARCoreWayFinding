package com.tribalscale.felipepaiva.arway2.chat.viewholders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tribalscale.felipepaiva.arway2.R;
import com.tribalscale.felipepaiva.arway2.chat.ChatMessage;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderTextWithButtons extends RecyclerView.ViewHolder {

    LinearLayout linearLayoutButtons;
    TextView textView;
    TextView buttonPositive;
    TextView buttonNegative;

    public ViewHolderTextWithButtons(@NonNull android.view.View itemView) {
        super(itemView);
    }

    public void bindData(final ChatMessage viewModel, View.OnClickListener clickListener) {
        textView = itemView.findViewById(R.id.chat_item_text_message);
        linearLayoutButtons = itemView.findViewById(R.id.chat_item_buttons_linear_layout);
        buttonNegative = itemView.findViewById(R.id.chat_item_button_negative);
        buttonPositive = itemView.findViewById(R.id.chat_item_button_positive);
        buttonPositive.setOnClickListener(clickListener);
        buttonNegative.setOnClickListener(view -> {
            textView.setText("Thanks for your feedbaack!");
            linearLayoutButtons.setVisibility(View.GONE);
        });
        textView.setText(viewModel.getMessage());
    }
}
