package com.bs.dental.model;

import java.util.List;

public class PatientTurnObject {

    private int draw;
    private int recordsTotal;
    private int startRow;
    private int recordsFiltered;
    private List<PatientTurn> data;


    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<PatientTurn> getData() {
        return data;
    }

    public void setData(List<PatientTurn> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PatientTurnObject{" +
                "draw=" + draw +
                ", recordsTotal=" + recordsTotal +
                ", startRow=" + startRow +
                ", recordsFiltered=" + recordsFiltered +
                ", data=" + data +
                '}';
    }
}
