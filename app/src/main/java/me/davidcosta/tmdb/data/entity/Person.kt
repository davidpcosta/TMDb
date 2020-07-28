package me.davidcosta.tmdb.data.entity

data class Person(
    val id: Long,
    val name: String,
    val role: String?,
    val profilePath: String?
)