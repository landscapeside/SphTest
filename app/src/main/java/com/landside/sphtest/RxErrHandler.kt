package com.landside.sphtest

import android.content.Context
import android.widget.Toast
import com.landside.support.exceptions.ServerException
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.functions.Consumer
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber
import javax.inject.Inject

class RxErrHandler @Inject
constructor(
  internal var context: Context
) : Consumer<Throwable> {

  fun init() {
    RxJavaPlugins.reset()
    RxJavaPlugins.setErrorHandler(this)
  }

  @Throws(Exception::class)
  override fun accept(throwable: Throwable) {
    var throwable = throwable
    when (throwable) {
      is UndeliverableException -> {
        throwable = throwable.cause!!
        when (throwable) {
          is ServerException -> {
            Toast.makeText(context, throwable.message, Toast.LENGTH_LONG)
                .show()
            Timber.e(throwable)
          }
          else -> {
            Toast.makeText(context, throwable.message, Toast.LENGTH_LONG)
              .show()
            Timber.e(throwable)
          }
        }
      }
      is ServerException -> {
        Toast.makeText(context, throwable.message, Toast.LENGTH_LONG)
            .show()
        Timber.e(throwable)
      }
    }
  }
}
