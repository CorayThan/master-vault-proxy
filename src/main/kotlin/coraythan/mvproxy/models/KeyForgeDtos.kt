package coraythan.mvproxy.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

data class KeyForgeDeckResponse(
        val deck: KeyForgeDeckDto? = null,
        val error: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class KeyForgeDecksPageDto(
        val count: Int,
        val data: List<KeyForgeDeck>,
        val _linked: KeyForgeDeckLinksFullCards
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class KeyForgeDeckLinks(
        val houses: List<String>?,
        val cards: List<String>?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class KeyForgeDeckLinksFullCards(
        val houses: Set<KeyForgeHouse>?,
        val cards: List<KeyForgeCard>?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class KeyForgeDeckDto(
        val data: KeyForgeDeck,
        val _linked: KeyForgeDeckLinksFullCards
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class KeyForgeHouse(
        val id: String,
        val name: String,
        val image: String
)