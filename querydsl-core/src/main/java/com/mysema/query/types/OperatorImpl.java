/*
 * Copyright 2015, The Querydsl Team (http://www.querydsl.com/team)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mysema.query.types;

import java.io.ObjectStreamException;
import java.lang.reflect.Field;

import javax.annotation.concurrent.Immutable;

/**
 * OperatorImpl is the default implementation of the {@link Operator} interface
 */
@Immutable
public final class OperatorImpl<T> implements Operator<T> {

    private static final long serialVersionUID = -2435035383548549877L;

    private final String id;

    private final int hashCode;

    public OperatorImpl(String ns, String local) {
        this(ns + "#" + local);
    }

    private OperatorImpl(String id) {
        this.id = id;
        this.hashCode = id.hashCode();
    }

    /**
     * Get the unique id for this Operator
     *
     * @return
     */
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    private Object readResolve() throws ObjectStreamException {
        try {
            String[] names = id.split("#");
            Field opField = Class.forName(names[0]).getField(names[1]);
            return opField.get(null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
