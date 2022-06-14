package com.example.dictionary.data

import com.example.dictionary.domain.presenter.Presenter
import com.example.dictionary.view.AppState
import com.example.dictionary.view.Contract
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class PresenterImpl(
    private val interactor: InteractorImpl
) : Presenter {
    private val compositeDisposable = CompositeDisposable()
    private var activity: Contract? = null

    override fun attach(view: Contract) {
        if (view != activity) {
            activity = view
        }
    }

    override fun detach(view: Contract) {
        compositeDisposable.clear()
        if (view == activity) {
            activity = null
        }
    }

    override fun getData(text: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(text, isOnline)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { activity?.renderData(AppState.Loading(true)) }
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {
            override fun onNext(t: AppState) {
                activity?.renderData(t)
            }

            override fun onError(e: Throwable) {
                activity?.renderData(AppState.Error(e))
            }

            override fun onComplete() {}
        }
    }
}