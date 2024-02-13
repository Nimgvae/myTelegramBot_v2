package mytelegrambot_v2.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * A simple generic wrapper class for holding a result of type T.
 *
 * @param <T> The type of the result
 */
@Getter
@AllArgsConstructor
public class SimpleResponse<T> {
    /**
     * The result of type T.
     */
    private final T result;


}
