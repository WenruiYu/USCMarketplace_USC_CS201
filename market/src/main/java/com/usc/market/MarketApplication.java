package com.usc.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.usc.market.util.LogThread;

@SpringBootApplication
public class MarketApplication {

    public static void main(String[] args) {
        LogThread.startLogThread();
        SpringApplication.run(MarketApplication.class, args);
    }

}
