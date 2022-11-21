package com.INGSW.NaTour.Retrofit.CallbackInterface;

import com.INGSW.NaTour.Model.MapPoint;

import java.util.List;

public interface TracciatoSentieroCallback {

    void onSuccessResponse(boolean response);
    void onSuccessList(List<MapPoint> tracciato);
    void onFailure(Throwable throwable);
}
