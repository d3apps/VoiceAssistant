package com.dennisdavydov.voiceassistant;

import android.icu.text.DateFormatSymbols;
import android.support.v4.util.Consumer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AI {

    public static void getAnswer(String userQuestion, final Consumer<String> callBack){

        userQuestion = userQuestion.toLowerCase();

        Map<String,String> dataBase = new HashMap<String,String>(){{
            put("привет" , "И вам здрасте! :)");
            put("как дела" , "Да вроде ничего.");
            put("чем занимаешься" , "Отвечаю на дурацкие вопросы :)");
            put("как тебя зовут" , "Я - голосовой помощник Вика");
            put("кто тебя создал" , "Денис Давыдов меня создал");
            put("есть ли жизнь на марсе" ,
                    "Есть ли жизнь на Марсе, нет ли жизни на Марсе науке это неизвестно");
            put("кто президент России" , "президент России Путин Владимир Владимирович");
            put("какого цвета небо" , "небо голубого цвета ");




        }};

        final ArrayList<String> answers = new ArrayList<>();

        for (String databaseQuestion : dataBase.keySet()){
         if (userQuestion.contains(databaseQuestion)){
             answers.add(dataBase.get(databaseQuestion));
         }
        }


        Locale myLocale = new Locale("ru","RU");
        DateFormat dateFormat = new SimpleDateFormat("EEEE d MMMM", myLocale);
        String currentDate = dateFormat.format(new Date());
        DateFormat timeFormat = new SimpleDateFormat("H:mm",myLocale);
        String currentTime = timeFormat.format(new Date());

        if (userQuestion.contains("какой сегодня день")) {
            answers.add("Сегодня " + currentDate);
        }

        if (userQuestion.contains("сколько сейчас времени")) {
            answers.add("Сейчас " + currentTime);
        }

        Pattern cityPattern =
                Pattern.compile("какая погода в городе (\\p{L}+)",Pattern.CASE_INSENSITIVE);
        Matcher matcher = cityPattern.matcher(userQuestion);
        if (matcher.find()){
            String cityName = matcher.group(1);
            Weather.get(cityName, new Consumer<String>() {
                @Override
                public void accept(String s) {
                    answers.add(s);
                    callBack.accept(String.join(", ", answers));
                }
            });
        }else if (userQuestion.contains("расскажи афоризм")) {
            Forismatic.get(new Consumer<String>() {
                @Override
                public void accept(String s) {
                    answers.add(s);
                    callBack.accept(String.join(", ", answers));
                }
            });
        }else {
            if (answers.isEmpty()){
            callBack.accept("OK");
            return;
            }
            callBack.accept(String.join(", ", answers));
        }







    }
}
