package com.bolsadeideas.springboot.reactor.springboot.reactor.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

    private Logger Log = LoggerFactory.getLogger(SpringBootReactorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootReactorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Flux<String> nombres = Flux.just("Andres", "Pedro", "Maria" ,"Juan")
                .doOnNext( e -> {
                    if (e.isEmpty()){
                        throw new RuntimeException("Nombres no pueden ser vacios");
                    }
                    System.out.println(e);
                });

        nombres.subscribe(e -> Log.info(e),
                error -> Log.error(error.getMessage(),
                        (Runnable) () -> {
                            Log.info("Ha finalizado la ejecucion del observable con exito!");
                        }));

    }
}
