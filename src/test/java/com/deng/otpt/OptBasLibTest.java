package com.deng.otpt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.deng.otpt.controller.OptBasLib;

@SpringBootTest
public class OptBasLibTest {
    @Autowired
    OptBasLib optBasLib;

    @Test
    void testTestlib() {
        System.out.println(optBasLib.getOtpCode());
    }
}
