package coraythan.mvproxy.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class KeyForgeCard(
        val id: String,
        val card_title: String,
        val house: String,
        val card_type: String,
        val front_image: String,
        val card_text: String,
        val amber: Int,
        val power: String?,
        val armor: String?,
        val rarity: String,
        val flavor_text: String? = null,
        val card_number: String,
        val expansion: Int,
        val is_maverick: Boolean,
        val is_anomaly: Boolean,
        val is_enhanced: Boolean,
        val traits: String? = null
)
