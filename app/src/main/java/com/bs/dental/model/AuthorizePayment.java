package com.bs.dental.model;

/**
 * Created by bs-110 on 1/8/2016.
 */
public class AuthorizePayment {
    private int CreditCardId;
    private String CreditCardNumber;
    private int OrderId;
    private int CreditCardExpireYear;
    private int CreditCardExpireMonth;
    private String CreditCardCvv2;
    private String CardHolderName;

    public int getCreditCardId() {
        return CreditCardId;
    }

    public void setCreditCardId(int creditCardId) {
        CreditCardId = creditCardId;
    }

    public String getCreditCardNumber() {
        return CreditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        CreditCardNumber = creditCardNumber;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getCreditCardExpireYear() {
        return CreditCardExpireYear;
    }

    public void setCreditCardExpireYear(int creditCardExpireYear) {
        CreditCardExpireYear = creditCardExpireYear;
    }

    public int getCreditCardExpireMonth() {
        return CreditCardExpireMonth;
    }

    public void setCreditCardExpireMonth(int creditCardExpireMonth) {
        CreditCardExpireMonth = creditCardExpireMonth;
    }

    public String getCreditCardCvv2() {
        return CreditCardCvv2;
    }

    public void setCreditCardCvv2(String creditCardCvv2) {
        CreditCardCvv2 = creditCardCvv2;
    }

    public String getCardHolderName() {
        return CardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        CardHolderName = cardHolderName;
    }
}
