package com.maxciv.businesslogic;

public enum Condition {
    IN_LIBRARY(0, "Book is in the library"),
    IN_READING(1, "Book is in the reading"),
    NOT_AVAILABLE(2, "Book is not available"),
    RETURNED_TO_OWNER(3, "Book returned to owner"),
    ON_EXCHANGE(4, "Book is on the exchange at this time"),
    ORDER_IN_PROGRESS(5, "Order in progress");

    private final int condition;
    private final String conditionName;

    Condition(int condition, String conditionName) {
        this.condition = condition;
        this.conditionName = conditionName;
    }

    public int getConditionId() {
        return condition;
    }

    public String getConditionName() {
        return conditionName;
    }

    @Override
    public String toString() {
        return conditionName;
    }

    /**
     * Get the condition which corresponds to the given number as returned by
     * {@link Condition#getConditionId()}. If the number is an invalid value,
     * then {@link Condition#NOT_AVAILABLE} will be returned.
     *
     * @param condition The numeric condition value.
     * @return The condition.
     */
    public static Condition valueOf(int condition) {
        try {
            return Condition.values()[condition];
        } catch (ArrayIndexOutOfBoundsException e) {
            return NOT_AVAILABLE;
        }
    }
}
