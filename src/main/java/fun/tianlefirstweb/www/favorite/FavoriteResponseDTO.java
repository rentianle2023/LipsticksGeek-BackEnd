package fun.tianlefirstweb.www.favorite;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class FavoriteResponseDTO {

    private Integer id;
    private String name;
    private String hexColor;
    private Timestamp createTime;
}
