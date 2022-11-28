package com.INGSW.NaTour.TestDataSentieroToUI;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class TestDataSentieroToUI {
/*
    Strategia White Box - CGF All Branch Coverage
    Difficoltà:
        1) Giusto (1...3)
        2) Sbagliato (<= 0 and >=4)
    Durata:
        1) Giusto (>0)
        2) Sbagliato (<=0)
 */

    SentieroAdapterStub sentieroAdapterStub;

    @Before
    public void setUp(){
        sentieroAdapterStub = new SentieroAdapterStub();
    }

    @After
    public void tearDown(){
        sentieroAdapterStub = null;
    }

    @Test
    public void testGiustoFacileGiusto() { //1,2,3,7,8,9,10,12
        ArrayList<String> array = new ArrayList<String>(Arrays.asList("Facile","02:20"));
        assertEquals(array, sentieroAdapterStub.convertDataSentieroToUI(1,140));
    }

    @Test
    public void testGiustoMedioGiusto() {  //1,2,4,7,8,9,10,12
        ArrayList<String> array = new ArrayList<String>(Arrays.asList("Medio","02:20"));
        assertEquals(array, sentieroAdapterStub.convertDataSentieroToUI(2,140));
    }

    @Test
    public void testGiustoDifficileGiusto() {  //1,2,5,7,8,9,10,12
        ArrayList<String> array = new ArrayList<String>(Arrays.asList("Difficile","02:20"));
        assertEquals(array, sentieroAdapterStub.convertDataSentieroToUI(3,140));
    }

    @Test
    public void testGiustoSbagliato() { //1,2,3,7,11,12
        ArrayList<String> array = new ArrayList<String>(Arrays.asList("Facile",null));
        assertEquals(array, sentieroAdapterStub.convertDataSentieroToUI(1,-10));
    }

    @Test
    public void testSbagliatoGiusto() { //1,2,6,7,8,9,10,12
        ArrayList<String> array = new ArrayList<String>(Arrays.asList(null,"02:20"));
        assertEquals(array, sentieroAdapterStub.convertDataSentieroToUI(-1,140));
    }

}