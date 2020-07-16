package me.davidcosta.tmdb.data.api

import me.davidcosta.tmdb.BuildConfig
import me.davidcosta.tmdb.data.model.*
import retrofit2.Call
import retrofit2.http.*

interface Api {

    /******************************************
     * AUTHENTICATION
     ******************************************/
    @GET("/3/authentication/token/new")
    fun createRequestToken(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Call<AuthenticationResult>

    @FormUrlEncoded
    @POST("/3/authentication/token/validate_with_login")
    fun validateWithLogin(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("request_token") requestToken: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Call<AuthenticationResult>

    @FormUrlEncoded
    @POST("authentication/session/new")
    fun createSession(
        @Field("request_token") requestToken: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Call<SessionResult>

    /******************************************
     * ACCOUNT
     ******************************************/

    @GET("account")
    fun accountDetails(
        @Query("session_id") sessionId: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Call<AccountDetails>

    @GET("account/{account_id}/watchlist/movies")
    fun watchlist(
        @Path("account_id") accountId: Long,
        @Query("session_id") sessionId: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = BuildConfig.TMDB_LANGUAGE
    ): Call<PagedResult<Media>>

    @POST("account/{account_id}/watchlist")
    fun addToWatchlist(
        @Body mediaWatchlist: MediaWatchlist,
        @Path("account_id") accountId: Long,
        @Query("session_id") sessionId: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Call<WatchlistOperationResponse>

    @POST("account/{account_id}/watchlist")
    fun removeFromWatchlist(
        @Body mediaWatchlist: MediaWatchlist,
        @Path("account_id") accountId: Long,
        @Query("session_id") sessionId: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Call<WatchlistOperationResponse>

    /******************************************
     * MOVIES
     ******************************************/

    @GET("genre/movie/list")
    fun movieGenres(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = BuildConfig.TMDB_LANGUAGE
    ): Call<Genres>

    @GET("discover/movie")
    fun moviesByGenre(
        @Query("with_genres") genreId: Long,
        @Query("include_adult") includeAdults: Boolean = false,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = BuildConfig.TMDB_LANGUAGE
    ): Call<PagedResult<Media>>

    @GET("movie/{movie_id}")
    fun movieDetails(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = BuildConfig.TMDB_LANGUAGE
    ): Call<Movie>

    @GET("movie/{movie_id}/similar")
    fun similarMovies(
        @Path("movie_id") movieId: Long,
        @Query("include_adult") includeAdults: Boolean = false,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = BuildConfig.TMDB_LANGUAGE
    ): Call<PagedResult<Media>>

    @GET("movie/{movieId}/credits")
    fun movieCredits(
        @Path("movieId") movieId: Long,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Call<Credits>

    @GET("movie/popular")
    fun moviesPopular(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = BuildConfig.TMDB_LANGUAGE
    ): Call<PagedResult<Media>>

    /******************************************
     * TV
     ******************************************/

    @GET("genre/tv/list")
    fun tvGenres(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = BuildConfig.TMDB_LANGUAGE
    ): Call<Genres>

    @GET("tv/{tv_id}")
    fun tvDetails(
        @Path("tv_id") tvId: Long,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = BuildConfig.TMDB_LANGUAGE
    ): Call<Tv>

    @GET("tv/popular")
    fun tvsPopular(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = BuildConfig.TMDB_LANGUAGE
    ): Call<PagedResult<Media>>
}