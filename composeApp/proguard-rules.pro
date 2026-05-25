# ProGuard Rules for Arcane

# Keep classes for SQLDelight
-keep class com.example.arcane.data.local.** { *; }

# Keep classes for Kotlin Serialization
-keep,includedescriptorclasses class com.example.arcane.**$$serializer { *; }

# Keep Android Application and Activity
-keepclasseswithmembers class com.example.arcane.** {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclassmembers class com.example.arcane.** {
    @androidx.compose.runtime.Composable <methods>;
}
