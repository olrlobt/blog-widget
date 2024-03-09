package olrlobt.githubtistoryposting.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class Postings {
	List<Posting> postings;

	public Postings() {
		this.postings = new ArrayList<>();
	}

	public void add(Posting posting) {
		postings.add(posting);
	}
}
