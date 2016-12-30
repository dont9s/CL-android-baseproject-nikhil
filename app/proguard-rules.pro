# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/cl-mac-mini-3/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontwarn android.support.**

# Retrofit 2.X
## https://square.github.io/retrofit/ ##

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}


# OkHttp
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**


# Okio
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**


## Square Picasso specific rules ##
## https://square.github.io/picasso/ ##

-dontwarn com.squareup.okhttp.**


-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
**[] $VALUES;
public *;
}

-keepclassmembers class * {
private <fields>;
}

-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.stripe.** { *; }

-dontwarn sun.misc.Unsafe

-keep class com.skeleton.models.** { *; }

-dontwarn rx.**

-keep public class de.hdodenhof.*
-dontwarn org.xmlpull.v1.**
-dontwarn com.github.siyamed.**
-keep class com.github.siyamed.shapeimageview.**{ *; }

