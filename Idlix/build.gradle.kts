// use an integer for version numbers
version = 19


cloudstream {
    language = "id"
    // All of these properties are optional, you can safely remove them

    // description = "Lorem Ipsum"
     authors = listOf("Hexated", "TeKuma25")

    /**
     * Status int as the following:
     * 0: Down
     * 1: Ok
     * 2: Slow
     * 3: Beta only
     * */
    status = 1 // will be 3 if unspecified
    tvTypes = listOf(
        "TvSeries",
        "Movie",
        "Anime",
        "AsianDrama",
    )

    iconUrl = "hhttps://tv7.idlix.asia/wp-content/uploads/2020/07/logov4.png"
}