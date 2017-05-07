package com.example.lenovo.inkredoemi.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Lenovo on 07-05-2017.
 */

public class EMIRecord extends Model {
    @Column(name = "Tenure")
    int tenure;
    @Column(name = "EMI")
    double emi;
    @Column(name = "Total")
    double total;
    @Column(name = "PhoneNumber")
    String number;
    @Column(name = "UUID",unique = true)
    String UUID;
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getTenure() {
        return tenure;
    }

    public void setTenure(int tenure) {
        this.tenure = tenure;
    }

    public double getEmi() {
        return emi;
    }

    public void setEmi(double emi) {
        this.emi = emi;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public List<EMIRecord> getAllRecords() {
        return new Select()
                .from(EMIRecord.class)
                .orderBy("UUID ASC")
                .execute();
    }

}
