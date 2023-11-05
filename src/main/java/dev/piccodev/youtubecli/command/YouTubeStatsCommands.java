package dev.piccodev.youtubecli.command;

import dev.piccodev.youtubecli.model.TeamTabRow;
import dev.piccodev.youtubecli.model.Video;
import dev.piccodev.youtubecli.service.CommandService;
import dev.piccodev.youtubecli.service.ReportService;
import dev.piccodev.youtubecli.service.VideoService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.TableBuilder;

import java.time.LocalDate;
import java.util.List;

@ShellComponent
public class YouTubeStatsCommands {

    //O "value" é o texto que aparecerá na ajuda do comando.
    private final VideoService videoService;
    private final CommandService commandService;
    private final ReportService reportService;

    public YouTubeStatsCommands(VideoService videoService, CommandService commandService, ReportService reportService) {
        this.commandService = commandService;
        this.videoService = videoService;
        this.reportService = reportService;
    }

    @ShellMethod(key = "recent", value = "List recent videos by max count")
    public void recent(@ShellOption(defaultValue = "100") Integer max){

        List<Video> videos = videoService.findRecent(max);

        TableBuilder tableBuilder = commandService.listToArrayTableModel(videos);
        System.out.println(tableBuilder.build().render(150));
    }

    @ShellMethod(key = "filter-by-year", value = "List videos by year")
    public void byYear(@ShellOption(defaultValue = "2023") Integer year){

        List<Video> videos = videoService.findAllByYear(year);

        TableBuilder tableBuilder = commandService.listToArrayTableModel(videos);
        System.out.println(tableBuilder.build().render(150));
    }

    @ShellMethod(key = "report", value = "Run a report based on all of the videos for the current year")
    public void report(){

        List<Video> videos = videoService.findAllByYear(LocalDate.now().getYear());

        List<TeamTabRow> teamTabRows = reportService.videosToTeamTabRows(videos);
        teamTabRows.forEach(TeamTabRow::print);
    }
}
