# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# WebView에서 JavaScript 인터페이스를 사용하는 경우 대비
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# WebView 관련 기본 설정 유지
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
