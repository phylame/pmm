package pmm.pbm.service.results;

import java.util.List;

import org.apache.commons.lang3.tuple.MutablePair;

import lombok.Data;

@Data
public class ListBookVO {
    private Integer id;

    private String title;

    private String intro;

    private MutablePair<Integer, String> author;

    private MutablePair<Integer, String> genre;

    private MutablePair<Integer, String> cover;

    private List<MutablePair<Integer, String>> tags;

    public String getWho() {
        return title;
    }
}
