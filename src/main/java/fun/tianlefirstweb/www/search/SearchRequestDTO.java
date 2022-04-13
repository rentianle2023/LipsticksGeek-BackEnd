package fun.tianlefirstweb.www.search;

import lombok.Data;

import java.util.List;

@Data
public class SearchRequestDTO {

    private List<String> fields;
    private String term;
}
