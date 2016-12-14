package pmm.pbm.service.params;

import java.util.List;

import org.apache.commons.lang3.tuple.MutablePair;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ListBookDTO extends PagingDTO {
    private String id;

    private String title;

    private String authorId;

    private String authorName;

    private String genreId;

    private String genreName;

    private String coverId;

    private String coverName;

    private List<MutablePair<Integer, String>> tags;

}
