package co.com.millennialapps.utils.sqlite;

import android.content.ContentValues;

import java.lang.reflect.Field;
import java.util.ArrayList;

import co.com.millennialapps.utils.models.DataFieldNull;

/**
 * Created by Erick Velasco on 18/12/2017.
 */

public class SQLiteTools<T> {

    public static <T> String getCreateSentence(Class<T> clazz) {
        StringBuilder sentence = new StringBuilder("CREATE TABLE " + clazz.getSimpleName() + " (");
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType().isPrimitive() && !field.getName().equals("serialVersionUID")) {
                if (field.getType().equals(Integer.TYPE)
                        || field.getType().equals(Boolean.TYPE)
                        || field.getType().equals(Byte.TYPE)
                        || field.getType().equals(Long.TYPE)
                        || field.getType().equals(Short.TYPE)) {
                    if (field.isAnnotationPresent(DataFieldNull.class)) {
                        sentence.append(field.getName()).append(" INT NULL, ");
                    } else {
                        sentence.append(field.getName()).append(" INT NOT NULL, ");
                    }
                } else {
                    if (field.getType().equals(Float.TYPE) || field.getType().equals(Double.TYPE)) {
                        if (field.isAnnotationPresent(DataFieldNull.class)) {
                            sentence.append(field.getName()).append(" REAL NULL, ");}
                        else {
                            sentence.append(field.getName()).append(" REAL NOT NULL, ");
                        }
                    } else {
                        if (field.getType().equals(Character.TYPE)) {
                            if (field.isAnnotationPresent(DataFieldNull.class)) {
                                sentence.append(field.getName()).append(" TEXT NULL, ");
                            }else {
                                sentence.append(field.getName()).append(" TEXT NOT NULL, ");
                            }
                        }
                    }
                }
            } else {
                if (field.getType().isAssignableFrom(String.class)) {
                    if (field.isAnnotationPresent(DataFieldNull.class)) {
                        sentence.append(field.getName()).append(" TEXT NULL, ");
                    }else {
                        sentence.append(field.getName()).append(" TEXT NOT NULL, ");
                    }
                }
            }
        }

        return sentence.substring(0, sentence.lastIndexOf(",")) + ");";
    }

    public static <T> String[] getColumns(Class<T> clazz) {
        ArrayList<String> columns = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType().isPrimitive() && !field.getName().equals("serialVersionUID")) {
                if (field.getType().equals(Integer.TYPE)
                        || field.getType().equals(Boolean.TYPE)
                        || field.getType().equals(Byte.TYPE)
                        || field.getType().equals(Long.TYPE)
                        || field.getType().equals(Short.TYPE)
                        || field.getType().equals(Float.TYPE)
                        || field.getType().equals(Double.TYPE)
                        || field.getType().equals(Character.TYPE)) {
                    columns.add(field.getName());
                }
            } else {
                if (field.getType().isAssignableFrom(String.class)) {
                    columns.add(field.getName());
                }
            }
        }
        return columns.toArray(new String[columns.size()]);
    }

    public static <T> ContentValues getContentValues(T obj) {
        ContentValues values = new ContentValues();
        try {
            for (Field field : obj.getClass().getDeclaredFields()) {
                boolean accesibility = field.isAccessible();
                field.setAccessible(true);
                if (field.get(obj) != null) {
                    if (field.getType().isPrimitive() && !field.getName().equals("serialVersionUID")) {
                        if (field.getType().equals(Integer.TYPE)) {
                            values.put(field.getName(), field.getInt(obj));
                        } else if (field.getType().equals(Boolean.TYPE)) {
                            values.put(field.getName(), field.getBoolean(obj));
                        } else if (field.getType().equals(Byte.TYPE)) {
                            values.put(field.getName(), field.getByte(obj));
                        } else if (field.getType().equals(Long.TYPE)) {
                            values.put(field.getName(), field.getLong(obj));
                        } else if (field.getType().equals(Short.TYPE)) {
                            values.put(field.getName(), field.getShort(obj));
                        } else if (field.getType().equals(Float.TYPE)) {
                            values.put(field.getName(), field.getFloat(obj));
                        } else if (field.getType().equals(Double.TYPE)) {
                            values.put(field.getName(), field.getDouble(obj));
                        }
                    } else {
                        if (field.getType().isAssignableFrom(String.class)) {
                            values.put(field.getName(), field.get(obj).toString());
                        }
                    }
                }
                field.setAccessible(accesibility);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return values;
    }
}
