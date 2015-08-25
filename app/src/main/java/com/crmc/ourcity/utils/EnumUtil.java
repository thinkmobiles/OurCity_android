package com.crmc.ourcity.utils;


import android.content.Intent;

public class EnumUtil {
    public static class Serializer<T extends Enum<T>> extends Deserializer<T> {

        private T target;

        public Serializer(Class<T> _targetType, T _target) {
            super(_targetType);
            this.target = _target;
        }

        public void to(Intent _intent) {
            _intent.putExtra(name, target.ordinal());
        }
    }

    public static class Deserializer<T extends Enum<T>> {

        protected Class<T> targetType;
        protected String name;

        public Deserializer(Class<T> _targetType) {
            this.targetType = _targetType;
            this.name = _targetType.getName();
        }

        public T from(Intent _intent) {
            if (!_intent.hasExtra(name)) throw new IllegalStateException();
            return targetType.getEnumConstants()[_intent.getIntExtra(name, -1)];
        }
    }
    public static <T extends Enum<T>> Deserializer<T> deserialize(Class<T> _targetType) {
        return new Deserializer<>(_targetType);
    }

    public static <T extends Enum<T>> Serializer<T> serialize(Class<T> _targetType, T _target) {
        return new Serializer<>(_targetType, _target);
    }
}
