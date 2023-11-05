package dev.piccodev.youtubecli.client;

import dev.piccodev.youtubecli.model.SearchListResponse;
import dev.piccodev.youtubecli.model.VideoListResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface YouTubeDataClient {

    @GetExchange("/search?part=id&channelId={channelId}&order=date&maxResults={maxResults}&key={key}&pageToken={pageToken}")
    SearchListResponse search(@PathVariable String channelId, @PathVariable String key, @PathVariable Integer maxResults, @PathVariable String pageToken);

    @GetExchange("/videos?part=id&part=statistics&part=snippet&id={videoId}&key={key}")
    VideoListResponse getVideo(@PathVariable String videoId, @PathVariable String key);

    @GetExchange("/search?part=id&channelId={channelId}&order=date&maxResults={maxResults}&key={key}&pageToken={pageToken}&publishedAfter={publishedAfter}")
    SearchListResponse searchByPublishedAfter(@PathVariable String channelId, @PathVariable String key, @PathVariable Integer maxResults, @PathVariable String pageToken, @PathVariable String publishedAfter);

}