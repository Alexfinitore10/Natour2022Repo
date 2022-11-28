package com.INGSW.NaTour.TestEmailPassword;

import java.util.regex.Pattern;

public class SignUpPresenterStub {

    public boolean isEmailCorrectAndPasswordCorrentandEqual(String email, String pass1, String pass2){
        String regexEmailValid = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        String regexStrongPasswordValid = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*\\=\\-\\_\\:\\.\\,\\\"\\€\\/\\\\\\^]).{8,}$";

        Pattern patternEmail = Pattern.compile(regexEmailValid);
        Pattern patternPassword = Pattern.compile(regexStrongPasswordValid);

        if (email == null || pass1==null || pass2==null){
            return false;
        }

        if(email.isEmpty() || pass1.isEmpty() || pass2.isEmpty()){
            return false;
        }

        if(!patternEmail.matcher(email).matches()){
            return false;
        }

        if(!patternPassword.matcher(pass1).matches() || !patternPassword.matcher(pass2).matches()){
            return false;
        }

        if(!pass1.equals(pass2)){
            return false;
        }

        return true;
    }

}
