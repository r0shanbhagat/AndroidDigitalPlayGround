package com.digital.playground.repository.logging

import java.io.IOException

class NoInternetException(msg: String?, cause: Throwable?) : IOException(msg, cause)