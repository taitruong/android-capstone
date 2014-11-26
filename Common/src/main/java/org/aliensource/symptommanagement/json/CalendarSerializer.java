package org.aliensource.symptommanagement.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.aliensource.symptommanagement.DateTimeUtils;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by ttruong on 25-Nov-14.
 */
public class CalendarSerializer extends JsonSerializer<Calendar> {

    @Override
    public void serialize(Calendar calendar, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        System.out.println(">>>> serializing " + DateTimeUtils.FORMAT_DDMMYYYY_HHMM.format(calendar.getTime()));
        jgen.writeString(DateTimeUtils.FORMAT_DDMMYYYY_HHMM.format(calendar.getTime()));
    }

}
