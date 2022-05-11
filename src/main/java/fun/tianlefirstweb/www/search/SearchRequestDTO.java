package fun.tianlefirstweb.www.search;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class SearchRequestDTO {

    @NotEmpty(message = "搜索字段不能为空")
    private List<String> fields;

    private String term;
}
