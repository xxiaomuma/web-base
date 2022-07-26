package pers.xiaomuma.framework.page;

import java.util.ArrayList;
import java.util.List;


public class PageResult<T> implements Pagable<T> {

	private long current = 1;
	private long size = 10;
	private long total;
	private long pages;
	private List<T> records;

	public PageResult() {}

	public PageResult(long current, long size) {
		this.current = current;
		this.size = size;
	}

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
		if (total > 0) {
			this.pages = calculatePages(total, size);
		}
	}

	private long calculatePages(long total, long size) {
		if (total % size == 0) {
			return  total / size;
		} else {
			return  total / size + 1;
		}
	}

	public static <T> PageResult<T> emptyPage() {
		return new PageResult<>(0, 0, 0, new ArrayList<>());
	}

	@Override
	public long getCurrent() {
		return this.current;
	}

	@Override
	public long getSize() {
		return this.size;
	}

	@Override
	public long getTotal() {
		return this.total;
	}

	@Override
	public long getPages() {
		return this.pages;
	}

	@Override
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
	}

	public void setPages(long pages) {
		this.pages = calculatePages(total, size);
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}
}
