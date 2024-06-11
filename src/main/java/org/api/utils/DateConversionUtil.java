package org.api.utils;

import java.time.LocalDate;
import java.sql.Date;

public class DateConversionUtil {
    public static Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }
}
