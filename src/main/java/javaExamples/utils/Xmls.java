package javaExamples.utils;

import java.util.HashMap;
import java.util.Map;

public final class Xmls {
    public static final class Escaping {
        private enum State {PRINT,READING_TOKEN, END_READING_TOKEN}
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

        private static final class EscapableSymbols {
            private static final Map<String, Character> dictionary = new HashMap<>();
            private static final int MAX_SIZE;
            static {
                dictionary.put("lt",'<');
                dictionary.put("gt",'>');
                dictionary.put("amp",'&');
                dictionary.put("apos",'\'');
                dictionary.put("quot",'"');
                int currentMaxSize = 0;
                for (String key:dictionary.keySet()) {
                    int size = key.length();
                    if (size > currentMaxSize) {
                        currentMaxSize = size;
                    }
                }
                MAX_SIZE = currentMaxSize;
            }

            private static boolean isEscapable(StringBuilder symbolAsString) {
                return isEscapable(symbolAsString.toString());
            }

            private static boolean isEscapable(String symbolAsString) {
                return dictionary.containsKey(symbolAsString);
            }

            private static char getSymbol(StringBuilder symbolAsString) {
                return getSymbol(symbolAsString.toString());
            }

            private static char getSymbol(String symbolAsString) {
                if (!isEscapable(symbolAsString)) {
                    throw new RuntimeException("Escaped symbol does not exist.");
                }
                return dictionary.get(symbolAsString);
            }
        }

        public static String unescape(String toUnescape) {
            StringBuilder escaped = new StringBuilder();
            StringBuilder queue   = new StringBuilder();
            State state = State.PRINT;
            for (char character: toUnescape.toCharArray()) {
                state = getState(character, state);
                if (character == '&') {
                    continue;
                }
                switch (state) {
                    case READING_TOKEN:
                        queue.append(character);
                        if (queue.length() > EscapableSymbols.MAX_SIZE) {
                            escaped.append("&").append(queue);
                            queue = new StringBuilder();
                            state = State.PRINT;
                        }
                        break;
                    case END_READING_TOKEN:
                        if (EscapableSymbols.isEscapable(queue)) {
                            escaped.append(EscapableSymbols.getSymbol(queue));
                        } else {
                            escaped.append("&").append(queue).append(";");
                        }
                        queue = new StringBuilder();
                        break;
                    default:
                        escaped.append(character);
                        break;
                }
            }
            if (queue.length() > 0) {
                escaped.append(queue);
            }
            return escaped.toString();
        }
    }
}