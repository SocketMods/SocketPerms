package dev.socketmods.socketperms.api;

import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

public interface IPermissionValue {
    IPermissionValue EMPTY = new IPermissionValue() {
        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public String toString() {
            return "EMPTY";
        }
    };

    boolean isEmpty();

    default OptionalBoolean asBoolean() {
        return OptionalBoolean.empty();
    }

    default OptionalInt asInt() {
        return OptionalInt.empty();
    }

    default OptionalLong asLong() {
        return OptionalLong.empty();
    }

    default OptionalDouble asDouble() {
        return OptionalDouble.empty();
    }

    String toString();
}
