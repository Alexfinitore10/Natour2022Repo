package com.INGSW.NaTour.Retrofit.CallbackInterface;

import com.INGSW.NaTour.Model.Sentiero;

public interface OpinioneCallback {

    void onSuccessResponse(boolean response);
    void onSuccess(Sentiero sentieroUpdated);
    void onFailure(Throwable throwable);

}
