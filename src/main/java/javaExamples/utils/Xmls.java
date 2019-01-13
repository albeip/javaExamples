package javaExamples.utils;

import java.util.HashMap;
import java.util.Map;

public final class Xmls {
    private enum State {PRINT,READING_TOKEN, END_READING_TOKEN}
    private static final Map<String, String> DICTIONARY = new HashMap<>();
    static {
        DICTIONARY.put("lt","<");
        DICTIONARY.put("gt",">");
        DICTIONARY.put("amp","&");
        DICTIONARY.put("apos","'");
        DICTIONARY.put("quot","\"");
    }

    private static State getState(char character, State currentState) {
        switch (character) {
            case '&':
                return State.READING_TOKEN;
            case ';':
                if (currentState == State.READING_TOKEN) {
                    return State.END_READING_TOKEN;
                } else {
                    return currentState;
                }
            default:
                if (currentState == State.READING_TOKEN) {
                    return currentState;
                } else {
                    return State.PRINT;
                }
        }
    }
    public static String unescape(String toUnescape) {
        StringBuilder escaped = new StringBuilder();
        StringBuilder queue = new StringBuilder();
        State state = State.PRINT;
        for (char character :toUnescape.toCharArray()      ) {
            state = getState(character, state);
            if (character == '&') {
                continue;
            }
            switch (state) {
                case READING_TOKEN:
                    queue.append(character);
                    break;
                case END_READING_TOKEN:
                    if (DICTIONARY.containsKey(queue.toString())) {
                        escaped.append(DICTIONARY.get(queue.toString()));
                    } else {
                        escaped.append("&").append(queue.toString()).append(";");
                    }
                    queue = new StringBuilder();
                    break;
                default:
                    escaped.append(character);
            }
        }
        if (queue.length() > 0) {
            escaped.append(queue.toString());
        }
        return escaped.toString();
    }
}