package olrlobt.githubtistoryposting.service.platform;

import java.io.IOException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.RedirectView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import olrlobt.githubtistoryposting.domain.BlogInfo;
import olrlobt.githubtistoryposting.domain.BlogTag;
import olrlobt.githubtistoryposting.domain.ImageSize;
import olrlobt.githubtistoryposting.domain.Posting;
import olrlobt.githubtistoryposting.domain.PostingType;
import olrlobt.githubtistoryposting.utils.DateUtils;
import olrlobt.githubtistoryposting.utils.ScrapingUtils;
import olrlobt.githubtistoryposting.utils.UrlUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class Tistory implements Blog {

    private final ScrapingUtils scrapingUtils;

    @Override
    public Posting posting(String blogName, int index, PostingType postingType) throws IOException {
        int page = 1;
        Document firstDocument = scrapingUtils.byUrl(createUrl(blogName, page));
        BlogTag theme = findTistoryTheme(firstDocument);

        int postingOfPage = getPostingOfPageNum(firstDocument, theme);
        page += index / postingOfPage;
        String url = createUrl(blogName, page);
        Document document = scrapingUtils.byUrl(url);

        Elements postings = document.select(theme.getPostingList());
        return findPosting(postings, index % postingOfPage, theme, postingType, url);
    }

    @Override
    public RedirectView link(String blogName, int index) throws IOException {
        int page = 1;
        Document firstDocument = scrapingUtils.byUrl(createUrl(blogName, page));
        BlogTag theme = findTistoryTheme(firstDocument);

        int postingOfPage = getPostingOfPageNum(firstDocument, theme);
        page += index / postingOfPage;
        Document document = scrapingUtils.byUrl(createUrl(blogName, page));

        Elements postings = document.select(theme.getPostingLink());
        index %= postingOfPage;
        if (index >= postings.size()) {
            return new RedirectView(document.select(BlogInfo.TISTORY.getBlogUrl())
                    .attr("content"));
        }

        String postingParam = postings.get(index)
                .attr("href");
        return new RedirectView(UrlUtils.of(blogName, postingParam));
    }

    @Override
    public Posting blog(String blogName) throws IOException {
        Document document = scrapingUtils.byUrl(createUrl(blogName, 1));

        String originalThumb = document.select(BlogInfo.TISTORY.getBlogThumb())
                .attr("content");
        String resizeThumbnail = UrlUtils.changeThumbnailSize(originalThumb, ImageSize.TistoryBlog.getSizeParam());
        String title = document.select(BlogInfo.TISTORY.getBlogName())
                .attr("content");
        String footer = document.select(BlogInfo.TISTORY.getBlogUrl())
                .attr("content");

        return new Posting(resizeThumbnail, title, "", "", footer, PostingType.BlogInfo);
    }

    private static String createUrl(String blogName, int page) {
        return "https://" + blogName + ".tistory.com/?page=" + page;
    }

    /**
     * Tistory 테마 찾기
     */
    private BlogTag findTistoryTheme(Document document) {
        String themeIndex = document.selectFirst("body").className().split(" ")[0];
        if (themeIndex.isEmpty()) {
            themeIndex = document.selectFirst("html").id();
            if (themeIndex.isEmpty()) {
                themeIndex = document.selectFirst(".emph_t").text();
            }
        }
        return BlogTag.findTheme(themeIndex);
    }

    private int getPostingOfPageNum(Document document, BlogTag theme) {
        Elements postings = document.select(theme.getPostingList());
        return postings.size();
    }

    private Posting findPosting(Elements postings, int index, BlogTag theme, PostingType postingType, String url) {
        if (index >= postings.size()) {
            return Posting.createNoPosting();
        }
        return makePosting(postings.get(index), theme, postingType, url);
    }

    private Posting makePosting(Element posting, BlogTag theme, PostingType postingType, String url) {
        String thumbnail = findThumbnail(posting, theme.getPostingThumb());
        String title = posting.select(theme.getPostingTitle()).text();
        LocalDate date = DateUtils.parser(posting.select(theme.getPostingDate()).text());
        return new Posting(thumbnail, title, "", date, url, postingType);
    }

    private static String findThumbnail(Element posting, String themeTag) {
        String thumbnail = null;
        Element thumb = posting.select(themeTag).first();

        if (thumb != null) {
            String src = thumb.attr("src");
            String style = thumb.attr("style");
            String dataSrc = thumb.attr("data-src");

            if (!src.isEmpty()) {
                thumbnail = UrlUtils.addProtocol(src);
            } else if (!style.isEmpty()) {
                String urlPattern = "url\\('(.+?)'\\)";
                Pattern pattern = Pattern.compile(urlPattern);
                Matcher matcher = pattern.matcher(style);
                if (matcher.find()) {
                    thumbnail = matcher.group(1);
                }
            } else {
                thumbnail = dataSrc.replace("amp;", "");
            }

            if (!thumbnail.isEmpty()) {
                thumbnail = UrlUtils.changeThumbnailSize(thumbnail, ImageSize.TistoryPosting.getSizeParam());
            }
        }
        return thumbnail;
    }
}
