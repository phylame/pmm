package pmm.pbm.service.params;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pmm.pbm.util.cms.QueryField;
import pw.phylame.ycl.value.MutablePair;

@Data
@EqualsAndHashCode(callSuper = true)
public class ListBookDTO extends PagingDTO {
    @QueryField
    private String id;

    @QueryField
    private String title;

    private String authorId;

    @QueryField
    private String author;

    private String genreId;

    @QueryField
    private String genre;

    private String tagId;

    @QueryField
    private String tag;

    private String coverId;

    private String cover;

    private List<MutablePair<Integer, String>> tags;

}
