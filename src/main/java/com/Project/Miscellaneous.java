package com.Project;

import javafx.scene.control.TextField;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Miscellaneous {
    public static Boolean timeValidity(String time){
        boolean status = false;

        int[] timeParts = new int[3];

        for (int i = 0; i < timeParts.length; i++)
            timeParts [i] = Integer.parseInt(time.split(":")[i]);

        if (timeParts[0]>=0 && timeParts[0] <=23)
            if (timeParts[1] >= 0 && timeParts[1] <=60)
                if (timeParts[2] >=0 && timeParts[2] <= 60)
                    status = true;

        return status;
    }

    //This method set's the current time
    public static long setTime(TextField field){
        //Setting current time in Time Field of Vehicle Entry Pane
        Date date = new Date();
        long time = date.getTime();

        field.setText(convertTimeToString(time));

        return time;
    }

    public static String convertTimeToString(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        if (timeValidity(sdf.format(time)))
            return sdf.format(time);
        else
            return "";
    }

    public static long convertTimeToLong(String time){
        long formattedTime = 0;

        String [] splitTime = time.split(":");

        formattedTime += Integer.parseInt(splitTime[0])* 10000L + Integer.parseInt(splitTime[1]) * 100L + Integer.parseInt(splitTime[2]);

        return formattedTime;
    }

    public static String formatTime(long time){
        String hours = "";
        String min = "";
        String sec = "";

        sec += Long.toString(time%100);
        time = time / 100;
        min = Long.toString(time%100);
        time /= 100;
        hours = Long.toString(time);

        return hours + ":" + min + ":" + sec;
    }

    public static String setDate(){
        //Setting current date
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
}
