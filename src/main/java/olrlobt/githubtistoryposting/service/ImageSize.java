package olrlobt.githubtistoryposting.service;

import lombok.Getter;

@Getter
public enum ImageSize {

	TistoryBlog("C428x428"),
	TistoryPosting("C217x122");

	private final String sizeParam;

	ImageSize(String size) {
		this.sizeParam = size;
	}
}
