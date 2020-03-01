package org.syphr.mythtv.commons.translate;

import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

public class DateUtilsTest
{
    @Test
    public void testToUtc()
    {
        Date date = new Date();
        System.out.println(date);

        DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println(format.format(DateUtils.toUtc(date)));
        System.out.println(format.format(date));
    }

    @Test
    public void testToLocal()
    {
        fail("Not yet implemented");
    }
}
