package infosys.teamd.bioskopapisecurity.DTO;

import infosys.teamd.bioskopapisecurity.Model.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilmsRequestDTO {
    private String title;
    private Integer status;

    public Films convertToEntity(){
        return Films.builder()
                .name(this.title)
                .isPlaying(this.status)
                .build();
    }
}
