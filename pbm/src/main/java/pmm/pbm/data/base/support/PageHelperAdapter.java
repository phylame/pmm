package pmm.pbm.data.base.support;

import java.util.List;

import org.apache.commons.lang3.Validate;

import com.github.pagehelper.Page;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PageHelperAdapter<E> implements Paged<E> {
    @NonNull
    private final Page<E> page;

    public PageHelperAdapter(List<E> page) {
        Validate.isTrue(page instanceof Page<?>, "page must be instance of %s", Page.class.getName());
        this.page = (Page<E>) page;
    }

    @Override
    public long getTotal() {
        return page.getTotal();
    }

    @Override
    public int getPages() {
        return page.getPages();
    }

    @Override
    public int getOffset() {
        return page.getPageNum();
    }

    @Override
    public int getLimit() {
        return page.getPageSize();
    }

    @Override
    public boolean isFirst() {
        return page.getPageNum() <= 1;
    }

    @Override
    public boolean isLast() {
        return page.getPageNum() >= page.getPages();
    }

    @Override
    public List<E> getResults() {
        return page;
    }

    @Override
    public E get(int index) {
        return page.get(index);
    }
}
