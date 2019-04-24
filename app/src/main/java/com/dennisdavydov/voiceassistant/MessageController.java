package com.dennisdavydov.voiceassistant;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MessageController extends RecyclerView.Adapter {

    public static final int USER_MESSAGE = 0;
    public static final int ASSISTANT_MESSAGE = 1;

    public List<Message> messageList = new ArrayList<>();

    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);
        return message.isSent ? USER_MESSAGE : ASSISTANT_MESSAGE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        int layout;
        if (viewType == USER_MESSAGE){
            layout = R.layout.user_message;
        }else {
            layout = R.layout.assistant_message;
        }
       View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(layout,viewGroup,false);
        return new MessageView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int messageNumber) {
        Message message = messageList.get(messageNumber);
        ((MessageView)viewHolder).bind(message);

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
