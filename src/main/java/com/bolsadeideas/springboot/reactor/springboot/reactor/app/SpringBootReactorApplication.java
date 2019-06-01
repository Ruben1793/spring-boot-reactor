package com.bolsadeideas.springboot.reactor.springboot.reactor.app;

import com.bolsadeideas.springboot.reactor.springboot.reactor.app.models.Usuario;
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
        Flux<Usuario> nombres = Flux.just("Andres", "Pedro", "Maria", "Juan")
                .map(nombre ->  new Usuario(nombre.toUpperCase(), null))
                .doOnNext(usuario ->{
                    if (usuario == null){
                        throw new RuntimeException("Nombres no pueden ser vacios");
                    }
                    System.out.println(usuario.toString());
                }).map(usuario -> {
                    String nombre =  usuario.getNombre().toLowerCase();
                    usuario.setNombre(nombre);
                    return usuario;
                });

        nombres.subscribe(usuario -> Log.info(usuario.getNombre()),
                error -> Log.error(error.getMessage(),
                        (Runnable) () -> {
                            Log.info("Ha finalizado la ejecucion del observable con exito!");
                        }));

    }
}
