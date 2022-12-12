/*
    INGSW2122_N_34 FRANCESCO CICCARELLI N86003285, ALEX CIACCIARELLA N86003179
*/

package com.INGSW.NaTour.Retrofit.CallbackInterface;

import com.INGSW.NaTour.Model.Email;
import java.util.List;

public interface EmailCallback {

    void onSuccessResponse(boolean response);
    void onSuccessList(List<Email> emailList);
    void onFailure(Throwable throwable);
}
