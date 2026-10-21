package edu.ufg.plantillaaulas.util;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class ClaveUtil {

    private static final int ITERACIONES = 600000;
    private static final SecureRandom RANDOM = new SecureRandom();

    private ClaveUtil() {
    }

    public static String crearHash(String clave) {
        byte[] sal = new byte[16];
        RANDOM.nextBytes(sal);

        byte[] hash = calcular(clave, sal, ITERACIONES);

        return ITERACIONES + ":"
                + Base64.getEncoder().encodeToString(sal) + ":"
                + Base64.getEncoder().encodeToString(hash);
    }

    public static boolean verificar(String clave, String guardado) {
        if (guardado == null) {
            return false;
        }

        try {
            String[] partes = guardado.split(":");

            if (partes.length != 3) {
                return false;
            }

            int iteraciones = Integer.parseInt(partes[0]);

            if (iteraciones < 1 || iteraciones > 1000000) {
                return false;
            }

            byte[] sal = Base64.getDecoder().decode(partes[1]);
            byte[] esperado = Base64.getDecoder().decode(partes[2]);
            byte[] recibido = calcular(clave, sal, iteraciones);

            return MessageDigest.isEqual(esperado, recibido);

        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static byte[] calcular(
            String clave, byte[] sal, int iteraciones) {

        PBEKeySpec especificacion = new PBEKeySpec(
                clave.toCharArray(), sal, iteraciones, 256
        );

        try {
            SecretKeyFactory fabrica = SecretKeyFactory.getInstance(
                    "PBKDF2WithHmacSHA256"
            );

            return fabrica.generateSecret(especificacion).getEncoded();

        } catch (GeneralSecurityException e) {
            throw new IllegalStateException(
                    "No se pudo procesar la contraseña.", e
            );
        } finally {
            especificacion.clearPassword();
        }
    }
}