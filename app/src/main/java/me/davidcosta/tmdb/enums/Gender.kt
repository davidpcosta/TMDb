package me.davidcosta.tmdb.enums

import me.davidcosta.tmdb.R

enum class Gender(val value: Int, val label: Int) {
    UNDEFINED(0, R.string.gender_undefined),
    FEMALE(1, R.string.gender_female),
    MALE(2, R.string.gender_male);

    companion object {
        fun parse(value: Int): Gender =
            when (value) {
                1 -> FEMALE
                2 -> MALE
                else -> UNDEFINED
            }
    }
}