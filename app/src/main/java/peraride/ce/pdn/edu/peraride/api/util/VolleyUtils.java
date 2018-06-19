package peraride.ce.pdn.edu.peraride.api.util;

import com.android.volley.Request;

import java.lang.reflect.Field;

import static peraride.ce.pdn.edu.peraride.api.util.ReflectionUtils.allFields;
import static peraride.ce.pdn.edu.peraride.api.util.ReflectionUtils.find;


/**
 * Volley library hacking utils.
 */
public final class VolleyUtils {
  /* [ CONSTANTS ] ================================================================================================= */

    /**
     * multi-threading access lock.
     */
    private final static Object sLock = new Object();

  /* [ STATIC MEMBERS ] ============================================================================================ */

    /**
     * Cached value.
     */
    private static Field sCanceledField;

  /* [ STATIC METHODS ] ============================================================================================ */

    /**
     * Reset cancel state of the provided instance.
     *
     * @param <T>     the type parameter
     * @param request the request instance
     * @throws IllegalAccessException the illegal access exception
     */
    public static <T> void resetCancelState(final Request<T> request) throws IllegalAccessException {
        if (request.isCanceled()) {
            getCanceledField().setBoolean(request, false);
        }
    }

    /**
     * Gets canceled field by reflection and cache it for future calls. Reflection is a time consuming operation.
     *
     * @return the canceled field instance.
     */
    private static Field getCanceledField() {
        if (null == sCanceledField) {
            synchronized (sLock) {
                if (null == sCanceledField) {
                    final Field field = find(allFields(Request.class), "mCanceled");
                    if (field != null) {
                        field.setAccessible(true);
                    }

                    sCanceledField = field;
                }
            }
        }

        return sCanceledField;
    }
}
