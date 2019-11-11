package coraythan.mvproxy.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class KeyForgeDeck(
        val id: String,
        val name: String,
        val expansion: Int,
        val power_level: Int = 0,
        val chains: Int = 0,
        val wins: Int = 0,
        val losses: Int = 0,
        val cards: List<String>? = null,
        val _links: KeyForgeDeckLinks? = null
)
