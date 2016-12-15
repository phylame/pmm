package pmm.pbm.data.base.support;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpringPageAdapter<E> implements Paged<E> {
    @NonNull
    private final Page<E> page;

    @Override
    public long getTotal() {
        return page.getTotalElements();
    }

    @Override
    public int getPages() {
        return page.getTotalPages();
    }

    @Override
    public int getOffset() {
        return page.getNumber() + 1;
    }

    @Override
    public int getLimit() {
        return page.getSize();
    }

    @Override
    public boolean isFirst() {
        return page.isFirst();
    }

    @Override
    public boolean isLast() {
        return page.isLast();
    }

    @Override
    public List<E> getResults() {
        return page.getContent();
    }
}
