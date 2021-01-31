package dev.socketmods.socketperms.values;

import dev.socketmods.socketperms.api.IPermissionValue;
import dev.socketmods.socketperms.api.OptionalBoolean;

import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.StringJoiner;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class PermissionValue implements IPermissionValue {
    private final boolean isEmpty;
    private final OptionalBoolean optBool;
    private final OptionalInt optInt;
    private final OptionalLong optLong;
    private final OptionalDouble optDouble;

    public PermissionValue(OptionalBoolean optBool, OptionalInt optInt, OptionalLong optLong, OptionalDouble optDouble) {
        this.optBool = optBool;
        this.optInt = optInt;
        this.optLong = optLong;
        this.optDouble = optDouble;
        this.isEmpty = !optBool.isPresent() && !optInt.isPresent() && !optLong.isPresent() && !optDouble.isPresent();
    }

    @Override
    public boolean isEmpty() {
        return isEmpty;
    }

    @Override
    public OptionalBoolean asBoolean() {
        return optBool;
    }

    @Override
    public OptionalInt asInt() {
        return optInt;
    }

    @Override
    public OptionalLong asLong() {
        return optLong;
    }

    @Override
    public OptionalDouble asDouble() {
        return optDouble;
    }

    @Override
    public String toString() {
        if (isEmpty) return "EMPTY";
        final StringJoiner joiner = new StringJoiner(",", "PermissionValue[", "]");
        optBool.ifPresent(bool -> joiner.add(Boolean.toString(bool)));
        optInt.ifPresent(num -> joiner.add(Integer.toString(num)));
        optLong.ifPresent(num -> joiner.add(num + "L"));
        optDouble.ifPresent(num -> joiner.add(num + "D"));
        return joiner.toString();
    }
}
