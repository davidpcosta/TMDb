package me.davidcosta.tmdb.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*


data class Tv (
    @SerializedName("id") var id: Long,
    @SerializedName("name") var name: String,
    @SerializedName("original_name") var originalName: String,
    @SerializedName("backdrop_path") var backdropPath: String?,
    @SerializedName("poster_path") var posterPath: String?,
    @SerializedName("overview") var overview: String?,
    @SerializedName("first_air_date") var firstAirData: Date,
    @SerializedName("last_air_date") var lastAirDate: Date,
    @SerializedName("original_language") var originalLanguage: String,
    @SerializedName("vote_average") var voteAverage: Double,
    @SerializedName("genres") var genres: List<Genre>,
    @SerializedName("episode_run_time") var runtime: List<Int>,
    @SerializedName("status") var status: String,
    @SerializedName("seasons") var seasons: List<Season>,
    @SerializedName("networks") var networks: List<Network>,
    @SerializedName("number_of_episodes") var numberOfEpisodes: Int,
    @SerializedName("number_of_seasons") var numberOfSeasons: Int,
    @SerializedName("homepage") var homepage: String?,

    @SerializedName("created_by") var createdBy: List<Credit>?,
    @SerializedName("last_episode_to_air") var lastEpisodeToAir: Episode?,
    @SerializedName("next_episode_to_air") var nextEpisodeToAir: Episode?,

    @SerializedName("type") var type: String,
    @SerializedName("languages") var languages: List<String>,
    @SerializedName("origin_country") var originCountry: List<String>,
    @SerializedName("production_companies") var productionCompanies: List<Company>,
    @SerializedName("in_production") var inProduction: Boolean,
    @SerializedName("vote_count") var voteCount: Int,
    @SerializedName("popularity") var popularity: Double
): Serializable