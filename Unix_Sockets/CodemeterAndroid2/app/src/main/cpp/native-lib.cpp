#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_s2icode_codemeterandroid2_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_s2icode_codemeterandroid2_CodemeterActivity_stringFromJNI(JNIEnv *env, jobject thiz) {
    // TODO: implement stringFromJNI()

        std::string hello = "Hello from Codemeter";
        return env->NewStringUTF(hello.c_str());
}