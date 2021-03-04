package com.openpayd.foreignexchangeapp.dto.response;

import java.util.List;

public class PagedResponse<T> {
	private boolean hasNext;
	private List<T> content;

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}
}
