package pers.xiaomuma.framework.page;

import java.util.List;

public interface Pagable<T> {

	long getCurrent();

	long getSize();

	long getTotal();

	default long getPages() {
		return 1;
	}

	List<T> getRecords();

}
