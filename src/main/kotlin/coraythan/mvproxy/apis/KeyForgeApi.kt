package coraythan.mvproxy.apis

import coraythan.mvproxy.models.KeyForgeDeckDto
import coraythan.mvproxy.models.KeyForgeDeckResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Service
class KeyForgeApi(
        val restTemplate: RestTemplate
) {

    private var requestsInFiveSec = 0

    @Scheduled(fixedDelayString = "PT10S", initialDelayString = "PT10S")
    fun resetRequests() {
        requestsInFiveSec = 0
    }

    private val log = LoggerFactory.getLogger(this::class.java)

    fun findDeck(deckId: String, withCards: Boolean = true): KeyForgeDeckResponse {
        val rateLimited = canMakeRequest()
        if (rateLimited != null) {
            return rateLimited
        }
        return try {
            val found = keyforgeGetRequest(
                    KeyForgeDeckDto::class.java,
                    "decks/v2/$deckId${if (withCards) "/?links=cards" else ""}"
            )

            KeyForgeDeckResponse(
                    deck = found
            )
        } catch (exception: Exception) {
            KeyForgeDeckResponse(error = exception.localizedMessage)
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
                    returnType
            )
            return decksEntity.body
        } catch (e: HttpClientErrorException.NotFound) {
            // No results
            return null
        }
    }

    private fun canMakeRequest(): KeyForgeDeckResponse? {
        if (requestsInFiveSec < 5) {
            requestsInFiveSec++
            return null
        } else {
            return KeyForgeDeckResponse(error = "Rate Limited")
        }
    }
}