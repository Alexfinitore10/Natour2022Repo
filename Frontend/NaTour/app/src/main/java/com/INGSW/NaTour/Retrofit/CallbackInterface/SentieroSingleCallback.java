/*
    INGSW2122_N_34 FRANCESCO CICCARELLI N86003285, ALEX CIACCIARELLA N86003179
*/

package com.INGSW.NaTour.Retrofit.CallbackInterface;

import com.INGSW.NaTour.Model.Sentiero;

public interface SentieroSingleCallback {

    void onSuccessResponse(boolean response);
    void onSuccess(Sentiero sentiero);
    void onFailure(Throwable throwable);

}
