package com.INGSW.NaTour.Retrofit.CallbackInterface;


import com.INGSW.NaTour.Model.Sentiero;

import java.util.List;

public interface SentieroCallback {

    void onSuccessResponse(boolean response);
    void onSuccessList(List<Sentiero> sentieri);
    void onFailure(Throwable throwable);

}
