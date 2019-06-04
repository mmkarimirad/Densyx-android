package com.bs.dental.networking.response;

import com.bs.dental.model.PaymentMethod;
import com.bs.dental.networking.BaseResponse;

import java.util.List;

/**
 * Created by Ashraful on 12/9/2015.
 */
public class PaymentMethodRetrievalResponse extends BaseResponse {
    private Object RewardPointsAmount;

    private boolean DisplayRewardPoints;
    private int RewardPointsBalance;
    private boolean UseRewardPoints;

    public List<PaymentMethod> getPaymentMethods() {
        return PaymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        PaymentMethods = paymentMethods;
    }

    private List<PaymentMethod> PaymentMethods;

    public boolean isUseRewardPoints() {
        return UseRewardPoints;
    }

    public void setUseRewardPoints(boolean useRewardPoints) {
        UseRewardPoints = useRewardPoints;
    }

    public int getRewardPointsBalance() {
        return RewardPointsBalance;
    }

    public void setRewardPointsBalance(int rewardPointsBalance) {
        RewardPointsBalance = rewardPointsBalance;
    }

    public boolean isDisplayRewardPoints() {
        return DisplayRewardPoints;
    }

    public void setDisplayRewardPoints(boolean displayRewardPoints) {
        DisplayRewardPoints = displayRewardPoints;
    }

}
