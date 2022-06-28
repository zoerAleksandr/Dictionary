package dependencies

// Android
private const val CORE_KTX_VER = "1.8.0"
private const val APP_COMPAT_VER = "1.4.1"
private const val MATERIAL_VER = "1.6.1"
private const val CONSTRAINT_LAYOUT_VER = "2.1.4"

const val CORE_KTX_DEP = "androidx.core:core-ktx:$CORE_KTX_VER"
const val APP_COMPAT_DEP = "androidx.appcompat:appcompat:$APP_COMPAT_VER"
const val MATERIAL_DEP = "com.google.android.material:material:$MATERIAL_VER"
const val CONSTRAINT_LAYOUT_DEP =
    "androidx.constraintlayout:constraintlayout:$CONSTRAINT_LAYOUT_VER"

// Test
private const val JUNIT_VER = "4.13.2"
private const val ANDROID_JUNIT_VER = "1.1.3"
private const val ESPRESSO_VER = "3.4.0"

const val JUNIT_DEP = "junit:junit:$JUNIT_VER"
const val ANDROID_JUNIT_DEP = "androidx.test.ext:junit:$ANDROID_JUNIT_VER"
const val ESPRESSO_DEP = "androidx.test.espresso:espresso-core:$ESPRESSO_VER"

// ViewBindingPropertyDelegate
private const val VIEW_BINDING_DELEGATE_VER = "1.5.3"

const val VIEW_BINDING_DELEGATE_DEP =
    "com.github.kirich1409:viewbindingpropertydelegate:$VIEW_BINDING_DELEGATE_VER"
const val VIEW_BINDING_DELEGATE_DEP_NO_REF =
    "com.github.kirich1409:viewbindingpropertydelegate-noreflection:$VIEW_BINDING_DELEGATE_VER"

// Room
private const val ROOM_VER = "2.4.2"

const val ROOM_DEP = "androidx.room:room-runtime:$ROOM_VER"
const val KAPT_ROOM_DEP = "androidx.room:room-compiler:$ROOM_VER"

// Gson
private const val GSON_VER = "2.9.0"

const val GSON_DEP = "com.google.code.gson:gson:$GSON_VER"

// Koin
private const val KOIN_VER = "3.2.0-beta-1"
const val KOIN_CORE = "io.insert-koin:koin-core:$KOIN_VER"
const val KOIN_ANDROID = "io.insert-koin:koin-android:$KOIN_VER"

// FragmentKTX
private const val FRAGMENT_KTX_VER = "1.4.1"
const val FRAGMENT_KTX_DEP = "androidx.fragment:fragment-ktx:$FRAGMENT_KTX_VER"

// Retrofit
private const val RETROFIT_VER = "2.9.0"
private const val RETROFIT_COROUTINE_ADAPTER_VER = "0.9.2"
private const val OKHTTP_LOGGING_VER = "3.12.1"

const val RETROFIT_DEP = "com.squareup.retrofit2:retrofit:$RETROFIT_VER"
const val RETROFIT_CONVERTER_GSON_DEP = "com.squareup.retrofit2:converter-gson:$RETROFIT_VER"
const val RETROFIT_COROUTINE_ADAPTER_DEP =
    "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:$RETROFIT_COROUTINE_ADAPTER_VER"
const val OKHTTP_LOGGING_DEP = "com.squareup.okhttp3:logging-interceptor:$OKHTTP_LOGGING_VER"

// Coil
private const val COIL_VER = "1.4.0"
const val COIL_DEP = "io.coil-kt:coil:$COIL_VER"

// Coroutines
private const val COROUTINES_VER = "1.6.1"

const val COROUTINES_CORE_DEP = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$COROUTINES_VER"
const val COROUTINES_ANDROID_DEP = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$COROUTINES_VER"