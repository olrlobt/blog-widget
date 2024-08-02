package olrlobt.githubtistoryposting.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import olrlobt.githubtistoryposting.utils.DateUtils;

@Getter
@AllArgsConstructor
public class Posting {

    private final String thumbnail;
    private final String title;
    private String content;
    private String publishedTime;
    private String footer;
    private final PostingType postingType;

    public Posting(String thumbnail, String title, String content, LocalDate publishedTime, String footer,
                   PostingType postingType) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.content = content;
        this.publishedTime = DateUtils.toString(publishedTime);
        this.footer = footer;
        this.postingType = postingType;
    }

    public static Posting createNoPosting() {
        String thumbnail = BlogInfo.NOT_FOUND.getBlogThumb();
        String title = BlogInfo.NOT_FOUND.getBlogName();
        String footer = BlogInfo.NOT_FOUND.getBlogUrl();
        return new Posting(thumbnail, title, "", "", footer, PostingType.BlogPosting);
    }

    @Override
    public String toString() {
        return "Posting{" +
                "title='" + title + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", date=" + publishedTime +
                '}';
    }
}
