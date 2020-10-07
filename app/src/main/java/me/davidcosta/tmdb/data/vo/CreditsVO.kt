package me.davidcosta.tmdb.data.vo

import java.io.Serializable


data class CreditsVO (
    val cast: List<PersonVO>,
    val crew: List<PersonVO>
) : Serializable