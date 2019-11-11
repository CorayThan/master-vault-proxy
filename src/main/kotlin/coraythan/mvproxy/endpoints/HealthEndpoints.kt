package coraythan.mvproxy.endpoints

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/health")
class HealthEndpoints {

    private val log = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    fun healthCheck() = true

}
