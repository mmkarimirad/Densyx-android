package com.bs.dental.model;

import java.util.List;

/**
 * Created by mmkr on 2/5/2019 --- for add product list specially for densyx doctors.
 */
public class ProductTurnObject {
    private int draw;
    private int recordsTotal;
    private int startRow;
    private int recordsFiltered;
    private List<ProductTurn> data;

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

    public List<ProductTurn> getData() {
        return data;
    }

    public void setData(List<ProductTurn> data) {
        this.data = data;
    }
}
