package com.INGSW.NaTour.Extra;

import com.INGSW.NaTour.Model.User;

public class Constants {

    //Retrofit connection
    public static final String IP_ADDRESS = "natour.ddns.net";
    public static final String PORT = "25565";

    //Cognito info
    public static int LOGIN = -1; //0 = Utente Normale, 1 = Utente con Provider, 2 = Guest, // 3 = Admin

    public static User utente=null;

}
