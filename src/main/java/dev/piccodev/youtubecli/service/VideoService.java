package dev.piccodev.youtubecli.service;

import dev.piccodev.youtubecli.client.YouTubeDataClient;
import dev.piccodev.youtubecli.config.YouTubeConfigProps;
import dev.piccodev.youtubecli.model.SearchListResponse;
import dev.piccodev.youtubecli.model.Video;
import dev.piccodev.youtubecli.model.VideoListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService {

    private static final Logger logger = LoggerFactory.getLogger(VideoService.class);
    private final List<Video> videos = new ArrayList<>();
    private final YouTubeDataClient client;
    private final YouTubeConfigProps configProps;

    public VideoService(YouTubeDataClient client, YouTubeConfigProps configProps) {
        this.client = client;
        this.configProps = configProps;
        this.loadAllVideosThisYear("", LocalDate.now().getYear());
    }

    public void loadAllVideosThisYear(String pageToken, int year){
        SearchListResponse searchList = client.searchByPublishedAfter(configProps.channelId(), configProps.key(), 50, pageToken, year + "-01-01T00:00:00Z");

        searchList.items().stream()
                .filter(result -> result.id().kind().equals("youtube#video"))
                .map(result -> client.getVideo(result.id().videoId(), configProps.key()))
                .map(VideoListResponse::items)
                .forEach(videos::addAll);

        //Chamará esse método de forma recursiva até não existir mais páginas.
        if(searchList.nextPageToken() != null && !searchList.nextPageToken().isEmpty()){
            loadAllVideosThisYear(searchList.nextPageToken(), year);
        }
    }

    public List<Video> findRecent(Integer max) {
        return videos.stream().limit(max).toList();
    }

    public List<Video> findAllByYear(Integer year) {

        return videos.stream()
                .filter(video -> video.snippet().publishedAt().getYear() == year)
                .toList();
    }
}
