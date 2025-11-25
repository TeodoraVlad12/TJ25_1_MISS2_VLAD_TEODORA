package StableMatch.example.StableMatch.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchingResponse {
    private List<Assignment> assignments;
    private boolean stable;
}
