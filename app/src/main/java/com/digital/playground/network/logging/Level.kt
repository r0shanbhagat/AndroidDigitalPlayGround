package com.digital.playground.network.logging

/**
 * @Details Level
 * @Author Roshan Bhagat
 */
enum class Level {
    /**
     * No logs.
     */
    NONE,

    /**
     * Logs request and response lines.
     *
     *
     * RegisterRequest:
     * <pre>`--> POST /greeting HTTP/1.1 (3-byte body)
     *
     * <-- HTTP/1.1 200 OK (22ms, 6-byte body)
    ` * *</pre>
     */
    BASIC,

    /**
     * Logs request and response lines and their respective headers.
     *
     *
     * RegisterRequest:
     * <pre>`--> POST /greeting HTTP/1.1
     * Host: example.com
     * Content-Type: plain/text
     * Content-Length: 3
     * --> END POST
     *
     * <-- HTTP/1.1 200 OK (22ms)
     * Content-Type: plain/text
     * Content-Length: 6
     * <-- END HTTP
    ` * *</pre>
     */
    HEADERS,

    /**
     * Logs request and response lines and their respective headers and bodies (if present).
     *
     *
     * RegisterRequest:
     * <pre>`--> POST /greeting HTTP/1.1
     * Host: example.com
     * Content-Type: plain/text
     * Content-Length: 3
     *
     * Hi?
     * --> END GET
     *
     * <-- HTTP/1.1 200 OK (22ms)
     * Content-Type: plain/text
     * Content-Length: 6
     *
     * Hello!
     * <-- END HTTP
    ` * *</pre>
     */
    BODY
}