package com.bs.dental.networking.response;

import com.bs.dental.networking.BaseResponse;

/**
 * Created by Ashraful on 11/30/2015.
 */
public class AddtoCartResponse extends BaseResponse {

    private boolean Success;
    private boolean ForceRedirect;
    private int Count;

    public boolean isForceRedirect() {
        return ForceRedirect;
    }

    public void setForceRedirect(boolean forceRedirect) {
        ForceRedirect = forceRedirect;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }


    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }


}
