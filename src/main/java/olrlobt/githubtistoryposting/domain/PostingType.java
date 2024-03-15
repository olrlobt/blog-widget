package olrlobt.githubtistoryposting.domain;

import lombok.Getter;

@Getter
public enum PostingType {

	BlogInfo(217,217, -1),
	BlogPosting(217, 126, 126);

	private final int width;
	private final int height;
	private final int startText;

	PostingType(int width, int height, int startText) {
		this.width = width;
		this.height = height;
		this.startText = startText;
	}
}
