package tech.mohamedashraf.domain.utils

fun String.isValidEmail(): Boolean {
    val regex = "^[a-zA-Z0-9.a-zA-Z0-9.!#\$%&'*+-/=?^_`{|}~]+@[a-zA-Z0-9]+\\.[a-zA-Z]+".toRegex()
    return this.matches(regex)
}