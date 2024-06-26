plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}


def keysPropertiesFile = rootProject.file("keys.properties")
def keysProperties = new Properties()
if (keysPropertiesFile.exists()) {
    keysProperties.load(new FileInputStream(keysPropertiesFile))
}

android {
    namespace = "com.example.loolah"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.loolah"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildConfigField "String", "GOOGLE_MAPS_API_KEY", "\"${keysProperties['GOOGLE_MAPS_API_KEY']}\""

        manifestPlaceholders = [
            GOOGLE_MAPS_API_KEY: "${keysProperties['GOOGLE_MAPS_API_KEY']}",
        ]
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.5")
    implementation("androidx.navigation:navigation-ui:2.7.5")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.github.bumptech.glide:glide:4.14.2")
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    implementation ("com.google.android.gms:play-services-maps:18.2.0")
    implementation ("com.google.android.libraries.places:places:3.2.0")
    implementation ("com.opencsv:opencsv:5.8")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")
    implementation("com.android.volley:volley:1.2.1")

    implementation(platform("com.google.firebase:firebase-bom:32.4.1"))
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-storage")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.firebase:geofire-android-common:3.2.0")
}