package pers.xiaomuma.base.web.api;

import com.google.common.collect.Lists;

import java.util.List;


public class PageResult<T> {

	private long current = 1;
	private long size = 10;
	private long total = 0;
	private long pages = 1;
	private List<T> records;

	public PageResult() {}

	public PageResult(PageResult<?> pageResult, List<T> records) {
		this.current = pageResult.getCurrent();
		this.size = pageResult.getSize();
		this.total = pageResult.getTotal();
		this.pages = pageResult.getPages();
		this.records = records;
	}

	public PageResult(long current, long size, long total, List<T> records) {
		this.current = current;
		this.size = size;
		this.total = total;
		this.records = records;
		setTotal(total);
	}

	private long calculatePages(long total, long size) {
		if (total % size == 0) {
			return  total / size;
		} else {
			return  total / size + 1;
		}
	}

	public static <T> PageResult<T> emptyPage() {
		PageResult<T> pageResult = new PageResult<>();
		pageResult.setRecords(Lists.newArrayList());
		return pageResult;
	}

	public long getCurrent() {
		return this.current;
	}

	public long getSize() {
		return this.size;
	}

	public long getTotal() {
		return this.total;
	}

	public long getPages() {
		return this.pages;
	}

	public List<T> getRecords() {
		return this.records;
	}

	public void setCurrent(long current) {
		this.current = current;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public void setTotal(long total) {
		this.total = total;
		if (total > 0) {
			this.pages = calculatePages(total, size);
		}
	}

	public void setPages(long pages) {
		this.pages = calculatePages(total, size);
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}
}
