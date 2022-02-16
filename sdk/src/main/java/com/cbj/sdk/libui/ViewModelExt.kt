package com.cbj.sdk.libui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cbj.sdk.libbase.exception.ExceptionUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @author:CBJ
 * @date: 2022/2/14
 * @des:
 */
fun ViewModel.launch(
    onError: (e: Throwable) -> Unit = {},
    onComplete: () -> Unit = {},
    block: suspend CoroutineScope.() -> Unit
) {
    viewModelScope.launch(
        CoroutineExceptionHandler { _, throwable ->
            run {
                ExceptionUtil.catchException(throwable)
                onError(throwable)
            }
        }
    ) {
        try {
            block.invoke(this)
        } finally {
            onComplete()
        }
    }
}