/**
 * To define plugins
 */
object BuildPlugins {
    val android by lazy { "com.android.tools.build:gradle:${Versions.gradlePlugin}" }
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}" }
}

/**
 * To define dependencies
 */
object Deps {
    val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.appCompat}" }
    val materialDesign by lazy { "com.google.android.material:material:${Versions.material}" }
    val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}" }
    val swipeRefreshLayout by lazy { "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}" }
    val multidex by lazy { "androidx.multidex:multidex:${Versions.multidex}" }
    val navigationFragment by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.navigationArch}" }
    val navigationUI by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.navigationArch}" }
    val lifecycleExt by lazy { "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}" }
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }
    val retrofitConverter by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofit}" }
    val retrofitLogger by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.retrofitLogger}" }
    val hilt by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
    val hiltKapt by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hilt}" }
    val glide by lazy { "com.github.bumptech.glide:glide:${Versions.glide}" }
    val shimmer by lazy { "com.facebook.shimmer:shimmer:${Versions.shimmer}" }

    val junit by lazy { "junit:junit:${Versions.jUnit}" }

}