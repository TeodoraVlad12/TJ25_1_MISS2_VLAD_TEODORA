package StableMatch.example.StableMatch.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchingRequest {
    private List<Student> students;
    private List<Course> courses;
}
