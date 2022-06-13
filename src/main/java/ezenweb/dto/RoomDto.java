package ezenweb.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RoomDto {

    private String roomname;
    private String x;
    private String y;

}
