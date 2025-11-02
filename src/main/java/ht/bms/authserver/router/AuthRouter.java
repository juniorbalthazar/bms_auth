package ht.bms.authserver.router;

import ht.bms.authserver.service.handles.AuthHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AuthRouter {


    @Autowired
    private AuthHandler authHandler;

    @Bean
    public RouterFunction<ServerResponse> accountRoutes(){
        return RouterFunctions.route()
                .path("/api/v1", builder -> builder
                        .add(baseRoute(RequestPredicates.POST("/auth"), authHandler::login))
                        .add(baseRoute(RequestPredicates.GET("/profile"),authHandler::getProfile))
                )
                .build();
    }


    private RouterFunction<ServerResponse> baseRoute(RequestPredicate predicate, HandlerFunction<ServerResponse> handlerFunction) {
        return route(predicate, handlerFunction);
    }
}