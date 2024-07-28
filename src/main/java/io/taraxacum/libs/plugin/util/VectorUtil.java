package io.taraxacum.libs.plugin.util;

import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import java.util.Random;

public class VectorUtil {
    private static Random random = new Random();

    public static Vector fromYawPitch(float yaw, float pitch) {
        if (pitch == 90) {
            return new Vector(0, -1, 0);
        }
        if (pitch == -90) {
            return new Vector(0, 1, 0);
        }
        Vector vector = new Vector(0, 0, 1);
        vector.rotateAroundX(pitch / 180 * Math.PI);
        vector.rotateAroundY(yaw > 0 ? (360 - yaw) / 180 * Math.PI : -1 * yaw / 180 * Math.PI);
        return vector;
    }

    public static Vector getTrueRandom() {
        return new Vector(random.nextDouble() * 2 + -1, random.nextDouble() * 2 + -1, random.nextDouble() * 2 + -1);
    }

    public static Vector toLength(@Nonnull Vector vector, double length) {
        double x = vector.getX();
        double y = vector.getY();
        double z = vector.getZ();

        double v = Math.pow(length * length / vector.lengthSquared(), 0.5);
        return new Vector(x * v, y * v, z * v);
    }
}
