package coraythan.mvproxy.apis

import coraythan.mvproxy.models.KeyForgeDeckDto
import coraythan.mvproxy.models.KeyForgeDeckResponse
import coraythan.mvproxy.models.KeyForgeDecksPageDto
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import kotlin.system.measureTimeMillis

const val keyforgeApiDeckPageSize = 10

@Service
class KeyForgeApi(
        val restTemplate: RestTemplate
) {

    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * Null implies no decks available.
     */
    fun findDecks(page: Int, ordering: String = "date", pageSize: Int = keyforgeApiDeckPageSize, expansion: Int? = null): KeyForgeDecksPageDto? {
        var decks: KeyForgeDecksPageDto? = null

        val keyforgeRequestDuration = measureTimeMillis {
            decks = keyforgeGetRequest(
                    KeyForgeDecksPageDto::class.java,
                    "decks/?page=$page&page_size=$pageSize&search=&powerLevel=0,11&chains=0,24&ordering=$ordering" +
                            if (expansion == null) "" else "&expansion=$expansion"
            )
        }
        log.debug("Getting $pageSize decks from keyforge api took $keyforgeRequestDuration got decks ${decks?.count}")
        return decks
    }

    fun findDeck(deckId: String, withCards: Boolean = true): KeyForgeDeckResponse {
        try {
            val found = keyforgeGetRequest(
                    KeyForgeDeckDto::class.java,
                    "decks/$deckId${if (withCards) "/?links=cards" else ""}"
            )

            return KeyForgeDeckResponse(
                    deck = found
            )
        } catch (exception: Exception) {
            return KeyForgeDeckResponse(error = exception.localizedMessage)
        }
    }

    private fun <T> keyforgeGetRequest(returnType: Class<T>, url: String): T? {
        try {
            val decksEntity = restTemplate.exchange(
                    "https://www.keyforgegame.com/api/$url",
                    HttpMethod.GET,
                    HttpEntity<Any?>(null, HttpHeaders().let {
                        it.set("cache-control", "no-cache")
                        it.set("user-agent", "SpringBootRequest")
                        it
                    }),
                    returnType)
            return decksEntity.body
        } catch (e: HttpClientErrorException.NotFound) {
            // No results
            return null
        }
    }
}