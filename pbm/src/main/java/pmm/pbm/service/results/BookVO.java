package pmm.pbm.service.results;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import pw.phylame.ycl.value.MutablePair;
import pw.phylame.ycl.value.Pair;

@Data
public class BookVO implements Serializable {
    private static final long serialVersionUID = 5799625074535458608L;

    private Integer id;

    private String title;

    private String intro;

    private MutablePair<Integer, String> author;

    private MutablePair<Integer, String> genre;

    private MutablePair<Integer, String> cover;

    private List<Pair<Integer, String>> tags;

    public String getWho() {
        return title;
    }
}
