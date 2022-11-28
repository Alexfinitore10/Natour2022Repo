package com.INGSW.NaTour.TestConvertHourandMinuteToMinute;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.INGSW.NaTour.Presenter.SentieroPresenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestConvertHourAndMinuteToMinute {

/*
    Strategia White Box - CGF All Branch Coverage
    Hour:
        1) Giusto (>=0)
        2) Sbagliato (<0)
        3) Sbagliato null
    Minute:
        1) Giusto (>=0)
        2) Sbagliato (<0)
        3) Sbagliato null

    Test1) Hour=1, Minute=10 1,2,3,5,8,14
*/

    SentieroPresenter sentieroPresenter;

    @Before
    public void setUp(){
        sentieroPresenter = new SentieroPresenter();
    }

    @After
    public void tearDown(){
        sentieroPresenter = null;
    }

    @Test
    public void testCorretto() { //1,2,3,5,8,14
        Integer expected = new Integer(70);
        assertEquals(expected, sentieroPresenter.convertHourAndMinuteToMinute(1,10));
    }

    @Test
    public void testZeroZero() { //1,2,3,4
        assertThrows(IllegalArgumentException.class, () -> sentieroPresenter.convertHourAndMinuteToMinute(0,0));
    }

    @Test
    public void testNULLNULL() { //1,2,6,7
        assertThrows(IllegalArgumentException.class, () -> sentieroPresenter.convertHourAndMinuteToMinute(null,null));
    }

    @Test
    public void testNULLMinore0() { //1,2,6,8,9,11,13
        assertThrows(IllegalArgumentException.class, () -> sentieroPresenter.convertHourAndMinuteToMinute(null,-10));
    }

    @Test
    public void testNULLGiusto() { //1,2,6,8,9,10,14
        Integer expected = new Integer(10);
        assertEquals(expected,sentieroPresenter.convertHourAndMinuteToMinute(null,10));
    }

    @Test
    public void testGiustoNULL() { //1,2,6,8,9,11,12,14
        Integer expected = new Integer(120);
        assertEquals(expected,sentieroPresenter.convertHourAndMinuteToMinute(2,null));
    }


}