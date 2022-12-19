package com.Project;

import javafx.scene.control.TextField;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {

    /*====================================== Time Validity ======================================*/

    public static Boolean timeValidity(String time){
        //This method checks if the entered is valid or not
        boolean status = false;

        int[] timeParts = new int[3];

        for (int i = 0; i < timeParts.length; i++)
            timeParts [i] = Integer.parseInt(time.split(":")[i]);

        if (timeParts[0]>=0 && timeParts[0] <=23) {
            if (timeParts[1] >= 0 && timeParts[1] <=60) {
                if (timeParts[2] >=0 && timeParts[2] <= 60) {
                    status = true;
                }
            }
        }

        return status;
    }

    //======================================================================================//

    /*====================================== Set Time ======================================*/

    //This method set's the current time
    public static void setTime(TextField field){
        //Setting current time in Time Field of Vehicle Entry Pane
        Date date = new Date();
        long time = date.getTime();

        field.setText(convertTimeToString(time));
    }

    //==========================================================================================//

    /*================================= Convert Time To String =================================*/


    //This method converts the time to string type
    public static String convertTimeToString(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        if (timeValidity(sdf.format(time)))
            return sdf.format(time);
        else
            return "";
    }

    //======================================================================================//

    /*====================================== Set Date ======================================*/


    //This method set the current date
    public static String setDate(){
        //Setting current date
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
}
