package com.todoroo.andlib;

import static com.todoroo.andlib.Constants.AS;
import static com.todoroo.andlib.Constants.SPACE;

public abstract class DBObject<T extends DBObject<?>> implements Cloneable {
    protected String alias;
    protected final String expression;

    protected DBObject(String expression){
        this.expression = expression;
    }

    @SuppressWarnings("unchecked")
    public T as(String newAlias) {
        try {
            T clone = (T) clone();
            clone.alias = newAlias;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasAlias() {
        return alias != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DBObject<?> dbObject = (DBObject<?>) o;

        if (alias != null ? !alias.equals(dbObject.alias) : dbObject.alias != null) return false;
        if (expression != null ? !expression.equals(dbObject.expression) : dbObject.expression != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = alias != null ? alias.hashCode() : 0;
        result = 31 * result + (expression != null ? expression.hashCode() : 0);
        return result;
    }

    @Override
    public final String toString() {
        if (hasAlias()) {
            return alias;
        }
        return expression;
    }

    public final String toStringInSelect() {
        StringBuilder sb = new StringBuilder(expression);
        if (hasAlias()) {
            sb.append(SPACE).append(AS).append(SPACE).append(alias);
        } else {
            int pos = expression.indexOf('.');
            if(pos > 0)
                sb.append(SPACE).append(AS).append(SPACE).append(expression.substring(pos + 1));
        }
        return sb.toString();
    }
}
