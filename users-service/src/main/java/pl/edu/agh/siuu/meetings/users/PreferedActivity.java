package pl.edu.agh.siuu.meetings.users;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class PreferedActivity {
    private String category;
    private String comment;
}
