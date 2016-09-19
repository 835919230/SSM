package com.hc;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 诚 on 2016/9/18.
 */
public class NormalTest {

    private int fib(int n) {
        if (n < 0) throw new IllegalArgumentException("Must > 0");
        if (n == 0) return 0;
        if (n == 1) return 1;
        int rel = fib(n-1) + fib(n-2);

        return rel;
    }

    @Test
    public void testElapsed() {
        int rel;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            rel = fib(40);
        }
        long now = System.currentTimeMillis();
        System.out.println((now - startTime)*1.0d/5d);
    }

    @Test
    public void test() throws ParseException {
        System.out.println(new Date());
        Date parse = DateFormat.getDateInstance().parse("2016-05-16 06-14-22");
        System.out.println(parse);
    }
}
