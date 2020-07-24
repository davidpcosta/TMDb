package me.davidcosta.tmdb.data.api

class Params {
    enum class SortBy(val value: String) {
        PopularityAsc("popularity.asc"),
        PopularityDesc("popularity.desc"),
        ReleaseDateAsc("release_date.asc"),
        ReleaseDateDesc("release_date.desc"),
        RevenueAsc("revenue.asc"),
        RevenueDesc("revenue.desc"),
        PrimaryReleaseDateAsc("primary_release_date.asc"),
        PrimaryReleaseDateDesc("primary_release_date.desc"),
        OriginalTitleAsc("original_title.asc"),
        OriginalTitleDesc("original_title.desc"),
        VoteAverageAsc("vote_average.asc"),
        VoteAverageDesc("vote_average.desc"),
        VoteCountAsc("vote_count.asc"),
        VoteCountDesc("vote_count.desc")
    }

    enum class ReleaseType(val value: Int) {
        Premiere(1),
        TheatricalLimited(2),
        Theatrical(3),
        Digital(4),
        Physical(5),
        TV(6)
    }
}