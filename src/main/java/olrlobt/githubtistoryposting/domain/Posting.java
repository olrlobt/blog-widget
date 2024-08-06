package olrlobt.githubtistoryposting.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import olrlobt.githubtistoryposting.utils.DateUtils;

@Getter
@AllArgsConstructor
public class Posting {

    private final String thumbnail;
    @Setter
    private String blogImage;
    @Setter
    private String author;
    private final String title;
    private String content;
    private String publishedTime;
    private String url;
    private final PostingType postingType;

    public Posting(String thumbnail, String title, String content, LocalDate publishedTime, String url,
                   PostingType postingType) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.content = content;
        this.publishedTime = DateUtils.toString(publishedTime);
        this.url = url;
        this.postingType = postingType;
    }

    public Posting(String thumbnail, String title, String content, String publishedTime, String url,
                   PostingType postingType) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.content = content;
        this.publishedTime = publishedTime;
        this.url = url;
        this.postingType = postingType;
    }

    public static Posting createNoPosting() {
        String thumbnail = BlogInfo.NOT_FOUND.getBlogThumb();
        String title = BlogInfo.NOT_FOUND.getBlogName();
        String url = BlogInfo.NOT_FOUND.getBlogUrl();
        return new Posting(thumbnail, title, "", "", url, PostingType.BlogPosting);
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
