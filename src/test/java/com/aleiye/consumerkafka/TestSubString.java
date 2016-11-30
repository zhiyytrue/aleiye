package com.aleiye.consumerkafka;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

/**
 * Created by aric on 2016/10/26.
 */
public class TestSubString {
    @Test
    public void testsub(){
        String str = StringUtils.replace(StringUtils.substring("1474611636.995492", 0, 14),".","");
        System.out.println(str);
    }

    }

