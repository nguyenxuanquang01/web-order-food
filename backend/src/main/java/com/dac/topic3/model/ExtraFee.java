package com.dac.topic3.model;

public class ExtraFee {
    private double feeShip;
    private double voucher;
    private double fee;

    public double getFeeShip() {
        return feeShip;
    }

    public void setFeeShip(double feeShip) {
        this.feeShip = feeShip;
    }

    public double getVoucher() {
        return voucher;
    }

    public void setVoucher(double voucher) {
        this.voucher = voucher;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "ExtraFee{" +
                "feeShip=" + feeShip +
                ", voucher=" + voucher +
                ", fee=" + fee +
                '}';
    }
}
