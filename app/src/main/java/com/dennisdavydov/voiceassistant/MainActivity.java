package com.dennisdavydov.voiceassistant;

import android.speech.tts.TextToSpeech;
import android.support.v4.util.Consumer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    protected Button sendButton;
    protected EditText userMessage;
    protected RecyclerView chatWindow;
    protected TextToSpeech textToSpeech;

    protected MessageController messageController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendButton = findViewById(R.id.sendButton);
        userMessage = findViewById(R.id.editText);
        chatWindow = findViewById(R.id.chatWindow);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener();

            }
        });

        messageController = new MessageController();
        chatWindow.setLayoutManager(new LinearLayoutManager(this));
        chatWindow.setAdapter(messageController);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                textToSpeech.setLanguage(new Locale("ru"));
            }
        });

        String intro = "Привет, Я - голосовой помощник Вика, Я могу отвечать на простые вопросы, " +
                "узнать погоду в нужном городе, время или рассказать афоризм, что Вас интересует?";
        messageController.messageList.add(new Message(intro, false));
        textToSpeech.speak(intro,TextToSpeech.QUEUE_FLUSH,null,null);
        messageController.notifyDataSetChanged();
        chatWindow.scrollToPosition(messageController.messageList.size()-1);


    }
    protected void onClickListener(){
        String message = userMessage.getText().toString();
        userMessage.setText("");

        messageController.messageList.add(new Message(message, true));

        AI.getAnswer(message, new Consumer<String>() {
            @Override
            public void accept(String answer) {
                messageController.messageList.add(new Message(answer, false));
                messageController.notifyDataSetChanged();
                chatWindow.scrollToPosition(messageController.messageList.size()-1);
            }
        });


    }
}
