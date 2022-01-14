package com.digital.playground.network.logging

import java.io.IOException
/**
 * @Details NoInternetException
 * @Author Roshan Bhagat
 */
class NoInternetException(msg: String?, cause: Throwable?) : IOException(msg, cause)