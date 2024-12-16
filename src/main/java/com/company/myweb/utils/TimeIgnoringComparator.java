package com.company.myweb.utils;

import lombok.Singular;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Comparator;
import java.util.Date;

@Component
public class TimeIgnoringComparator implements Comparator<Date> {
    public int compare(Date d1, Date d2) {
        if (d1.getYear() != d2.getYear())
            return d1.getYear() - d2.getYear();
        if (d1.getMonth() != d2.getMonth())
            return d1.getMonth() - d2.getMonth();
        return d1.getDate() - d2.getDate();
    }
}