package pmm.pbm.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.Formatter;

import lombok.Value;

@Value
public class DateFormatter implements Formatter<Date> {
    private final String pattern;

    @Override
    public String print(Date object, Locale locale) {
        return new SimpleDateFormat(pattern, locale).format(object);
    }

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        return new SimpleDateFormat(text, locale).parse(text);
    }

}
