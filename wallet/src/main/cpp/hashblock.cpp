#include "hashblock.h"
#include <inttypes.h>

#include <jni.h>

/*jbyteArray JNICALL scryptN(JNIEnv *env, jclass cls, jbyteArray passwd, jbyteArray salt,
    jint N, jint r, jint p, jint dkLen)
{
    jint Plen = (*env)->GetArrayLength(env, passwd);
    jint Slen = (*env)->GetArrayLength(env, salt);
    jbyte *P = (*env)->GetByteArrayElements(env, passwd, NULL);
    jbyte *S = (*env)->GetByteArrayElements(env, salt, NULL);
    uint8_t *buf = malloc(sizeof(uint8_t) * dkLen);
    jbyteArray DK = NULL;

    if (P == NULL || S == NULL || buf == NULL) goto cleanup;

    if (crypto_scrypt((uint8_t *) P, Plen, (uint8_t *) S, Slen, N, r, p, buf, dkLen)) {
        jclass e = (*env)->FindClass(env, "java/lang/IllegalArgumentException");
        char *msg;
        switch (errno) {
            case EINVAL:
                msg = "N must be a power of 2 greater than 1";
                break;
            case EFBIG:
            case ENOMEM:
                msg = "Insufficient memory available";
                break;
            default:
                msg = "Memory allocation failed";
        }
        (*env)->ThrowNew(env, e, msg);
        goto cleanup;
    }

    DK = (*env)->NewByteArray(env, dkLen);
    if (DK == NULL) goto cleanup;

    (*env)->SetByteArrayRegion(env, DK, 0, dkLen, (jbyte *) buf);

  cleanup:

    if (P) (*env)->ReleaseByteArrayElements(env, passwd, P, JNI_ABORT);
    if (S) (*env)->ReleaseByteArrayElements(env, salt, S, JNI_ABORT);
    if (buf) free(buf);

    return DK;
}*/



jbyteArray JNICALL hash9_native(JNIEnv *env, jclass cls, jbyteArray header)
{
    jint Plen = (env)->GetArrayLength(header);
    jbyte *P = (env)->GetByteArrayElements(header, NULL);
    //uint8_t *buf = malloc(sizeof(uint8_t) * dkLen);
    jbyteArray DK = NULL;

    if (P)
	{
	
	uint256 result = Hash9(P, P+Plen);

    /*if (crypto_scrypt((uint8_t *) P, Plen, (uint8_t *) S, Slen, N, r, p, buf, dkLen)) {
        jclass e = (*env)->FindClass(env, "java/lang/IllegalArgumentException");
        char *msg;
        switch (errno) {
            case EINVAL:
                msg = "N must be a power of 2 greater than 1";
                break;
            case EFBIG:
            case ENOMEM:
                msg = "Insufficient memory available";
                break;
            default:
                msg = "Memory allocation failed";
        }
        (*env)->ThrowNew(env, e, msg);
        goto cleanup;
    }*/

    DK = (env)->NewByteArray(32);
    if (DK)
	{
		(env)->SetByteArrayRegion(DK, 0, 32, (jbyte *) result.begin());
	}
	

    if (P) (env)->ReleaseByteArrayElements(header, P, JNI_ABORT);
    //if (buf) free(buf);
	}
    return DK;
}

static const JNINativeMethod methods[] = {
    { "hash9_native", "([B)[B", (void *) hash9_native }
};

jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;

    if ((vm)->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return -1;
    }

    jclass cls = (env)->FindClass("com/hashengineering/crypto/Hash9");
    int r = (env)->RegisterNatives(cls, methods, 1);

    return (r == JNI_OK) ? JNI_VERSION_1_6 : -1;
}