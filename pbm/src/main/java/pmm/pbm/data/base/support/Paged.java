/**
 *
 */
package pmm.pbm.data.base.support;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import pmm.pbm.config.AppConfig;

/**
 * Common pagination support.
 *
 * @param <E>
 *            type of result
 * @author PW[<a href="mailto:phylame@163.com">phylame@163.com</a>]
 */
public interface Paged<E> extends Iterable<E> {
    /**
     * Number of shown pages in paging bar.
     */
    int SHOWN_PAGES = AppConfig.shownPages;

    /**
     * Default number of shown results in a page.
     */
    int DEFAULT_LIMITS = AppConfig.defaultLimit;

    /**
     * Returns number of total results.
     *
     * @return the number of results
     * @author PW[<a href="mailto:phylame@163.com">phylame@163.com</a>]
     */
    long getTotal();

    /**
     * Returns number of total pages.
     *
     * @return the number of pages
     * @author PW[<a href="mailto:phylame@163.com">phylame@163.com</a>]
     */
    int getPages();

    /**
     * Returns the offset of current page.
     *
     * @return the page offset
     * @author PW[<a href="mailto:phylame@163.com">phylame@163.com</a>]
     */
    int getOffset();

    /**
     * Returns the limit of results in a page.
     *
     * @return the results limit
     * @author PW[<a href="mailto:phylame@163.com">phylame@163.com</a>]
     */
    int getLimit();

    /**
     * Returns {@literal true} if current page is the first one.
     *
     * @return {@literal true} for first page, otherwise {@literal false}
     * @author PW[<a href="mailto:phylame@163.com">phylame@163.com</a>]
     */
    boolean isFirst();

    /**
     * Returns {@literal true} if current page is the last one.
     *
     * @return {@literal true} for last page, otherwise {@literal false}
     * @author PW[<a href="mailto:phylame@163.com">phylame@163.com</a>]
     */
    boolean isLast();

    /**
     * Returns the list of results in current page.
     *
     * @return the list of results, may be empty list
     * @author PW[<a href="mailto:phylame@163.com">phylame@163.com</a>]
     */
    List<E> getResults();

    default E get(int index) {
        return getResults().get(index);
    }

    @Override
    default Iterator<E> iterator() {
        return getResults().iterator();
    }

    default int[] getOffsets() {
        int pages = getPages(), offset = getOffset(), end = offset + SHOWN_PAGES - 1, start = offset;
        if (end > pages) { // no more pages
            start += pages - end;
            end = pages;
            if (start < 1) {
                start = 1;
            }
        }
        int offsets[] = new int[end - start + 1], beg = start;
        Arrays.setAll(offsets, i -> beg + i);
        return offsets;
    }

    default int[] getLimits() {
        int[] limits = new int[DEFAULT_LIMITS];
        Arrays.setAll(limits, i -> 1 + i);
        return limits;
    }
}
