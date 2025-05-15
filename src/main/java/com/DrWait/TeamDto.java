package com.DrWait;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString

public class TeamDto {

    private String name;
    private String pm;
    private String member1;
    private String member2;
    private String member3;
}
