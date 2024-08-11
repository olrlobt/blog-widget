package olrlobt.githubtistoryposting.domain;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import olrlobt.githubtistoryposting.utils.DateUtils;

@Getter
@AllArgsConstructor
@Builder
public class Posting {

    private final String thumbnail;
    private String blogImage;
    private String author;
    private final String title;
    private String content;
    private String publishedTime;
    private String url;
    private String siteName;
    private final PostingBase postingBase;
    private Watermark watermark;

    public Posting(String thumbnail, String title, String content, LocalDate publishedTime, String url, String siteName,
                   PostingBase postingBase) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.content = content;
        this.publishedTime = DateUtils.toString(publishedTime);
        this.url = url;
        this.siteName = siteName;
        this.postingBase = postingBase;
    }

    public Posting(String thumbnail, String title, String url,
                   PostingBase postingBase) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.url = url;
        this.postingBase = postingBase;
    }

    public Posting(String thumbnail, String blogImage, String author, String title, String content,
                   LocalDate publishedTime,
                   String url, String siteName, PostingBase postingBase, Watermark watermark) {
        this.thumbnail = thumbnail;
        this.blogImage = blogImage;
        this.author = author;
        this.title = title;
        this.content = content;
        this.publishedTime = DateUtils.toString(publishedTime);
        this.url = url;
        this.siteName = siteName;
        this.postingBase = postingBase;
        this.watermark = watermark;
    }

    public static Posting createBlogPosting(String thumbnail, String title, String url, PostingBase postingBase) {
        return new Posting(thumbnail, title, url, postingBase);
    }

    public static Posting createNoPosting() {
        String thumbnail = BlogInfo.NOT_FOUND.getBlogThumb();
        String title = BlogInfo.NOT_FOUND.getBlogName();
        String url = BlogInfo.NOT_FOUND.getBlogUrl();
        return Posting.createBlogPosting(thumbnail, title, url, PostingBase.BlogPosting);
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
