package fun.tianlefirstweb.www.search;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ColorSearchRequestDTO {

    @Pattern(regexp = "^#(?:[0-9a-fA-F]{3}){1,2}$", message = "HexColor格式不合法")
    private String hexColor;
}
