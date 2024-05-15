package olrlobt.githubtistoryposting.domain;

import lombok.Getter;

@Getter
public enum PostingType {

	BlogInfo(217, 260, 217, 217, 0, 20, 217, 14, -1, 3),
	BlogPosting(217, 260, 217, 126, 0, 20, 217, 14, 126, 3),
	BlogPostingWide(800, 217, 217, 217, 583, 25, 583, 25, 10, 2);

	private final int boxWidth;
	private final int boxHeight;
	private final int imgWidth;
	private final int imgHeight;
	private final int imgStartWidth;
	private final int textPadding;
	private final int titleWidth;
	private final int titleSize;
	private final int titleStartHeight;
	private final int titleMaxLine;

	PostingType(int boxWidth, int boxHeight, int imgWidth, int imgHeight, int imgStartWidth, int textPadding,
		int titleWidth, int titleSize, int titleStartHeight, int titleMaxLine) {
		this.boxWidth = boxWidth;
		this.boxHeight = boxHeight;
		this.imgWidth = imgWidth;
		this.imgHeight = imgHeight;
		this.imgStartWidth = imgStartWidth;
		this.textPadding = textPadding;
		this.titleWidth = titleWidth;
		this.titleSize = titleSize;
		this.titleStartHeight = titleStartHeight;
		this.titleMaxLine = titleMaxLine;
	}
}
