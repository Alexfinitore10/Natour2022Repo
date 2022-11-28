package com.INGSW.NaTour.TestDataSentieroToUI;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class SentieroAdapterStub {


    public ArrayList<String> convertDataSentieroToUI(int difficolta, int durata){
        String format, diff;//1

        switch (difficolta){//2
            case 1:
                diff = "Facile";//3
                break;
            case 2:
                diff = "Medio";//4
                break;
            case 3:
                diff = "Difficile";//5
                break;
            default: diff = null;//6
        }

        if(durata > 0){//7
            int hour = durata/60;//8
            int minute = durata - (hour*60);//9
            format = String.format("%02d:%02d",hour,minute);//10
        }else format = null;//11

        return new ArrayList<String>(Arrays.asList(diff,format));//12
    }

}
