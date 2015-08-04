package com.crmc.ourcity.utils;


import android.content.Intent;

public class EnumUtil {
    public static class Serializer<T extends Enum<T>> extends Deserializer<T> {
        private T target;

        public Serializer(Class<T> targetType, T target) {
            super(targetType);
            this.target = target;
        }
        public void to(Intent intent) {
            intent.putExtra(name, target.ordinal());
        }
    }

    public static class Deserializer<T extends Enum<T>> {
        protected Class<T> targetType;
        protected String name;
        public Deserializer(Class<T> targetType) {
            this.targetType = targetType;
            this.name = targetType.getName();
        }
        public T from(Intent intent) {
            if (!intent.hasExtra(name)) throw new IllegalStateException();
            return targetType.getEnumConstants()[intent.getIntExtra(name, -1)];
        }
    }
    public static <T extends Enum<T>> Deserializer<T> deserialize(Class<T> targetType) {
        return new Deserializer<T>(targetType);
    }
    public static <T extends Enum<T>> Serializer<T> serialize(Class<T> targetType, T target) {
        return new Serializer<T>(targetType, target);
    }
}
