package org.api.utils;

import java.sql.Date;
import java.time.LocalDate;

public class DateConversionUtil {
    public static Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }
}
