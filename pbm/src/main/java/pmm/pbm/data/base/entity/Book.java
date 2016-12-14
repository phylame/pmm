package pmm.pbm.data.base.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pmm.pbm.data.base.support.Entity;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Book extends Entity {
    /**
     * Title of book
     *
     * @mbg.generated
     */
    private String title;

    /**
     * Id of book author
     *
     * @mbg.generated
     */
    private Integer authorId;

    /**
     * Id of book genre
     *
     * @mbg.generated
     */
    private Integer genreId;

    /**
     * Brief introduction of book
     *
     * @mbg.generated
     */
    private String intro;

    /**
     * Id of book cover
     *
     * @mbg.generated
     */
    private Integer coverId;
}