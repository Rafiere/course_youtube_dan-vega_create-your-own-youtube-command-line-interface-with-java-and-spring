package dev.piccodev.youtubecli.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("youtube")
public record YouTubeConfigProps(String channelId, String key) {
}
