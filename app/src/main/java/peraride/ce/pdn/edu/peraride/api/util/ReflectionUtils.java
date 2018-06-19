package peraride.ce.pdn.edu.peraride.api.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Common reflection methods.
 */
final class ReflectionUtils {
  /* [ CONSTRUCTORS ] ============================================================================================== */

    /**
     * hidden constructor.
     */
    private ReflectionUtils() {
        throw new AssertionError();
    }

  /* [ STATIC METHODS ] ============================================================================================ */

    /**
     * Extract all fields of the provided class type.
     *
     * @param type the class type
     * @return the list of found fields.
     */
    public static List<Field> allFields(final Class<?> type) {
        final List<Field> fields = new LinkedList<>();

        return allFields(fields, type);
    }

    /**
     * Extract all fields in recursive manner.
     *
     * @param fields the extracted fields
     * @param type   the type to examine
     * @return the extracted fields
     */
    private static List<Field> allFields(final List<Field> fields, final Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            allFields(fields, type.getSuperclass());
        }

        return fields;
    }

    /**
     * Find field by name in list of fields.
     *
     * @param fields the list of fields to process
     * @param name   the name to find.
     * @return found field instance, otherwise {@code null}.
     */
    public static Field find(final List<Field> fields, final String name) {
        for (Field f : fields) {
            if (name.equals(f.getName())) {
                return f;
            }
        }

        return null;
    }
}
