package io.github.jitendra9873.smartlanes.data;

import java.util.Date;

public class Transaction {

    private long trId;

    public String trToll;

    public String vehicleId;

    public double trFee;

    public Date trTime;

    public Transaction(long id, String toll, String vid, double fee, Date dtime) {
        this.trId = id;
        this.trToll = toll;
        this.vehicleId = vid;
        this.trFee = fee;
        this.trTime = dtime;
    }
}
