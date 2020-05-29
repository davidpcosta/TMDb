package me.davidpcosta.tmdb.data

import me.davidpcosta.tmdb.data.model.AuthenticationResult
import me.davidpcosta.tmdb.data.model.Movie
import me.davidpcosta.tmdb.data.model.Result
import me.davidpcosta.tmdb.data.model.SessionResult
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import rx.Observable

interface Api {

    // Authentication endpoints

    @GET("/3/authentication/token/new?api_key=14c5082821919b82e9100838d9985ab1")
    fun createRequestToken(): Observable<AuthenticationResult>

    @FormUrlEncoded
    @POST("/3/authentication/token/validate_with_login?api_key=14c5082821919b82e9100838d9985ab1")
    fun validateWithLogin(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("request_token") requestToken: String
    ): Observable<AuthenticationResult>

    @FormUrlEncoded
    @POST("/3/authentication/session/new?api_key=14c5082821919b82e9100838d9985ab1")
    fun createSession(
        @Field("request_token") requestToken: String
    ): Observable<SessionResult>

    // Watchlist


    @GET("/3/account/9042674/watchlist/movies?api_key=14c5082821919b82e9100838d9985ab1&session_id=3270961c1062d2f1d42260b1e0ec4b0ccc230706&language=pt-BR")
    fun watchlist(): Observable<Result<Movie>>

}