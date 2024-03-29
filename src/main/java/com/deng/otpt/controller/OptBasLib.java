package com.deng.otpt.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.bastiaanjansen.otp.HMACAlgorithm;
import com.bastiaanjansen.otp.TOTP;

/**
 * 规定时间内获取otp
 */
@RestController
public class OptBasLib {

    private static int minsLeft = 5;
    private static TOTP OTP;

    public OptBasLib(@Value("${encrypted.otpSecretKey}") String otpSecretKey) {

        /**
         * 有效分钟每一分钟递减
         */
        Thread t = new Thread() {
            @Override
            public void run() {
                while (true) {

                    if (minsLeft > 0 && LocalDateTime.now().getSecond() == 0) {
                        System.out.println("count:=" + --minsLeft);
                    }

                    // sleep 一下，避免while耗尽资源
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            }
        };

        t.start();

        TOTP.Builder builder = new TOTP.Builder(otpSecretKey.getBytes());
        builder
                .withPasswordLength(6)
                .withAlgorithm(HMACAlgorithm.SHA1)
                .withPeriod(Duration.ofSeconds(30));

        OTP = builder.build();

    }

    @GetMapping("/test")
    public String getOtpCode() {
        return getOtp();
    }


    @GetMapping("update/{minutes}")
    public String updateLeftTime(@PathVariable("minutes") Integer time) {
        minsLeft = time;
        return "success";
    }

    @MessageMapping("/otp")
    @SendTo("/topic/otp")
    public String greeting(String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return getOtp();
    }

    private String getOtp() {
        if (minsLeft > 0) {
            // 过期时间
            int exSecond = 30 - LocalDateTime.now().getSecond() % 30;
            String string = String.format("otp=%s ,expires :%s S .\n Now：%s", OTP.now(), exSecond, LocalDateTime.now().toString());
            return string;
        }
        return "Unauthorization";
    }

}
