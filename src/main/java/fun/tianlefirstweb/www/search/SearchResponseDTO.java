package fun.tianlefirstweb.www.search;

import lombok.Data;

@Data
public class SearchResponseDTO {

    private final String id;
    private final String name;
    private final Boolean isColor;
    private final String hexColor;
}
