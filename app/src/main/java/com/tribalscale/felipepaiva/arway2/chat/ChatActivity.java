package com.tribalscale.felipepaiva.arway2.chat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tribalscale.felipepaiva.arway2.R;
import com.tribalscale.felipepaiva.arway2.arscene.MainActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChatActivity extends AppCompatActivity implements ChatContract.view {

    private ChatContract.presenter chatPressenter;
    private FloatingActionButton fab;
    private EditText editText;
    private RecyclerView chatListView;
    private ChatAdapter chatAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatPressenter = new ChatPressenter(this, this);
        setContentView(R.layout.activity_chat);

        fab = findViewById(R.id.fab);
        editText = findViewById(R.id.text_input_message);
        chatListView = findViewById(R.id.content_chat_listview);

        fab.setEnabled(false);
        editText.addTextChangedListener(getTextWatcher());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(editText.getText().toString());
            }
        });

        chatPressenter.initDialogFlow();
    }

    private void sendMessage(String text) {
        chatPressenter.sendMessage(text);
    }

    private TextWatcher getTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //not implemented
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 1){
                    fab.setEnabled(true);
                    fab.setColorFilter(Color.GREEN);
                }else{
                    fab.setEnabled(false);
                    fab.setColorFilter(Color.WHITE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //not implemented§
            }
        };
    }

    @Override
    public void setAdapter(ChatAdapter chatAdapter) {
        this.chatAdapter = chatAdapter;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        chatListView.setLayoutManager(linearLayoutManager);
        chatListView.setHasFixedSize(true);
        chatListView.setAdapter(chatAdapter);
    }

    @Override
    public void navigateToArScene() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void clearEditText() {
        editText.getText().clear();
        chatListView.smoothScrollToPosition(chatAdapter.getItemCount());
    }
}