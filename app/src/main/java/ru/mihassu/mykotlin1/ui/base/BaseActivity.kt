package ru.mihassu.mykotlin1.ui.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity<T, S: BaseViewState<T>> : AppCompatActivity() {

    abstract val viewModel: BaseViewModel<T, S>
    abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        viewModel.getViewState().observe(this, Observer<S> {
            if (it == null) return@Observer
            if (it.data != null) renderData(it.data)
            if (it.error != null) renderError(it.error)
        })
    }

    abstract fun renderData(data: T)

    protected fun renderError(error: Throwable) {
        error.message?.let { showError(it) }
    }

    protected fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}