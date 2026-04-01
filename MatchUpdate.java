import java.util.ArrayList;
import java.util.List;

public class MatchUpdate {
    private final boolean success;
    private final String message;
    private final List<String> events;

    private MatchUpdate(boolean success, String message, List<String> events) {
        this.success = success;
        this.message = message;
        this.events = events;
    }

    public static MatchUpdate success(List<String> events) {
        return new MatchUpdate(true, null, new ArrayList<>(events));
    }

    public static MatchUpdate error(String message) {
        return new MatchUpdate(false, message, List.of());
    }

    public static MatchUpdate info(String message) {
        return new MatchUpdate(true, message, List.of());
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getEvents() {
        return events;
    }
}
