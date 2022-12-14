package com.example.ricknmorty.arch

interface EpisodeListInterface {
    fun getEpisode(episodeNum: Int): Int?
    fun updateInputFilter(filterType: EpisodeFilterType, value: String)
}