package com.bs.dental.utils;

import com.bs.dental.model.PatientTurn;
import com.bs.dental.model.PatientTurnChartItem;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.List;

public class XAxisBarValueFormatter extends ValueFormatter {

    final String[] XLabels;

    public XAxisBarValueFormatter(List<PatientTurnChartItem> pol) {
        XLabels = new String[pol.size()];
        for (int i = 0; i < pol.size(); i++) {
            XLabels[i] = pol.get(i).getPersianDateTime();
        }
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        return XLabels[(int) value];
    }

}
