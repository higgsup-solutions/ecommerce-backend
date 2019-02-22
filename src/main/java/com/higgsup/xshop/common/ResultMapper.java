package com.higgsup.xshop.common;

import java.lang.reflect.Constructor;

public abstract class ResultMapper {

    protected <T> T createInstance(Constructor<?> ctor, Object[] args) {
        try {
            T t =  (T) ctor.newInstance(args);
            return t;
        } catch (IllegalArgumentException e) {
            StringBuilder sb = new StringBuilder("no constructor taking:\n");
            for (Object object : args) {
            	sb.append("\t").append(object == null ? "null" : object.getClass().getName()).append("\n");
            }
            throw new RuntimeException(sb.toString(), e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
