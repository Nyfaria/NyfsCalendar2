package com.nyfaria.nyfscalendar.util;

import com.nyfaria.nyfscalendar.config.NCConfig;
import net.minecraft.client.Minecraft;

import java.time.Year;

public class DateUtils {

    public static long getDay(){

//        long currentDay = (getStartDay() + serverAge()) % getDaysInYear();
//        //int counter = 0;
//        for(int k = 0; k < DAYSINMONTHS.stream().count(); k++){
//            //counter += DAYSINMONTHS.get(k);
//            if(DAYSINMONTHS.get(k) > currentDay){
//                return currentDay;
//            }
//            else
//            {
//                currentDay -= DAYSINMONTHS.get(k);
//            }
//
//
//        }
        return 0;
    }
    public static long serverAge(){
        return Minecraft.getInstance().level.getDayTime() / 24000L % 2147483647L;
    }
    public static long getStartDay(){
        return NCConfig.INSTANCE.startDay.get();
    }
    public static long getStartMonth(){
        return NCConfig.INSTANCE.startMonth.get();
    }
    public static long getStartYear(){
        return NCConfig.INSTANCE.startYear.get();
    }
    public static long getDaysInYear(int year){
        return Year.of(year).length();
    }


}
