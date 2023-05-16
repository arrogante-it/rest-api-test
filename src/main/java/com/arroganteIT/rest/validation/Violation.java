package com.arroganteIT.rest.validation;

public class Violation {
    // in java14 you can use modificator - record
//    public record Violation(String property, String message) {}


        private String property;
        private String message;

        public Violation(String property, String message) {
            this.property = property;
            this.message = message;
        }

        public String getProperty() {
            return property;
        }

        public String getMessage() {
            return message;
        }

}
