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

    @GetMapping("/{id}")
    fun findDeck(@PathVariable id: String) = keyforgeApi.findDeck(id, true)

}
