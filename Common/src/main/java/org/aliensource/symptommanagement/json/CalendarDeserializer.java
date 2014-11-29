package org.aliensource.symptommanagement.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import org.aliensource.symptommanagement.DateTimeUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by ttruong on 25-Nov-14.
 */
public class CalendarDeserializer extends JsonDeserializer<Calendar> {

    @Override
    public Calendar deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        String dateStr = jsonParser.getText();
        try {
            Date date = DateTimeUtils.FORMAT_DDMMYYYY_HHMM.parse(dateStr);
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTimeInMillis(date.getTime());
            return cal;
        } catch (ParseException ex) {
            throw new IOException("Cannot parse " + dateStr, ex);
        }
    }
}