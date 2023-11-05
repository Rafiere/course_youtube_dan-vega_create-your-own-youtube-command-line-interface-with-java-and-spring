package dev.piccodev.youtubecli.config;

import dev.piccodev.youtubecli.client.YouTubeDataClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration(proxyBeanMethods = false)
public class ClientConfig {

    @Bean
    public HttpServiceProxyFactory httpServiceProxyFactory() {

        WebClient webClient = WebClient.builder()
                .baseUrl("https://www.googleapis.com/youtube/v3")
                .build();

        return HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient)).build();
    }

    @Bean
    public YouTubeDataClient youTubeDataClient(HttpServiceProxyFactory httpServiceProxyFactory) {
        return httpServiceProxyFactory.createClient(YouTubeDataClient.class);
    }
}
