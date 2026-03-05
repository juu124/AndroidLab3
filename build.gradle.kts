// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false

    id("com.google.gms.google-services") version "4.4.4" apply false

    // KSP(Kotlin Symbol Processing)
    // 코틀린 코드를 해석해서 코드를 자동으로 생성해주는 개발자 도구..
    // @(어노테이션) 해석기라고 생각해면 될 것.
    // 과거에는 kapt을 주고 사용했는데 요즈믕ㄴ KSP를 사용한다. 더 빠르기 때문이다.
    // 2.0.21-1.0.28  : 코틀린 언어버전-ksp 버전
    // 사용하는 코틀린 버전 명시
    id("com.google.devtools.ksp") version "2.0.21-1.0.28" apply false
}