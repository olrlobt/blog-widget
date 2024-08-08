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
    @Setter
    private String siteName;
    private final PostingBase postingBase;
    @Setter
    private Watermark watermark;

    public Posting(String thumbnail, String title, String content, LocalDate publishedTime, String url,
                   PostingBase postingBase) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.content = content;
        this.publishedTime = DateUtils.toString(publishedTime);
        this.url = url;
        this.postingBase = postingBase;
    }

    public Posting(String thumbnail, String title, String content, String publishedTime, String url,
                   PostingBase postingBase) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.content = content;
        this.publishedTime = publishedTime;
        this.url = url;
        this.postingBase = postingBase;
    }

    public static Posting createNoPosting() {
        String thumbnail = BlogInfo.NOT_FOUND.getBlogThumb();
        String title = BlogInfo.NOT_FOUND.getBlogName();
        String url = BlogInfo.NOT_FOUND.getBlogUrl();
        return new Posting(thumbnail, title, "", "", url, PostingBase.BlogPosting);
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
