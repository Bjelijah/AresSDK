package com.cbj.sdk.libbase.utils


import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.FlowableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


/**
 * Created by Administrator on 2017/8/7.
 */

object RxUtil {

    fun <T> doInUIThread(uiTask: RxSimpleTask<T>): Disposable {
        return Flowable.just(uiTask)
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({ uiTask -> uiTask.doTask() }, { throwable ->
                throwable.printStackTrace()
                uiTask.doTaskError(throwable)
            }, { uiTask.doFinish() })
    }




    fun <T> doInIOTthread(ioTask: RxSimpleTask<T>): Disposable {

        return Flowable.just(ioTask)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { ioTask -> ioTask.doTask() },
                { throwable -> ioTask.doTaskError(throwable) },
                { ioTask.doFinish() })
    }

    fun <T> doRxTask(t: CommonTask<T>): Disposable {
        return Flowable.create<CommonTask<T>>({ e ->
            t.doInIOThread()
            e.onNext(t)
            e.onComplete()
        }, BackpressureStrategy.BUFFER)

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { commonTask -> commonTask.doInUIThread() },
                { throwable -> t.doError(throwable) },
                { t.doFinish() })
    }


    abstract class RxSimpleTask<T> {
        var t: T? = null

        abstract fun doTask()
        fun doTaskError(throwable: Throwable) {
            throwable.printStackTrace()
        }

        fun doFinish() {}

        constructor(t: T) {
            this.t = t
        }

        constructor() {}
    }


    abstract class CommonTask<T> {
        var t: T? = null

        constructor() {}

        constructor(t: T) {

            this.t = t
        }

        abstract fun doInIOThread()

        abstract fun doInUIThread()

        fun doError(throwable: Throwable) {
            throwable.printStackTrace()
        }

        fun doFinish() {}
    }

}
