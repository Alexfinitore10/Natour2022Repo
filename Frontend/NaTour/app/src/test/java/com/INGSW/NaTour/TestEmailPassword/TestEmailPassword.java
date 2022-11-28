package com.INGSW.NaTour.TestEmailPassword;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestEmailPassword {

/*
    Strategia Black Box - WECT

    variabili : email, pass1, pass2

    Per password giusta si intende una password che segui questi standard di sicurezza:
        Deve avere almeno 8 caratteri, 1 maiuscola, 1 carattere speciale e 1 numero


    email:
        CEE1) email valida          Esempio: "alex@gmail.com"
        CEE2) email non valida      Esempio: "@gmail.com"
        CEE3) email nulla           Esempio: null
        CEE4) email vuota           Esempio: ""
    password 1:
        CEP1) pass valida           Esempio: "Pass@1234"
        CEP2) pass non valida       Esempio: "pass"
        CEP3) pass vuota            Esempio: ""
        CEP4) pass null             Esempio: null
    password 2:
        CEPP1) pass valida          Esempio: "Pass@1234"
        CEPP2) pass non valida      Esempio: "pass"
        CEPP3) pass vuota           Esempio: ""
        CEPP4) pass null            Esempio: null

    Caso 1) Email vuota, pass1 vuota, pass2 vuota //false
    Caso 2) Email vuota, pass1 vuota, pass2 piena(valida) // false
    Caso 3) Email vuota, pass1 piena (valida), pass2 vuota //false
    Caso 4) Email vuota, pass1 piena (valida), pass2 piena (valida) //false
    Caso 5) Email vuota, pass1 piena (non valida), pass2 piena (non valida) // false
    Caso 6) Email vuota, pass1 piena (valida), pass2 (non valida) //false
    Caso 7) Email vuota, pass1 piena (non valida), pass2 (valida) //false
    Caso 8) Email piena (valida), pass1 vuota, pass2 vuota //false
    Caso 9) Email piena (valida), pass1 piena (valida), pass2 piena (valida) //true
    Caso 10) Email piena (non valida), pass1 piena (valida), pass2 piena (valida) //false
 */

    SignUpPresenterStub signUpPresenterStub;

    @Before
    public void init(){
        signUpPresenterStub = new SignUpPresenterStub();
    }

    @After
    public void clear(){
        signUpPresenterStub = null;
    }

    @Test
    public void testCorretto() {
        assertTrue(signUpPresenterStub.isEmailCorrectAndPasswordCorrentandEqual("alex@gmail.com", "Pass@1234", "Pass@1234"));
    }

    @Test
    public void testInvalidEmail() {
        assertFalse(signUpPresenterStub.isEmailCorrectAndPasswordCorrentandEqual("gmail.com", "Pass@1234", "Pass@1234"));
    }

    @Test
    public void testEmailNull() {
        assertFalse(signUpPresenterStub.isEmailCorrectAndPasswordCorrentandEqual(null, "Pass@1234", "Pass@1234"));
    }

    @Test
    public void testEmailBlank() {
        assertFalse(signUpPresenterStub.isEmailCorrectAndPasswordCorrentandEqual("", "Pass@1234", "Pass@1234"));
    }

    @Test
    public void testInvalidPass1() {
        assertFalse(signUpPresenterStub.isEmailCorrectAndPasswordCorrentandEqual("alex@gmail.com", "pass", "Pass@1234"));
    }

    @Test
    public void testBlankPass1() {
        assertFalse(signUpPresenterStub.isEmailCorrectAndPasswordCorrentandEqual("alex@gmail.com", "", "Pass@1234"));
    }

    @Test
    public void testNullPass1() {
        assertFalse(signUpPresenterStub.isEmailCorrectAndPasswordCorrentandEqual("alex@gmail.com", null, "Pass@1234"));
    }

    @Test
    public void testInvalidPass2() {
        assertFalse(signUpPresenterStub.isEmailCorrectAndPasswordCorrentandEqual("alex@gmail.com", "Pass@1234", "pass"));
    }

    @Test
    public void testBlankPass2() {
        assertFalse(signUpPresenterStub.isEmailCorrectAndPasswordCorrentandEqual("alex@gmail.com", "Pass@1234", ""));
    }

    @Test
    public void testNullPass2() {
        assertFalse(signUpPresenterStub.isEmailCorrectAndPasswordCorrentandEqual("alex@gmail.com", "Pass@1234", "pass"));
    }

}