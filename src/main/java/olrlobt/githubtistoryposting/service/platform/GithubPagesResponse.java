package olrlobt.githubtistoryposting.service.platform;

import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GithubPagesResponse {
    private Data data;

    @Getter
    public static class Data {
        private User user;
        private Repository repository;
    }

    @Getter
    public static class User {
        private String avatarUrl;
    }

    @Getter
    public static class Repository {
        private Object object;
    }

    @Getter
    public static class Object {
        private List<Entry> entries;
        private String text;
    }

    @Getter
    public static class Entry {
        private String name;
    }
}
