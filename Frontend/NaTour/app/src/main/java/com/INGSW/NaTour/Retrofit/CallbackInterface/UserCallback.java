package com.INGSW.NaTour.Retrofit.CallbackInterface;

import com.INGSW.NaTour.Model.User;
import java.util.List;

public interface UserCallback {

    void onSuccess(User user);
    void onSuccessResponse(boolean response);
    void onSuccessList(List<User> users);
    void onFailure(Throwable throwable);

}
