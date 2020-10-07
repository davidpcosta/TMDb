package me.davidcosta.tmdb.data.vo

import me.davidcosta.tmdb.enums.Gender
import java.io.Serializable

data class PersonVO(
    val id: Long,
    val name: String,
    val role: String? = null,
    val department: String? = null,
    val job: String? = null,
    val profilePath: String?,
    val gender: Gender? = null
): Serializable