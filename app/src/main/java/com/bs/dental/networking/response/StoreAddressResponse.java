package com.bs.dental.networking.response;

import com.bs.dental.model.StoreDM;
import com.bs.dental.networking.BaseResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashraful on 12/7/2015.
 */
public class StoreAddressResponse extends BaseResponse {

private List<StoreDM> PickupPoints=new ArrayList<>();


    public List<StoreDM> getPickupPoints() {
        return PickupPoints;
    }

    public void setPickupPoints(List<StoreDM> pickupPoints) {
        PickupPoints = pickupPoints;
    }
}
