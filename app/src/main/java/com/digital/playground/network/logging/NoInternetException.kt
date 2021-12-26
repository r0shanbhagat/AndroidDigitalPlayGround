package com.digital.playground.network.logging

import java.io.IOException

class NoInternetException(msg: String?, cause: Throwable?) : IOException(msg, cause)