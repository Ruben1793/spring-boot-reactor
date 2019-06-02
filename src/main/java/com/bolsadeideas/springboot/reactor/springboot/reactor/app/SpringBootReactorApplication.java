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
        Flux<String> nombres = Flux.just("Andres Guzman", "Pedro Fulano",
                "Maria Fulana", "Diego Sultano", "Juan Mengano", "Bruce Lee", "Bruce Willis");

         Flux<Usuario> usuarios = nombres.map(nombre ->  new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase()))
                .filter(usuario -> usuario.getNombre().toLowerCase().equals("bruce"))
                .doOnNext(usuario ->{
                    if (usuario == null){
                        throw new RuntimeException("Nombres no pueden ser vacios");
                    }
                    System.out.println(usuario.getNombre() + ' ' + usuario.getApellido());
                }).map(usuario -> {
                    String nombre =  usuario.getNombre().toLowerCase() + " " + usuario.getApellido().toLowerCase();
                    usuario.setNombre(nombre);
                    return usuario;
                });

        usuarios.subscribe(usuario -> Log.info(usuario.toString()),
                error -> Log.error(error.getMessage(),
                        (Runnable) () -> {
                            Log.info("Ha finalizado la ejecucion del observable con exito!");
                        }));

    }
}
