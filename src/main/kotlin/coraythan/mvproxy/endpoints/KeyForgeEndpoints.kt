package coraythan.mvproxy.endpoints

import coraythan.mvproxy.apis.KeyForgeApi
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/master-vault/decks")
class DeckEndpoints(
        private val keyforgeApi: KeyForgeApi
) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @PostMapping
    fun findDecks(@RequestBody filters: KeyForgeDeckRequestFilters) = keyforgeApi.findDecks(filters.page, filters.ordering, filters.pageSize, filters.expansion, filters.withCards)

    @GetMapping("/{id}")
    fun findDeck(@PathVariable id: String) = keyforgeApi.findDeck(id, true)

}

data class KeyForgeDeckRequestFilters(
        val page: Int,
        // "date", "-wins", "-losses"
        val ordering: String,
        val pageSize: Int,
        val expansion: Int?,
        val withCards: Boolean
)
