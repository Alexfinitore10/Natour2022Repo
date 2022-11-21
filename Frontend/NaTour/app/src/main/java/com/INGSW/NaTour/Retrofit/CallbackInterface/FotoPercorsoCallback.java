package com.INGSW.NaTour.Retrofit.CallbackInterface;

import com.INGSW.NaTour.Model.FotoPercorso;

import java.util.List;

public interface FotoPercorsoCallback {

    void onSuccessResponse(boolean response);
    void onSuccessList(List<FotoPercorso> photos);
    void onFailure(Throwable throwable);

}
